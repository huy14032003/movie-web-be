package com.example.movie_web_be.repository;

import com.example.movie_web_be.entity.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

    Optional<Actor> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT a FROM Actor a WHERE " +
            "(:name IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Actor> search(@Param("name") String name, Pageable pageable);
}
