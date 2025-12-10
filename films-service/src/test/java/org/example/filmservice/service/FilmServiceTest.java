package org.example.filmservice.service;

import org.example.filmservice.dto.FilmRequestDto;
import org.example.filmservice.entity.Film;
import org.example.filmservice.repository.FilmRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FilmServiceTest {

    private final FilmRepository repo = mock(FilmRepository.class);
    private final FilmService service = new FilmService(repo);

    @Test
    void testFindById() {
        // GIVEN
        Film film = new Film();
        film.setFilmId(10);
        when(repo.findById(10)).thenReturn(Optional.of(film));

        // WHEN
        Optional<Film> result = service.findById(10);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(10, result.get().getFilmId());
    }

    @Test
    void testCreateFilm() {

        // GIVEN
        FilmRequestDto req = new FilmRequestDto();
        req.setTitle("Test Film");
        req.setLanguageId(1);
        req.setRentalDuration(3);
        req.setRentalRate(BigDecimal.valueOf(4.99));
        req.setReplacementCost(BigDecimal.valueOf(19.99));

        ArgumentCaptor<Film> captor = ArgumentCaptor.forClass(Film.class);

        Film saved = new Film();
        saved.setFilmId(99);

        when(repo.save(any(Film.class))).thenReturn(saved);

        // WHEN
        Film result = service.create(req);

        // THEN
        verify(repo).save(captor.capture());

        Film filmSent = captor.getValue();
        assertEquals("Test Film", filmSent.getTitle());
        assertNotNull(filmSent.getLastUpdate());
        assertEquals(99, result.getFilmId());
    }

    @Test
    void testUpdateFilm() {

        // GIVEN
        Film existing = new Film();
        existing.setFilmId(50);
        existing.setTitle("Old Name");
        existing.setLanguageId(1);
        existing.setRentalDuration(3);
        existing.setRentalRate(BigDecimal.valueOf(4.99));
        existing.setReplacementCost(BigDecimal.valueOf(19.99));

        when(repo.findById(50)).thenReturn(Optional.of(existing));

        FilmRequestDto req = new FilmRequestDto();
        req.setTitle("New Title");
        req.setLanguageId(1);
        req.setRentalDuration(3);
        req.setRentalRate(BigDecimal.valueOf(4.99));
        req.setReplacementCost(BigDecimal.valueOf(19.99));

        Film updated = new Film();
        updated.setFilmId(50);
        updated.setTitle("New Title");
        updated.setLastUpdate(LocalDateTime.now());

        when(repo.save(any(Film.class))).thenReturn(updated);

        // WHEN
        Film result = service.update(50, req);

        // THEN
        assertEquals("New Title", result.getTitle());
        verify(repo).save(existing);
    }
}
