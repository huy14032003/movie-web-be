package com.example.movie_web_be.controller;

import com.example.movie_web_be.dto.request.EpisodeRequest;
import com.example.movie_web_be.dto.response.ApiResponse;
import com.example.movie_web_be.dto.response.EpisodeResponse;
import com.example.movie_web_be.service.EpisodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Episode", description = "API quản lý tập phim")
public class EpisodeController {

    private final EpisodeService episodeService;

    @PostMapping("/api/movies/{movieId}/episodes")
    @Operation(summary = "Tạo tập phim mới", description = "Thêm một tập phim mới cho phim bộ")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Tạo tập phim thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy phim"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Số tập đã tồn tại")
    })
    public ResponseEntity<ApiResponse<EpisodeResponse>> create(
            @Parameter(description = "ID của phim", required = true) @PathVariable Integer movieId,
            @Valid @RequestBody EpisodeRequest request) {
        EpisodeResponse response = episodeService.create(movieId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo tập phim thành công", response));
    }

    @GetMapping("/api/movies/{movieId}/episodes")
    @Operation(summary = "Lấy danh sách tập phim", description = "Lấy tất cả tập phim của một phim, sắp xếp theo số tập")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy phim")
    })
    public ResponseEntity<ApiResponse<List<EpisodeResponse>>> getByMovieId(
            @Parameter(description = "ID của phim", required = true) @PathVariable Integer movieId) {
        return ResponseEntity.ok(ApiResponse.success(episodeService.getByMovieId(movieId)));
    }

    @GetMapping("/api/episodes/{id}")
    @Operation(summary = "Lấy tập phim theo ID", description = "Lấy thông tin chi tiết của một tập phim")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy tập phim")
    })
    public ResponseEntity<ApiResponse<EpisodeResponse>> getById(
            @Parameter(description = "ID của tập phim", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(episodeService.getById(id)));
    }

    @PutMapping("/api/episodes/{id}")
    @Operation(summary = "Cập nhật tập phim", description = "Cập nhật thông tin tập phim theo ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy tập phim"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Số tập đã tồn tại")
    })
    public ResponseEntity<ApiResponse<EpisodeResponse>> update(
            @Parameter(description = "ID của tập phim", required = true) @PathVariable Integer id,
            @Valid @RequestBody EpisodeRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Cập nhật tập phim thành công", episodeService.update(id, request)));
    }

    @DeleteMapping("/api/episodes/{id}")
    @Operation(summary = "Xóa tập phim", description = "Xóa tập phim theo ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Xóa thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy tập phim")
    })
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của tập phim", required = true) @PathVariable Integer id) {
        episodeService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa tập phim thành công", null));
    }
}
