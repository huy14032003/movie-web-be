package com.example.movie_web_be.service;

import com.example.movie_web_be.dto.request.CommentRequest;
import com.example.movie_web_be.dto.response.CommentResponse;
import com.example.movie_web_be.dto.response.PageResponse;

public interface CommentService {

    CommentResponse create(CommentRequest request);

    PageResponse<CommentResponse> getByMovieId(Integer movieId, int page, int size);

    CommentResponse update(Integer id, String content);

    void delete(Integer id);
}
