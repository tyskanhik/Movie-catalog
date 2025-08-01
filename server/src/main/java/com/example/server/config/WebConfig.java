package com.example.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**") //Разрешаем CORS для всех эндпоинтов
                .allowedOrigins("http://localhost:4200")  // Домен фронтенда
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Разрешенные HTTP-методы
                .allowedHeaders("*")  // Разрешенные заголовки
                .allowCredentials(true)  // Разрешаем куки и авторизацию
                .maxAge(3600);  // Время кеширования CORS-префлайт запросов (в секундах)
  }
}
