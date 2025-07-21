package com.example.loganalyzer.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
        import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service // Indique à Spring que c'est un composant de service
public class LogAnalysisService {

    // Reprends les classes internes de ton ancien LogAnalyzer si tu en avais,
    // mais dans ton cas, tout est dans la méthode principale.

    public Map<String, Object> analyzeLog(String requestId) {
        Map<String, Object> results = new HashMap<>();

        List<String> poInstances = new ArrayList<>();
        List<String> woInstances = new ArrayList<>();
        List<String> soInstances = new ArrayList<>();
        String accountCaller = "Non trouvé"; // Initialise à "Non trouvé" directement

        Long swappedInTime = null;
        Long deletedTime = null;

        // Modification ici : lire le fichier depuis les ressources ou un chemin configurable
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("kpsaOrder.log");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) { // Utilisation d'InputStream pour les ressources

            String line;
            while ((line = reader.readLine()) != null) {
                // GET ACCOUNT line
                if (line.contains("GET ACCOUNT") && line.contains(requestId)) {
                    Pattern pattern = Pattern.compile("ACCOUNT : ([^|]+)");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        accountCaller = matcher.group(1).trim();
                    }
                }

                // PO - Product Order
                if (line.contains("Product Order Execution ENDED") && line.contains(requestId)) {
                    Pattern pattern = Pattern.compile("INSTANCE NAME : ([^|]+).*STATUS : ([A-Z]+)");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String instance = matcher.group(1).trim();
                        String status = matcher.group(2).trim();
                        poInstances.add(instance + " = " + status);
                    }
                }

                // WO - Work Order
                if (line.contains("Cartridge WO Execution ENDED") && line.contains(requestId)) {
                    Pattern pattern = Pattern.compile("INSTANCE NAME : ([^|]+).*STATUS : ([A-Z]+)");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String instance = matcher.group(1).trim();
                        String status = matcher.group(2).trim();
                        woInstances.add(instance + " = " + status);
                    }
                }

                // SO - Service Order
                if (line.contains("ServiceOrderData SWAPPED IN") && line.contains(requestId)) {
                    swappedInTime = extractTimestampMillis(line);
                }
                if (line.contains("ServiceOrderData DELETED") && line.contains(requestId)) {
                    deletedTime = extractTimestampMillis(line);
                    Pattern pattern = Pattern.compile("INSTANCE NAME : ([^|]+).*STATUS : ([A-Z]+)");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String instance = matcher.group(1).trim();
                        String status = matcher.group(2).trim();
                        soInstances.add(instance + " = " + status);
                    }
                }
            }

            // --- Préparation des résultats pour le retour JSON ---
            results.put("requestId", requestId);
            results.put("accountCaller", accountCaller);
            results.put("productOrders", poInstances.isEmpty() ? "Aucun PO trouvé." : poInstances);
            results.put("workOrders", woInstances.isEmpty() ? "Aucun WO trouvé." : woInstances);
            results.put("serviceOrders", soInstances.isEmpty() ? "Aucun SO trouvé." : soInstances);

            if (swappedInTime != null && deletedTime != null) {
                long durationMillis = deletedTime - swappedInTime;
                results.put("serviceOrderProcessingTimeMillis", durationMillis);
            } else {
                results.put("serviceOrderProcessingTime", "Données incomplètes");
            }

        } catch (IOException e) {
            // Dans un service, on ne fait pas de System.err.println direct, on logge ou on propage l'exception
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
            results.put("error", "Erreur lors de la lecture du fichier de log: " + e.getMessage());
            return results; // Retourne les résultats partiels avec l'erreur
        } catch (Exception e) { // Capture d'autres exceptions inattendues
            System.err.println("Une erreur inattendue est survenue : " + e.getMessage());
            results.put("error", "Une erreur inattendue est survenue: " + e.getMessage());
            return results;
        }

        return results;
    }

    private Long extractTimestampMillis(String line) {
        Pattern pattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String timestampStr = matcher.group(1);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(timestampStr);
                return date.getTime();
            } catch (ParseException e) {
                // Log l'erreur si nécessaire, mais retourne null pour indiquer l'échec
                System.err.println("Erreur de parsing de la date: " + timestampStr + " - " + e.getMessage());
                return null;
            }
        }
        return null;
    }
}