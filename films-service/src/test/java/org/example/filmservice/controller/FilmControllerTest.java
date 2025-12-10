package org.example.filmservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.filmservice.dto.FilmRequestDto;
import org.example.filmservice.entity.Film;
import org.example.filmservice.service.FilmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FilmController.class)
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService service;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testGetAllFilmsWithFilter() throws Exception {

        // GIVEN
        Film film = new Film();
        film.setFilmId(10);
        film.setTitle("Lord of the Code");
        film.setDescription("A test film");
        film.setReleaseYear(2001);
        film.setLanguageId(1);
        film.setRentalDuration(3);
        film.setRentalRate(BigDecimal.valueOf(4.99));
        film.setReplacementCost(BigDecimal.valueOf(19.99));
        film.setLastUpdate(LocalDateTime.now());

        Page<Film> page = new PageImpl<>(List.of(film));
        Pageable pageable = PageRequest.of(0, 10);

        when(service.findAll("lord", pageable)).thenReturn(page);

        // WHEN + THEN
        mockMvc.perform(get("/api/v1/films")
                        .param("title", "lord")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].filmId").value(10))
                .andExpect(jsonPath("$.content[0].title").value("Lord of the Code"));
    }

    @Test
    void testGetFilmById() throws Exception {

        // GIVEN
        Film film = new Film();
        film.setFilmId(1);
        film.setTitle("Test");
        film.setLanguageId(1);
        film.setRentalDuration(3);
        film.setRentalRate(BigDecimal.valueOf(4.99));
        film.setReplacementCost(BigDecimal.valueOf(19.99));
        film.setLastUpdate(LocalDateTime.now());

        when(service.findById(1)).thenReturn(Optional.of(film));

        // WHEN + THEN
        mockMvc.perform(get("/api/v1/films/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.filmId").value(1));
    }

    @Test
    void testCreateFilm() throws Exception {

        // GIVEN
        FilmRequestDto req = new FilmRequestDto();
        req.setTitle("Test Film");
        req.setDescription("Desc");
        req.setReleaseYear(2020);
        req.setLanguageId(1);
        req.setRentalDuration(3);
        req.setRentalRate(BigDecimal.valueOf(4.99));
        req.setReplacementCost(BigDecimal.valueOf(19.99));

        Film saved = new Film();
        saved.setFilmId(100);
        saved.setTitle("Test Film");
        saved.setDescription("Desc");
        saved.setReleaseYear(2020);
        saved.setLanguageId(1);
        saved.setRentalDuration(3);
        saved.setRentalRate(BigDecimal.valueOf(4.99));
        saved.setReplacementCost(BigDecimal.valueOf(19.99));
        saved.setLastUpdate(LocalDateTime.now());

        when(service.create(any(FilmRequestDto.class))).thenReturn(saved);

        // WHEN + THEN
        mockMvc.perform(post("/api/v1/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.filmId").value(100))
                .andExpect(jsonPath("$.title").value("Test Film"));

        verify(service, times(1)).create(any(FilmRequestDto.class));
    }

    @Test
    void testUpdateFilm() throws Exception {

        // GIVEN
        FilmRequestDto req = new FilmRequestDto();
        req.setTitle("Updated Film");
        req.setLanguageId(1);
        req.setRentalDuration(3);
        req.setRentalRate(BigDecimal.valueOf(4.99));
        req.setReplacementCost(BigDecimal.valueOf(19.99));

        Film updated = new Film();
        updated.setFilmId(5);
        updated.setTitle("Updated Film");
        updated.setLanguageId(1);
        updated.setRentalDuration(3);
        updated.setRentalRate(BigDecimal.valueOf(4.99));
        updated.setReplacementCost(BigDecimal.valueOf(19.99));
        updated.setLastUpdate(LocalDateTime.now());

        when(service.update(eq(5), any(FilmRequestDto.class))).thenReturn(updated);

        // WHEN + THEN
        mockMvc.perform(put("/api/v1/films/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.filmId").value(5))
                .andExpect(jsonPath("$.title").value("Updated Film"));
    }

    @Test
    void testDeleteFilm() throws Exception {

        // GIVEN
        doNothing().when(service).delete(123);

        // WHEN + THEN
        mockMvc.perform(delete("/api/v1/films/123"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(123);
    }
}

