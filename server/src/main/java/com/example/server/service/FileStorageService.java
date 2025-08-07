package com.example.server.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

  @Value("${app.upload.dir:./media/assets}")
  private String uploadDir;

  public String storeFile(MultipartFile file) throws IOException {
    // 1. Подготовка пути
    Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

    // 2. Создание директории, если не существует
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    // 3. Генерация уникального имени файла
    String filename = generateFileName(file);
    Path targetLocation = uploadPath.resolve(filename);

    // 4. Сохранение файла
    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

    return "assets/" + filename; // Возвращаем относительный URL
  }

  private String generateFileName(MultipartFile file) {
    String uuid = UUID.randomUUID().toString().substring(0, 8);
    String originalFilename = file.getOriginalFilename();
    String extension = "";

    if (originalFilename != null && originalFilename.contains(".")) {
      extension = originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    return "poster_" + uuid + extension.toLowerCase();
  }
}