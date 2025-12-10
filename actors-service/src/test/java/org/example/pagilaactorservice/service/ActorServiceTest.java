package org.example.pagilaactorservice.service;

import org.example.pagilaactorservice.entity.Actor;
import org.example.pagilaactorservice.repository.ActorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.*;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActorServiceTest {

    @Mock
    private ActorRepository repo;

    @InjectMocks
    private ActorService service;

    public ActorServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        // GIVEN
        Actor a = new Actor();
        a.setActorId(1);
        a.setFirstName("Test");
        a.setLastName("Test");

        Pageable pageable = PageRequest.of(0, 10);
        Page<Actor> page = new PageImpl<>(List.of(a));

        when(repo.findAll(pageable)).thenReturn(page);

        // WHEN
        Page<Actor> result = service.findAll(pageable);

        // THEN
        assertEquals(1, result.getTotalElements());
        verify(repo, times(1)).findAll(pageable);
    }

    @Test
    void testGetById() {
        // GIVEN
        Actor a = new Actor();
        a.setActorId(1);
        a.setFirstName("Test");

        when(repo.findById(1)).thenReturn(Optional.of(a));

        // WHEN
        Optional<Actor> result = service.findById(1);

        // THEN
        assertTrue(result.isPresent());
        assertEquals("Test", result.get().getFirstName());
    }

    @Test
    void testCreateActor() {
        // GIVEN
        Actor a = new Actor();
        a.setFirstName("Test");
        a.setLastName("Create");

        Actor saved = new Actor();
        saved.setActorId(1);
        saved.setFirstName("Test");
        saved.setLastName("Create");

        when(repo.save(a)).thenReturn(saved);

        // WHEN
        Actor result = service.create(a);

        // THEN
        assertEquals(1, result.getActorId());
        verify(repo, times(1)).save(a);
    }

    @Test
    void testUpdateActor() {
        //GIVEN
        Actor existing = new Actor();
        existing.setActorId(1);
        existing.setFirstName("Old");
        existing.setLastName("Name");

        Actor update = new Actor();
        update.setFirstName("New");
        update.setLastName("Name");

        Actor saved = new Actor();
        saved.setActorId(1);
        saved.setFirstName("New");
        saved.setLastName("Updated");

        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(saved);

        //WHEN
        Actor result = service.update(1, update);

        //THEN
        assertEquals("New", result.getFirstName());
        assertEquals("Updated", result.getLastName());

        verify(repo, times(1)).findById(1);
        verify(repo, times(1)).save(existing);
    }

    @Test
    void testDeleteActor() {
        // GIVEN
        Actor existing = new Actor();
        existing.setActorId(123);
        existing.setFirstName("Test");

        when(repo.findById(123)).thenReturn(Optional.of(existing));

        // WHEN
        service.delete(123);

        // THEN
        verify(repo, times(1)).findById(123);
        verify(repo, times(1)).delete(existing);
    }
}
