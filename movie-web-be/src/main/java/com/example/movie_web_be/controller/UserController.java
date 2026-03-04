package com.example.movie_web_be.controller;

import com.example.movie_web_be.dto.request.PageRequest;
import com.example.movie_web_be.dto.request.UserRequest;
import com.example.movie_web_be.dto.response.ApiResponse;
import com.example.movie_web_be.dto.response.PageResponse;
import com.example.movie_web_be.dto.response.UserResponse;
import com.example.movie_web_be.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "API quản lý người dùng")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Tạo người dùng mới")
    public ResponseEntity<ApiResponse<UserResponse>> create(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo người dùng thành công", userService.create(request)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy người dùng theo ID")
    public ResponseEntity<ApiResponse<UserResponse>> getById(
            @Parameter(description = "ID người dùng", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(userService.getById(id)));
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách người dùng (có phân trang)")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getAll(
            @ModelAttribute PageRequest pageRequest) {
        return ResponseEntity.ok(ApiResponse.success(userService.getAll(pageRequest.getPage(), pageRequest.getSize())));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật thông tin người dùng")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @Parameter(description = "ID người dùng", required = true) @PathVariable Integer id,
            @Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", userService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa người dùng")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID người dùng", required = true) @PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa người dùng thành công", null));
    }
}
