package com.example.server.service;

import com.example.server.dto.MovieDto;
import com.example.server.model.Genre;
import com.example.server.model.Movie;
import com.example.server.repository.GenreRepository;
import com.example.server.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final FileStorageService fileStorageService;

    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional
    public MovieDto createMovie(MovieDto movieDto, MultipartFile posterFile) throws IOException {
        // 1. Сохраняем постер
        String posterUrl = fileStorageService.storeFile(posterFile);

        // 2. Создаем фильм
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setYear(movieDto.getYear());
        movie.setDirector(movieDto.getDirector());
        movie.setPlot(movieDto.getPlot());
        movie.setPosterUrl(posterUrl);
        movie.setRating(movieDto.getRating());
        movie.setDuration(movieDto.getDuration());
        movie.setAgeRating(movieDto.getAgeRating());

        // 3. Обрабатываем жанры
        Set<Genre> genres = movieDto.getGenres().stream()
                .map(genreName -> genreRepository.findByName(genreName)
                        .orElseGet(() -> {
                            Genre newGenre = new Genre();
                            newGenre.setName(genreName);
                            return genreRepository.save(newGenre);
                        }))
                .collect(Collectors.toSet());

        movie.setGenres(genres);

        // 4. Сохраняем фильм
        Movie savedMovie = movieRepository.save(movie);
        return convertToDto(savedMovie);
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