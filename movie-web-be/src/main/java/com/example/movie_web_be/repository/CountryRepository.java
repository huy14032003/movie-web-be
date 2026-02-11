package com.example.movie_web_be.repository;

import com.example.movie_web_be.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    Optional<Country> findByName(String name);

    Optional<Country> findBySlug(String slug);

    boolean existsByName(String name);

    @Query("SELECT c FROM Country c WHERE " +
            "(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Country> search(@Param("name") String name, Pageable pageable);
}
