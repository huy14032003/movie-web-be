package com.example.movie_web_be.service;

import com.example.movie_web_be.dto.request.GenreRequest;
import com.example.movie_web_be.dto.response.GenreResponse;
import com.example.movie_web_be.dto.response.PageResponse;

import java.util.List;

public interface GenreService {

    GenreResponse create(GenreRequest request);

    GenreResponse update(Integer id, GenreRequest request);

    void delete(Integer id);

    GenreResponse getById(Integer id);

    List<GenreResponse> getAll();

    PageResponse<GenreResponse> getAllPaged(int page, int size);

    PageResponse<GenreResponse> search(String name, int page, int size);
}
