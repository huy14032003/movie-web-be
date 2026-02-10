package com.example.movie_web_be.repository;

import com.example.movie_web_be.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Integer> {

    List<Episode> findByMovieIdOrderByEpisodeNumberAsc(Integer movieId);

    Optional<Episode> findByMovieIdAndEpisodeNumber(Integer movieId, Integer episodeNumber);

    boolean existsByMovieIdAndEpisodeNumber(Integer movieId, Integer episodeNumber);
}
