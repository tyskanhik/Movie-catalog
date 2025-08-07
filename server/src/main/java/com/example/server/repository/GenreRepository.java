package com.example.server.repository;

import com.example.server.model.Genre;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
  Optional<Genre> findByName(String name);
}