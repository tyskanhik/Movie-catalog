package com.example.server.controller;

import com.example.server.dto.MovieDto;
import com.example.server.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    
    @GetMapping
    public List<MovieDto> getAllMovies() {
        return movieService.getAllMovies();
    }
}