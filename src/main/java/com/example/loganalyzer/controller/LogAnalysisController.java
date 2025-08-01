package com.example.loganalyzer.controller;

import com.example.loganalyzer.model.AnalysisResult;
import com.example.loganalyzer.service.LogAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/logs")
public class LogAnalysisController {

    private final LogAnalysisService logAnalysisService;

    @Autowired
    public LogAnalysisController(LogAnalysisService logAnalysisService) {
        this.logAnalysisService = logAnalysisService;
    }

    /**
     * Endpoint pour télécharger un fichier de log et lancer son analyse.
     * @param file Le fichier de log à analyser, de type MultipartFile.
     * @param requestId L'ID de la requête à rechercher dans le log.
     * @return Un objet AnalysisResult contenant le résultat de l'analyse.
     */
    @PostMapping("/analyze")
    public ResponseEntity<AnalysisResult> analyzeLog(
            @RequestParam("file") MultipartFile file,
            @RequestParam("requestId") String requestId) {
        try {
            AnalysisResult result = logAnalysisService.analyzeLogFile(file, requestId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
