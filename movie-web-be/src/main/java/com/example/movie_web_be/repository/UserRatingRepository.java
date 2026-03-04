package com.example.movie_web_be.repository;

import com.example.movie_web_be.entity.UserRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRatingRepository extends JpaRepository<UserRating, Integer> {

    Optional<UserRating> findByUserIdAndMovieId(Integer userId, Integer movieId);

    Page<UserRating> findByMovieId(Integer movieId, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM UserRating r WHERE r.movie.id = :movieId")
    Double getAverageRatingByMovieId(@Param("movieId") Integer movieId);
}
