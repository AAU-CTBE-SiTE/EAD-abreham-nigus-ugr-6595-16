package com.craft.actormanagement.controller;

import com.craft.actormanagement.model.Actor;
import com.craft.actormanagement.service.ActorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    // POST /api/actors
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Actor createActor(@Valid @RequestBody Actor actor) {
        return actorService.createActor(actor);
    }

    // GET /api/actors?page=0&size=10&sort=name,asc
    @GetMapping
    public Page<Actor> getAllActors(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return actorService.getAllActors(pageable);
    }

    // GET /api/actors/search?name=brad
    @GetMapping("/search")
    public List<Actor> searchByName(@RequestParam String name) {
        return actorService.searchByName(name);
    }

    // GET /api/actors/nationality?nationality=Ethiopian
    @GetMapping("/nationality")
    public List<Actor> searchByNationality(@RequestParam String nationality) {
        return actorService.searchByNationality(nationality);
    }

    // GET /api/actors/{id}
    @GetMapping("/{id}")
    public Actor getActorById(@PathVariable Long id) {
        return actorService.getActorById(id);
    }

    // PUT /api/actors/{id}
    @PutMapping("/{id}")
    public Actor updateActor(@PathVariable Long id, @Valid @RequestBody Actor actor) {
        return actorService.updateActor(id, actor);
    }

    // DELETE /api/actors/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
    }
}
