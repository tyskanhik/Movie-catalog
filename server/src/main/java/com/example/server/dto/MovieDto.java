package com.example.server.dto;

import lombok.Data;
import java.util.List;

@Data
public class MovieDto {
    private Long id;
    private String title;
    private Integer year;
    private String director;
    private String plot;
    private String posterUrl;
    private Double rating;
    private String duration;
    private String ageRating;
    private List<String> genres;
}