package com.example.loganalyzer.controller;

import com.example.loganalyzer.service.LogAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin; // N'oublie pas d'importer cette ligne
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/log-analyzer")
@CrossOrigin(origins = "*") // Cette ligne est la clé !
public class LogAnalyzerController {

    private final LogAnalysisService logAnalysisService;

    @Autowired
    public LogAnalyzerController(LogAnalysisService logAnalysisService) {
        this.logAnalysisService = logAnalysisService;
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Map<String, Object>> analyzeLog(@PathVariable String requestId) {
        Map<String, Object> results = logAnalysisService.analyzeLog(requestId);

        if (results.containsKey("error")) {
            return ResponseEntity.status(500).body(results);
        } else {
            return ResponseEntity.ok(results);
        }
    }
}