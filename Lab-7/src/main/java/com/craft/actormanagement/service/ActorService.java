package com.craft.actormanagement.service;

import com.craft.actormanagement.model.Actor;
import com.craft.actormanagement.repo.ActorRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ActorService {

    private final ActorRepo actorRepo;

    public ActorService(ActorRepo actorRepo) {
        this.actorRepo = actorRepo;
    }

    @Transactional
    public Actor createActor(Actor actor) {
        actor.setId(null); // prevent client from forcing a specific ID
        return actorRepo.save(actor);
    }

    @Transactional(readOnly = true)
    public Page<Actor> getAllActors(Pageable pageable) {
        return actorRepo.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Actor getActorById(Long id) {
        return actorRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Actor with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Actor> searchByName(String name) {
        return actorRepo.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Actor> searchByNationality(String nationality) {
        return actorRepo.findByNationality(nationality);
    }

    @Transactional
    public Actor updateActor(Long id, Actor updated) {
        Actor existing = getActorById(id);
        existing.setName(updated.getName());
        existing.setAge(updated.getAge());
        existing.setGender(updated.getGender());
        existing.setNationality(updated.getNationality());
        return actorRepo.save(existing);
    }

    @Transactional
    public void deleteActor(Long id) {
        Actor existing = getActorById(id);
        actorRepo.delete(existing);
    }
}
