package com.example.movie_web_be.service;

import com.example.movie_web_be.dto.request.MovieRequest;
import com.example.movie_web_be.dto.response.MovieResponse;
import com.example.movie_web_be.dto.response.PageResponse;

public interface MovieService {

    MovieResponse create(MovieRequest request);

    MovieResponse update(Integer id, MovieRequest request);

    void delete(Integer id);

    MovieResponse getById(Integer id);

    MovieResponse getBySlug(String slug);

    PageResponse<MovieResponse> getAll(int page, int size, String sortBy, String sortDir);

    PageResponse<MovieResponse> search(String title, Integer year, String director, int page, int size);

    PageResponse<MovieResponse> getFeatured(int page, int size);

    PageResponse<MovieResponse> getByGenre(Integer genreId, int page, int size);

    PageResponse<MovieResponse> getByActor(Integer actorId, int page, int size);
}
