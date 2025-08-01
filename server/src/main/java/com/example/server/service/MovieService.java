package com.example.server.service;

import com.example.server.model.Genre;
import com.example.server.model.Movie;
import com.example.server.dto.MovieDto;
import com.example.server.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream()
            .map(this::convertToDto)
            .toList();
    }
    
    private MovieDto convertToDto(Movie movie) {
        MovieDto dto = new MovieDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setYear(movie.getYear());
        dto.setDirector(movie.getDirector());
        dto.setPlot(movie.getPlot());
        dto.setPosterUrl(movie.getPosterUrl());
        dto.setRating(movie.getRating());
        dto.setDuration(movie.getDuration());
        dto.setAgeRating(movie.getAgeRating());
        
        dto.setGenres(movie.getGenres().stream()
            .map(Genre::getName)
            .toList());
            
        return dto;
    }
}