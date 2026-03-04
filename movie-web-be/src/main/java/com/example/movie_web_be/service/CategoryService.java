package com.example.movie_web_be.service;

import com.example.movie_web_be.dto.request.CategoryRequest;
import com.example.movie_web_be.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    CategoryResponse getById(Integer id);

    List<CategoryResponse> getAll();

    CategoryResponse update(Integer id, CategoryRequest request);

    void delete(Integer id);
}
