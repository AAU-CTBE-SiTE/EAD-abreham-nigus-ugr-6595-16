package com.site.actormanagement.controller;

import com.site.actormanagement.model.Actor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ActorController {

    List<Actor> actors = new ArrayList<>();

    public ActorController() {
        actors.add(new Actor(1, "John Doe", "The Great Adventure"));
        actors.add(new Actor(2, "Jane Smith", "Mystery Island"));
    }

    // CRUD -- READ (all)
    @GetMapping("/actor")
    public List<Actor> getActorDetails() {
        return actors;
    }

    // CRUD -- READ (one)
    @GetMapping("/actor/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable int id) {
        for (Actor actor : actors) {
            if (actor.getId() == id) {
                return ResponseEntity.ok(actor);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // CRUD -- CREATE
    @PostMapping("/actor")
    public ResponseEntity<Actor> addActor(@RequestBody Actor actor) {
        actors.add(actor);
        return ResponseEntity.status(HttpStatus.CREATED).body(actor);
    }

    // CRUD -- UPDATE
    @PutMapping("/actor/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable int id, @RequestBody Actor updated) {
        for (Actor actor : actors) {
            if (actor.getId() == id) {
                actor.setName(updated.getName());
                actor.setMovieName(updated.getMovieName());
                return ResponseEntity.ok(actor);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // CRUD -- DELETE
    @DeleteMapping("/actor/{id}")
    public ResponseEntity<String> deleteActor(@PathVariable int id) {
        boolean removed = actors.removeIf(actor -> actor.getId() == id);
        if (removed) {
            return ResponseEntity.ok("Actor deleted");
        }
        return ResponseEntity.notFound().build();
    }
}