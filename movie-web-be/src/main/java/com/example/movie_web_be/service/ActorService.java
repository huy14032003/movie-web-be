package com.example.movie_web_be.service;

import com.example.movie_web_be.dto.request.ActorRequest;
import com.example.movie_web_be.dto.response.ActorResponse;
import com.example.movie_web_be.dto.response.PageResponse;

import java.util.List;

public interface ActorService {

    ActorResponse create(ActorRequest request);

    ActorResponse update(Integer id, ActorRequest request);

    void delete(Integer id);

    ActorResponse getById(Integer id);

    List<ActorResponse> getAll();

    PageResponse<ActorResponse> getAllPaged(int page, int size);

    PageResponse<ActorResponse> search(String name, int page, int size);

    PageResponse<ActorResponse> getByCountry(Integer countryId, int page, int size);
}
