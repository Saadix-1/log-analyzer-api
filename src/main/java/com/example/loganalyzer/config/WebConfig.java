package com.example.loganalyzer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Applique la configuration CORS à tous les endpoints de l'API
                .allowedOrigins(
                        "http://localhost:3000",   // Pour les frameworks JS courants (React, Angular, Vue)
                        "http://127.0.0.1:3000",   // Variante de localhost
                        "http://localhost:8080",   // Si le frontend tourne aussi sur le port du backend
                        "http://localhost:63342"   // C'EST L'ORIGINE DE VOTRE FRONTEND SELON LE MESSAGE D'ERREUR !
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Autorise les méthodes HTTP
                .allowedHeaders("*") // Autorise tous les en-têtes
                .allowCredentials(true); // Autorise l'envoi de cookies d'authentification, etc.
    }
}