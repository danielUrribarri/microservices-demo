package org.example.filmservice.repository;

import org.example.filmservice.entity.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Integer> {

    Page<Film> findByTitleContainingIgnoreCase(String title, Pageable pageable);

}

