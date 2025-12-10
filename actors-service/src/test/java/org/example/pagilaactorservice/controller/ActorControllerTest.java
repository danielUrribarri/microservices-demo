package org.example.pagilaactorservice.controller;

import org.example.pagilaactorservice.entity.Actor;
import org.example.pagilaactorservice.service.ActorService;
import org.example.pagilaactorservice.dto.ActorCreateRequest;
import org.example.pagilaactorservice.dto.ActorUpdateRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.http.MediaType;

import org.springframework.data.domain.*;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ActorController.class)
@ImportAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
})
class ActorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorService service;

    @Test
    void testGetAllActors() throws Exception {
        //GIVEN
        Actor a = new Actor();
        a.setActorId(111);
        a.setFirstName("Test");
        a.setLastName("Test");

        Page<Actor> page = new PageImpl<>(List.of(a));
        Pageable pageable = PageRequest.of(0, 10, Sort.by("first_name"));

        when(service.findAll(eq(null), eq(null), any(Pageable.class))).thenReturn(page);

        //WHEN
        mockMvc.perform(get("/api/v1/actors"))
                //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].actorId").value(111));
    }

    @Test
    void testGetAllActorsWithFilters() throws Exception {
        // GIVEN
        Actor a = new Actor();
        a.setActorId(55);
        a.setFirstName("Albert");
        a.setLastName("Berg");

        Page<Actor> page = new PageImpl<>(List.of(a));
        Pageable pageable = PageRequest.of(0, 10, Sort.by("first_name"));

        when(service.findAll(eq("al"), eq("be"), any(Pageable.class))).thenReturn(page);

        //WHEN
        mockMvc.perform(
                        get("/api/v1/actors")
                                .param("firstName", "al")
                                .param("lastName", "be")
                )
                //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].actorId").value(55))
                .andExpect(jsonPath("$.content[0].firstName").value("Albert"))
                .andExpect(jsonPath("$.content[0].lastName").value("Berg"));
    }

    @Test
    void testGetActorById() throws Exception {
        //GIVEN
        Actor a = new Actor();
        a.setActorId(1);
        a.setFirstName("Test");
        a.setLastName("Test");

        when(service.findById(1)).thenReturn(Optional.of(a));

        //WHEN
        mockMvc.perform(get("/api/v1/actors/1"))

                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actorId").value(1));
    }

    @Test
    void testPostActor() throws Exception {
        //GIVEN
        ActorCreateRequest request = new ActorCreateRequest();
        request.setFirstName("Test");
        request.setLastName("Test");

        Actor saved = new Actor();
        saved.setActorId(123);
        saved.setFirstName("Test");
        saved.setLastName("Test");

        when(service.create(any(Actor.class))).thenReturn(saved);

        //WHEN
        mockMvc.perform(
                post("/api/v1/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.actorId").value(123))
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("Test"));

        //THEN
        verify(service, times(1)).create(any(Actor.class));
    }

    @Test
    void testUpdateActor() throws Exception {
        //GIVEN
        ActorUpdateRequest request = new ActorUpdateRequest();
        request.setFirstName("Updated");
        request.setLastName("Name");

        Actor updated = new Actor();
        updated.setActorId(123);
        updated.setFirstName("Updated");
        updated.setLastName("Name");

        when(service.update(eq(123), any(Actor.class))).thenReturn(updated);

        //WHEN
        mockMvc.perform(
                put("/api/v1/actors/123")
                        .contentType("application/json")
                                .content(new ObjectMapper().writeValueAsString(request))
        )
        //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actorId").value(123))
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("Name"));
    }

    @Test
    void testDeleteActor() throws Exception {
        //GIVEN
        doNothing().when(service).delete(123);

        //WHEN
        mockMvc.perform(delete("/api/v1/actors/123"))
                .andExpect(status().isNoContent());
        //THEN
        verify(service, times(1)).delete(123);
    }
}