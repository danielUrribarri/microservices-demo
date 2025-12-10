package org.example.pagilaactorservice.controller;

import org.example.pagilaactorservice.entity.Actor;
import org.example.pagilaactorservice.service.ActorService;
import org.example.pagilaactorservice.dto.ActorCreateRequest;
import org.example.pagilaactorservice.dto.ActorUpdateRequest;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/actors")
@CrossOrigin(origins = "http://localhost:4200")
public class ActorController {

    private final ActorService service;

    public ActorController(ActorService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Actor> getAllActors(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            Pageable pageable
    ) {
        return service.findAll(firstName, lastName, pageable);
    }

    @GetMapping("/{actor_id}")
    public Actor getActorById(@PathVariable Integer actor_id) {
        return service.findById(actor_id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Actor not found with id " + actor_id
                        ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Actor postActor(@RequestBody @Valid ActorCreateRequest request) {
        Actor actor = new Actor();
        actor.setFirstName(request.getFirstName());
        actor.setLastName(request.getLastName());
        return service.create(actor);
    }

    @PutMapping("/{actor_id}")
    public Actor updateActor(
            @PathVariable Integer actor_id,
            @RequestBody @Valid ActorUpdateRequest request
    ) {
        Actor actor = new Actor();
        actor.setFirstName(request.getFirstName());
        actor.setLastName(request.getLastName());
        return service.update(actor_id, actor);
    }

    @DeleteMapping("/{actor_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActor(@PathVariable Integer actor_id) {
        service.delete(actor_id);
    }
}

