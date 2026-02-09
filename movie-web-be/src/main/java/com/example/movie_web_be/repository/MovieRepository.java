package com.example.movie_web_be.repository;

import com.example.movie_web_be.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Optional<Movie> findBySlug(String slug);

    Page<Movie> findByFeaturedTrue(Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE " +
            "(:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:year IS NULL OR m.year = :year) AND " +
            "(:director IS NULL OR LOWER(m.director) LIKE LOWER(CONCAT('%', :director, '%')))")
    Page<Movie> search(
            @Param("title") String title,
            @Param("year") Integer year,
            @Param("director") String director,
            Pageable pageable
    );

    @Query("SELECT DISTINCT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId")
    Page<Movie> findByGenreId(@Param("genreId") Integer genreId, Pageable pageable);

    @Query("SELECT DISTINCT m FROM Movie m JOIN m.actors a WHERE a.id = :actorId")
    Page<Movie> findByActorId(@Param("actorId") Integer actorId, Pageable pageable);

    boolean existsBySlug(String slug);
}
