package com.example.movie_web_be.service;

import com.example.movie_web_be.dto.request.UserRatingRequest;
import com.example.movie_web_be.dto.response.PageResponse;
import com.example.movie_web_be.dto.response.UserRatingResponse;

public interface UserRatingService {

    UserRatingResponse rateMovie(UserRatingRequest request);

    PageResponse<UserRatingResponse> getByMovieId(Integer movieId, int page, int size);

    Double getAverageRating(Integer movieId);

    UserRatingResponse getByUserAndMovie(Integer userId, Integer movieId);

    void delete(Integer id);
}
