package org.example.filmservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FilmRequestDto {

    private String title;
    private String description;
    private Integer releaseYear;

    private Integer languageId;
    private Integer originalLanguageId;

    private Integer rentalDuration;
    private BigDecimal rentalRate;
    private Integer length;
    private BigDecimal replacementCost;

    private String rating;
    private String specialFeatures;
}
