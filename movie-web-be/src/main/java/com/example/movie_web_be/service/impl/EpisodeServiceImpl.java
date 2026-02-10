package com.example.movie_web_be.service.impl;

import com.example.movie_web_be.dto.request.EpisodeRequest;
import com.example.movie_web_be.dto.response.EpisodeResponse;
import com.example.movie_web_be.entity.Episode;
import com.example.movie_web_be.entity.Movie;
import com.example.movie_web_be.exception.DuplicateResourceException;
import com.example.movie_web_be.exception.ResourceNotFoundException;
import com.example.movie_web_be.repository.EpisodeRepository;
import com.example.movie_web_be.repository.MovieRepository;
import com.example.movie_web_be.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EpisodeServiceImpl implements EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final MovieRepository movieRepository;

    @Override
    public EpisodeResponse create(Integer movieId, EpisodeRequest request) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Phim", "id", movieId));

        if (episodeRepository.existsByMovieIdAndEpisodeNumber(movieId, request.getEpisodeNumber())) {
            throw new DuplicateResourceException("Tập phim", "episodeNumber", request.getEpisodeNumber());
        }

        Episode episode = Episode.builder()
                .movie(movie)
                .title(request.getTitle())
                .episodeNumber(request.getEpisodeNumber())
                .videoUrl(request.getVideoUrl())
                .serverName(request.getServerName())
                .quality(request.getQuality())
                .build();

        return toResponse(episodeRepository.save(episode));
    }

    @Override
    public EpisodeResponse update(Integer id, EpisodeRequest request) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tập phim", "id", id));

        if (!episode.getEpisodeNumber().equals(request.getEpisodeNumber())
                && episodeRepository.existsByMovieIdAndEpisodeNumber(episode.getMovie().getId(),
                        request.getEpisodeNumber())) {
            throw new DuplicateResourceException("Tập phim", "episodeNumber", request.getEpisodeNumber());
        }

        episode.setTitle(request.getTitle());
        episode.setEpisodeNumber(request.getEpisodeNumber());
        episode.setVideoUrl(request.getVideoUrl());
        episode.setServerName(request.getServerName());
        episode.setQuality(request.getQuality());

        return toResponse(episodeRepository.save(episode));
    }

    @Override
    public void delete(Integer id) {
        if (!episodeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tập phim", "id", id);
        }
        episodeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public EpisodeResponse getById(Integer id) {
        return episodeRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Tập phim", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EpisodeResponse> getByMovieId(Integer movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Phim", "id", movieId);
        }
        return episodeRepository.findByMovieIdOrderByEpisodeNumberAsc(movieId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private EpisodeResponse toResponse(Episode episode) {
        return EpisodeResponse.builder()
                .id(episode.getId())
                .movieId(episode.getMovie().getId())
                .title(episode.getTitle())
                .episodeNumber(episode.getEpisodeNumber())
                .videoUrl(episode.getVideoUrl())
                .serverName(episode.getServerName())
                .quality(episode.getQuality())
                .createdAt(episode.getCreatedAt())
                .build();
    }
}
