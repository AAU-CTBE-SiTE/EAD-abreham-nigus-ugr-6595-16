package com.craft.actormanagement.repo;

import com.craft.actormanagement.model.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepo extends JpaRepository<Actor, Long> {

    // Derived query — Spring Data generates the SQL automatically
    List<Actor> findByNameContainingIgnoreCase(String name);

    // Custom JPQL query with @Query
    @Query("SELECT a FROM Actor a WHERE LOWER(a.nationality) = LOWER(:nationality)")
    List<Actor> findByNationality(@Param("nationality") String nationality);

    // Pagination-aware findAll (inherited from JpaRepository, shown explicitly for clarity)
    Page<Actor> findAll(Pageable pageable);
}
