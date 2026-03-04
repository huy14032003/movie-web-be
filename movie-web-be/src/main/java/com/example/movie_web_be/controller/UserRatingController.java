package com.example.movie_web_be.controller;

import com.example.movie_web_be.dto.request.PageRequest;
import com.example.movie_web_be.dto.request.UserRatingRequest;
import com.example.movie_web_be.dto.response.ApiResponse;
import com.example.movie_web_be.dto.response.PageResponse;
import com.example.movie_web_be.dto.response.UserRatingResponse;
import com.example.movie_web_be.service.UserRatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
@Tag(name = "Rating", description = "API đánh giá phim")
public class UserRatingController {

    private final UserRatingService userRatingService;

    @PostMapping
    @Operation(summary = "Đánh giá phim", description = "Nếu user đã đánh giá phim này thì cập nhật điểm mới")
    public ResponseEntity<ApiResponse<UserRatingResponse>> rate(@Valid @RequestBody UserRatingRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Đánh giá thành công", userRatingService.rateMovie(request)));
    }

    @GetMapping("/movie/{movieId}")
    @Operation(summary = "Lấy danh sách đánh giá theo phim")
    public ResponseEntity<ApiResponse<PageResponse<UserRatingResponse>>> getByMovieId(
            @Parameter(description = "ID phim", required = true) @PathVariable Integer movieId,
            @ModelAttribute PageRequest pageRequest) {
        return ResponseEntity.ok(ApiResponse
                .success(userRatingService.getByMovieId(movieId, pageRequest.getPage(), pageRequest.getSize())));
    }

    @GetMapping("/movie/{movieId}/average")
    @Operation(summary = "Lấy điểm trung bình của phim")
    public ResponseEntity<ApiResponse<Double>> getAverage(
            @Parameter(description = "ID phim", required = true) @PathVariable Integer movieId) {
        return ResponseEntity.ok(ApiResponse.success(userRatingService.getAverageRating(movieId)));
    }

    @GetMapping("/user/{userId}/movie/{movieId}")
    @Operation(summary = "Lấy đánh giá của một user cho một phim cụ thể")
    public ResponseEntity<ApiResponse<UserRatingResponse>> getByUserAndMovie(
            @Parameter(description = "ID người dùng", required = true) @PathVariable Integer userId,
            @Parameter(description = "ID phim", required = true) @PathVariable Integer movieId) {
        return ResponseEntity.ok(ApiResponse.success(userRatingService.getByUserAndMovie(userId, movieId)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa đánh giá")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID đánh giá", required = true) @PathVariable Integer id) {
        userRatingService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa đánh giá thành công", null));
    }
}
