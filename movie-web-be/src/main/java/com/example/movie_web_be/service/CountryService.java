package com.example.movie_web_be.service;

import com.example.movie_web_be.dto.request.CountryRequest;
import com.example.movie_web_be.dto.response.CountryResponse;
import com.example.movie_web_be.dto.response.PageResponse;

import java.util.List;

public interface CountryService {

    CountryResponse create(CountryRequest request);

    CountryResponse update(Integer id, CountryRequest request);

    void delete(Integer id);

    CountryResponse getById(Integer id);

    List<CountryResponse> getAll();

    PageResponse<CountryResponse> getAllPaged(int page, int size);

    PageResponse<CountryResponse> search(String name, int page, int size);
}
