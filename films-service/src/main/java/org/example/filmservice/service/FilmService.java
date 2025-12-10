package org.example.filmservice.service;

import lombok.RequiredArgsConstructor;
import org.example.filmservice.dto.FilmRequestDto;
import org.example.filmservice.entity.Film;
import org.example.filmservice.entity.Rating;
import org.example.filmservice.repository.FilmRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository repo;

    public Page<Film> findAll(String title, Pageable pageable) {
        if (title == null) title = "";
        return repo.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Optional<Film> findById(Integer id) {
        return repo.findById(id);
    }

    public Film create(FilmRequestDto req) {

        Film film = new Film();
        film.setTitle(req.getTitle());
        film.setDescription(req.getDescription());
        film.setReleaseYear(req.getReleaseYear());

        film.setLanguageId(req.getLanguageId());
        film.setOriginalLanguageId(req.getOriginalLanguageId());
        film.setRentalDuration(req.getRentalDuration());
        film.setRentalRate(req.getRentalRate());
        film.setLength(req.getLength());
        film.setReplacementCost(req.getReplacementCost());

        if (req.getRating() != null) {
            film.setRating(Rating.fromDatabase(req.getRating()));
        }

        if (req.getSpecialFeatures() != null && !req.getSpecialFeatures().isEmpty()) {
            film.setSpecialFeatures(List.of(req.getSpecialFeatures().split(",")));
        }

        film.setLastUpdate(LocalDateTime.now());

        return repo.save(film);
    }

    public Film update(Integer id, FilmRequestDto req) {

        Film film = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found"));

        film.setTitle(req.getTitle());
        film.setDescription(req.getDescription());
        film.setReleaseYear(req.getReleaseYear());
        film.setLanguageId(req.getLanguageId());
        film.setOriginalLanguageId(req.getOriginalLanguageId());
        film.setRentalDuration(req.getRentalDuration());
        film.setRentalRate(req.getRentalRate());
        film.setLength(req.getLength());
        film.setReplacementCost(req.getReplacementCost());

        if (req.getRating() != null) {
            film.setRating(Rating.fromDatabase(req.getRating()));
        }


        if (req.getSpecialFeatures() != null && !req.getSpecialFeatures().isEmpty()) {
            film.setSpecialFeatures(List.of(req.getSpecialFeatures().split(",")));
        }

        film.setLastUpdate(LocalDateTime.now());

        return repo.save(film);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}

