package com.example.movie_web_be.service;

import com.example.movie_web_be.dto.request.EpisodeRequest;
import com.example.movie_web_be.dto.response.EpisodeResponse;

import java.util.List;

public interface EpisodeService {

    EpisodeResponse create(Integer movieId, EpisodeRequest request);

    EpisodeResponse update(Integer id, EpisodeRequest request);

    void delete(Integer id);

    EpisodeResponse getById(Integer id);

    List<EpisodeResponse> getByMovieId(Integer movieId);
}
