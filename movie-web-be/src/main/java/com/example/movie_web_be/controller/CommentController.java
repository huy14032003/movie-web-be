package com.example.movie_web_be.controller;

import com.example.movie_web_be.dto.request.CommentRequest;
import com.example.movie_web_be.dto.request.PageRequest;
import com.example.movie_web_be.dto.response.ApiResponse;
import com.example.movie_web_be.dto.response.CommentResponse;
import com.example.movie_web_be.dto.response.PageResponse;
import com.example.movie_web_be.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name = "Comment", description = "API quản lý bình luận phim")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @Operation(summary = "Gửi bình luận hoặc reply", description = "Để reply thì truyền thêm parentId")
    public ResponseEntity<ApiResponse<CommentResponse>> create(@Valid @RequestBody CommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Gửi bình luận thành công", commentService.create(request)));
    }

    @GetMapping("/movie/{movieId}")
    @Operation(summary = "Lấy bình luận theo phim", description = "Chỉ trả về root comments, replies được lồng bên trong")
    public ResponseEntity<ApiResponse<PageResponse<CommentResponse>>> getByMovieId(
            @Parameter(description = "ID phim", required = true) @PathVariable Integer movieId,
            @ModelAttribute PageRequest pageRequest) {
        return ResponseEntity.ok(ApiResponse.success(
                commentService.getByMovieId(movieId, pageRequest.getPage(), pageRequest.getSize())));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Sửa nội dung bình luận")
    public ResponseEntity<ApiResponse<CommentResponse>> update(
            @Parameter(description = "ID comment", required = true) @PathVariable Integer id,
            @RequestParam @NotBlank(message = "Nội dung không được để trống") @Size(max = 5000) String content) {
        return ResponseEntity
                .ok(ApiResponse.success("Cập nhật bình luận thành công", commentService.update(id, content)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa bình luận (cascade xóa replies)")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID comment", required = true) @PathVariable Integer id) {
        commentService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa bình luận thành công", null));
    }
}
