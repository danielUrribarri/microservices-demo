package org.example.filmservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.filmservice.dto.FilmRequestDto;
import org.example.filmservice.entity.Film;
import org.example.filmservice.service.FilmService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/films")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService service;

    @GetMapping
    public Page<Film> getAllFilms(
            @RequestParam(required = false) String title,
            Pageable pageable
    ) {
        return service.findAll(title, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@RequestBody FilmRequestDto request) {
        Film created = service.create(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(
            @PathVariable Integer id,
            @RequestBody FilmRequestDto request
    ) {
        Film updated = service.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

