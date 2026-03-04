package com.example.movie_web_be.service.impl;

import com.example.movie_web_be.dto.request.UserRatingRequest;
import com.example.movie_web_be.dto.response.PageResponse;
import com.example.movie_web_be.dto.response.UserRatingResponse;
import com.example.movie_web_be.entity.Movie;
import com.example.movie_web_be.entity.User;
import com.example.movie_web_be.entity.UserRating;
import com.example.movie_web_be.exception.ResourceNotFoundException;
import com.example.movie_web_be.repository.MovieRepository;
import com.example.movie_web_be.repository.UserRatingRepository;
import com.example.movie_web_be.repository.UserRepository;
import com.example.movie_web_be.service.UserRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRatingServiceImpl implements UserRatingService {

    private final UserRatingRepository userRatingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Override
    public UserRatingResponse rateMovie(UserRatingRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng", "id", request.getUserId()));

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Phim", "id", request.getMovieId()));

        Optional<UserRating> existing = userRatingRepository
                .findByUserIdAndMovieId(request.getUserId(), request.getMovieId());

        UserRating userRating;
        if (existing.isPresent()) {
            userRating = existing.get();
            userRating.setRating(request.getRating());
        } else {
            userRating = UserRating.builder()
                    .user(user)
                    .movie(movie)
                    .rating(request.getRating())
                    .build();
        }

        return toResponse(userRatingRepository.save(userRating));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserRatingResponse> getByMovieId(Integer movieId, int page, int size) {
        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Phim", "id", movieId);
        }

        Page<UserRating> pageResult = userRatingRepository.findByMovieId(movieId, PageRequest.of(page, size));

        return PageResponse.<UserRatingResponse>builder()
                .content(pageResult.getContent().stream().map(this::toResponse).collect(Collectors.toList()))
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .first(pageResult.isFirst())
                .last(pageResult.isLast())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Double getAverageRating(Integer movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Phim", "id", movieId);
        }
        Double avg = userRatingRepository.getAverageRatingByMovieId(movieId);
        return avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0;
    }

    @Override
    @Transactional(readOnly = true)
    public UserRatingResponse getByUserAndMovie(Integer userId, Integer movieId) {
        return userRatingRepository.findByUserIdAndMovieId(userId, movieId)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Đánh giá", "userId/movieId", userId + "/" + movieId));
    }

    @Override
    public void delete(Integer id) {
        if (!userRatingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Đánh giá", "id", id);
        }
        userRatingRepository.deleteById(id);
    }

    private UserRatingResponse toResponse(UserRating userRating) {
        return UserRatingResponse.builder()
                .id(userRating.getId())
                .userId(userRating.getUser().getId())
                .username(userRating.getUser().getUsername())
                .movieId(userRating.getMovie().getId())
                .rating(userRating.getRating())
                .createdAt(userRating.getCreatedAt())
                .build();
    }
}
