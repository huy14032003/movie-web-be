package com.example.movie_web_be.service;

import com.example.movie_web_be.dto.request.UserRequest;
import com.example.movie_web_be.dto.response.PageResponse;
import com.example.movie_web_be.dto.response.UserResponse;

public interface UserService {

    UserResponse create(UserRequest request);

    UserResponse getById(Integer id);

    PageResponse<UserResponse> getAll(int page, int size);

    UserResponse update(Integer id, UserRequest request);

    void delete(Integer id);
}
