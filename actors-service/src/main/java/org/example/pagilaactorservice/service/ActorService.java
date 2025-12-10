package org.example.pagilaactorservice.service;

import org.example.pagilaactorservice.entity.Actor;
import org.example.pagilaactorservice.repository.ActorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActorService {

    private final ActorRepository repo;

    public ActorService(ActorRepository repo) {
        this.repo = repo;
    }

    public Page<Actor> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<Actor> findAll(String firstName, String lastName, Pageable pageable) {

        if (firstName == null && lastName == null) return repo.findAll(pageable);

        if (firstName == null) firstName = "";
        if (lastName == null)  lastName  = "";

        return repo.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(
                firstName,
                lastName,
                pageable
        );
    }

    public Optional<Actor> findById(Integer actor_id) {
        return repo.findById(actor_id);
    }

    public Actor create(Actor actor) {
        return repo.save(actor);
    }

    public Actor update(Integer actor_id, Actor body) {
        Actor existing = repo.findById(actor_id)
                .orElseThrow(() -> new RuntimeException("Actor not found"));

        existing.setFirstName(body.getFirstName());
        existing.setLastName(body.getLastName());

        return repo.save(existing);
    }

    public void delete(Integer actorId) {
        Actor existing = repo.findById(actorId)
                .orElseThrow(() -> new RuntimeException("Actor not found"));

        repo.delete(existing);
    }
}
