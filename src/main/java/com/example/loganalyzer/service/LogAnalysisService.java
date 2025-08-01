package com.example.loganalyzer.service;

import com.example.loganalyzer.model.AnalysisResult;
import com.example.loganalyzer.model.ProductOrder;
import com.example.loganalyzer.model.ServiceOrder;
import com.example.loganalyzer.model.WorkOrder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class LogAnalysisService {

    // Date Time|Severity|DictionaryName:EventId|Originator|OrderId|Description
    // Regex pour Account Caller et Operation Type (MODIFIÉ ICI pour robustesse)
    // Capture les champs Originator (groupe 1 et 2) et assure que le pattern est présent
    // avant l'OrderId (qui peut être vide ou un autre format)
    private static final Pattern ACCOUNT_CALLER_OPERATION_TYPE_PATTERN = Pattern.compile(
            "^(?:[^|]*\\|){3}([^|:]+):([^|]+)\\|" // Capture Originator (Account:Operation)
    );

    // Pattern pour identifier les lignes de Product Order et Work Order, y compris les statuts
    // Groupes: 1=Timestamp, 2=Severity, 3=Originator (Account:Operation), 4=OrderId (PO/WO), 5=Description
    private static final Pattern GENERIC_LOG_PATTERN = Pattern.compile(
            "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{6})\\|([^|]+)\\|[^|]*\\|([^|]*)\\|([^|]*)\\|(.+)"
    );

    // Regex pour détecter les Product Orders CREATED
    private static final Pattern PRODUCT_ORDER_CREATED_PATTERN = Pattern.compile(
            "ProductOrderData CREATED for product order \\[([^\\]]+)\\]"
    );

    // Regex pour les Product Orders (y compris les statuts)
    private static final Pattern PRODUCT_ORDER_PATTERN = Pattern.compile(
            ".*ProductOrder (?:ID:)? ?([a-zA-Z0-9]+) (?:with )?status ([A-Z_]+).*"
    );

    // Regex pour les Work Orders (y compris les statuts et le temps de traitement)
    private static final Pattern WORK_ORDER_PATTERN = Pattern.compile(
            ".*WorkOrder ID: ([a-zA-Z0-9:]+).* STATUS : ([A-Z_]+).* PROCESSING TIME : (\\d+) microseconds"
    );

    // Regex pour les Service Orders (exemple basé sur vos logs "PNodeDriver...")
    private static final Pattern SERVICE_ORDER_PATTERN = Pattern.compile(
            "ServiceOrderData CREATED.*PNodeDriver(\\d{1,2}\\d{14}\\.\\d)" // Capture PNodeDriverXXYYYYMMDDHHMMSS.D
    );
    // Un autre pattern si les Service Orders sont aussi mentionnés par un autre type d'ID (ex: swap)
    private static final Pattern SERVICE_ORDER_SWAP_PATTERN = Pattern.compile(
            ".*ServiceOrderData CREATED for swap ID \\[(\\d+)\\]"
    );


    // Regex pour détecter un rollback (le texte peut varier)
    private static final Pattern ROLLBACK_PATTERN = Pattern.compile(".*ROLLBACK.*", Pattern.CASE_INSENSITIVE);


    public AnalysisResult analyzeLogFile(MultipartFile file, String targetRequestId) throws Exception {
        AnalysisResult result = new AnalysisResult();
        result.setRequestId(targetRequestId);
        result.setAccountCaller("Non trouvé / Par défaut"); // Valeurs par défaut
        result.setOperationType("Non trouvé / Par défaut");

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            lines = reader.lines().collect(Collectors.toList());
        }

        List<String> rollbackDetails = new ArrayList<>();
        boolean rollbackDetected = false;

        // Collecter tous les product orders avant d'associer les work orders
        List<ProductOrder> detectedProductOrders = new ArrayList<>();
        Map<String, ProductOrder> productOrderMap = new java.util.HashMap<>();

        // Première passe: collecter les informations générales et les Product/Service Orders
        for (String line : lines) {
            if (line.contains(targetRequestId)) { // Filtrer par l'ID de requête

                // Détecter Request ID et potentiellement Account Caller / Operation Type
                Matcher acOtMatcher = ACCOUNT_CALLER_OPERATION_TYPE_PATTERN.matcher(line);
                if (acOtMatcher.find()) {
                    String accountCandidate = acOtMatcher.group(1);
                    String operationCandidate = acOtMatcher.group(2);

                    // Seulement mettre à jour si une valeur valide (non null et non vide après trim) est trouvée
                    if (accountCandidate != null && !accountCandidate.trim().isEmpty()) {
                        // Mettre à jour si la valeur actuelle est par défaut OU si la nouvelle valeur est différente
                        if (result.getAccountCaller().equals("Non trouvé / Par défaut") || !result.getAccountCaller().equals(accountCandidate.trim())) {
                            result.setAccountCaller(accountCandidate.trim());
                        }
                    }
                    if (operationCandidate != null && !operationCandidate.trim().isEmpty()) {
                        // Mettre à jour si la valeur actuelle est par défaut OU si la nouvelle valeur est différente
                        if (result.getOperationType().equals("Non trouvé / Par défaut") || !result.getOperationType().equals(operationCandidate.trim())) {
                            result.setOperationType(operationCandidate.trim());
                        }
                    }
                }

                // Détection de Product Orders CREATED
                Matcher poCreatedMatcher = PRODUCT_ORDER_CREATED_PATTERN.matcher(line);
                if (poCreatedMatcher.find()) {
                    String poId = poCreatedMatcher.group(1).trim();
                    if (!productOrderMap.containsKey(poId)) {
                        ProductOrder po = new ProductOrder();
                        po.setId(poId);
                        po.setName("Nom PO inconnu"); // Valeur par défaut
                        po.setStatus("Status inconnu"); // Valeur par défaut
                        productOrderMap.put(poId, po);
                        detectedProductOrders.add(po);
                    }
                }

                // Détection de Product Orders avec statut
                Matcher poMatcher = PRODUCT_ORDER_PATTERN.matcher(line);
                if (poMatcher.find()) {
                    String poId = poMatcher.group(1).trim();
                    String status = poMatcher.group(2).trim();
                    if (productOrderMap.containsKey(poId)) {
                        productOrderMap.get(poId).setStatus(status);
                    } else {
                        // Si le PO est mentionné avec un statut avant d'être "CREATED"
                        ProductOrder po = new ProductOrder();
                        po.setId(poId);
                        po.setName("Nom PO inconnu"); // Valeur par défaut
                        po.setStatus(status);
                        productOrderMap.put(poId, po);
                        detectedProductOrders.add(po);
                    }
                }

                // Détection de Service Orders (basé sur le pattern PNodeDriver ou Swap ID)
                Matcher soMatcher = SERVICE_ORDER_PATTERN.matcher(line);
                if (soMatcher.find()) {
                    String soId = "PNodeDriver" + soMatcher.group(1).trim();
                    result.getServiceOrders().add(new ServiceOrder(soId, "Service Order", "N/A")); // Nom générique
                }
                Matcher soSwapMatcher = SERVICE_ORDER_SWAP_PATTERN.matcher(line);
                if (soSwapMatcher.find()) {
                    String soId = soSwapMatcher.group(1).trim();
                    result.getServiceOrders().add(new ServiceOrder(soId, "Service Order Swap", "N/A")); // Nom générique
                }

                // Détection de Rollback
                if (ROLLBACK_PATTERN.matcher(line).find()) {
                    rollbackDetected = true;
                    rollbackDetails.add(line);
                }
            }
        }

        // Deuxième passe: associer les Work Orders aux Product Orders détectés
        for (String line : lines) {
            if (line.contains(targetRequestId)) { // Toujours filtrer par l'ID de requête
                Matcher woMatcher = WORK_ORDER_PATTERN.matcher(line);
                if (woMatcher.find()) {
                    String woId = woMatcher.group(1).trim();
                    String woStatus = woMatcher.group(2).trim();
                    long processingTime = Long.parseLong(woMatcher.group(3).trim());

                    // Extraire le PO ID du WO ID (ex: 1808027668::26::1 -> 1808027668)
                    String parentPoId = woId.split("::")[0];

                    if (productOrderMap.containsKey(parentPoId)) {
                        ProductOrder parentPo = productOrderMap.get(parentPoId);
                        WorkOrder wo = new WorkOrder();
                        wo.setId(woId);
                        wo.setStatus(woStatus);
                        wo.setProcessingTimeMicros(processingTime);

                        // Tentative d'extraction du nom du Work Order (très basique, à améliorer si besoin)
                        Matcher genericLogMatcher = GENERIC_LOG_PATTERN.matcher(line);
                        if (genericLogMatcher.find()) {
                            wo.setRawLogTimestamp(genericLogMatcher.group(1).trim());
                            wo.setLogLevel(genericLogMatcher.group(2).trim());
                            // Le nom du WO n'est pas directement capturé par WORK_ORDER_PATTERN
                            // Il faudrait une logique plus complexe pour le déduire du 'Description'
                            // Pour l'instant, on peut mettre un nom générique ou essayer de le déduire.
                            // Si la description contient un nom clair pour le WO, on peut l'extraire.
                            String description = genericLogMatcher.group(5); // Le reste de la ligne
                            if (description.contains("WorkOrderData")) {
                                wo.setName("WorkOrder"); // Par défaut
                            } else {
                                // Essayer de trouver un nom basé sur la description si elle semble pertinente
                                Pattern namePattern = Pattern.compile("WorkOrder (\\w+)");
                                Matcher nameMatcher = namePattern.matcher(description);
                                if (nameMatcher.find()) {
                                    wo.setName(nameMatcher.group(1));
                                } else {
                                    wo.setName("Work Order inconnu");
                                }
                            }
                            // Extraction de l'instance, si disponible
                            Pattern instancePattern = Pattern.compile("INSTANCE NAME : ([^:]+)");
                            Matcher instanceMatcher = instancePattern.matcher(description);
                            if (instanceMatcher.find()) {
                                wo.setInstanceName(instanceMatcher.group(1).trim());
                            } else {
                                wo.setInstanceName("N/A");
                            }
                        }

                        parentPo.getWorkOrders().add(wo);
                    }
                }
            }
        }

        result.setProductOrders(new ArrayList<>(productOrderMap.values()));
        // Trier les Product Orders par ID ou autre critère si nécessaire
        Collections.sort(result.getProductOrders(), (po1, po2) -> po1.getId().compareTo(po2.getId()));

        result.setRollbackDetected(rollbackDetected);
        result.setRollbackDetails(rollbackDetails);

        return result;
    }
}