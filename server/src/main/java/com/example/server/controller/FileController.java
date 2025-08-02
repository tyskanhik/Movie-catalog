package com.example.server.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/media")
public class FileController {

  private final Path rootLocation = Paths.get("./media").toAbsolutePath().normalize();

  @GetMapping("/**/{filename:.+}")
  public ResponseEntity<Resource> serveFile(HttpServletRequest request) {
    try {
      String pathAfterMedia = request.getRequestURI()
          .substring(request.getContextPath().length() + "/media/".length());

      Path file = rootLocation.resolve(pathAfterMedia).normalize();

      if (!file.startsWith(rootLocation)) {
        return ResponseEntity.badRequest().build();
      }

      Resource resource = new UrlResource(file.toUri());

      if (!resource.exists() || !resource.isReadable()) {
        return ResponseEntity.notFound().build();
      }

      String contentType = determineContentType(file.getFileName().toString());

      return ResponseEntity.ok()
          .contentType(MediaType.parseMediaType(contentType))
          .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
          .body(resource);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  private String determineContentType(String filename) {
    String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    return switch (extension) {
      case "jpg", "jpeg" -> "image/jpeg";
      case "png" -> "image/png";
      case "gif" -> "image/gif";
      case "webp" -> "image/webp";
      default -> "application/octet-stream";
    };
  }
}