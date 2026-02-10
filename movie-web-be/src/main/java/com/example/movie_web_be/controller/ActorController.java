package com.example.movie_web_be.controller;

import com.example.movie_web_be.dto.request.ActorRequest;
import com.example.movie_web_be.dto.request.PageRequest;
import com.example.movie_web_be.dto.response.ActorResponse;
import com.example.movie_web_be.dto.response.ApiResponse;
import com.example.movie_web_be.dto.response.PageResponse;
import com.example.movie_web_be.service.ActorService;
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
@RequestMapping("/api/actors")
@RequiredArgsConstructor
@Tag(name = "Actor", description = "API quản lý diễn viên - CRUD, tìm kiếm, phân trang")
public class ActorController {

    private final ActorService actorService;

    @PostMapping
    @Operation(summary = "Tạo diễn viên mới", description = "Thêm một diễn viên mới vào hệ thống")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Tạo thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Tên diễn viên đã tồn tại")
    })
    public ResponseEntity<ApiResponse<ActorResponse>> create(@Valid @RequestBody ActorRequest request) {
        ActorResponse response = actorService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo diễn viên thành công", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy diễn viên theo ID", description = "Lấy thông tin chi tiết của một diễn viên")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy diễn viên")
    })
    public ResponseEntity<ApiResponse<ActorResponse>> getById(
            @Parameter(description = "ID của diễn viên", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(actorService.getById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật diễn viên", description = "Cập nhật thông tin diễn viên theo ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy diễn viên"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Tên diễn viên đã tồn tại")
    })
    public ResponseEntity<ApiResponse<ActorResponse>> update(
            @Parameter(description = "ID của diễn viên", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody ActorRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật diễn viên thành công", actorService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa diễn viên", description = "Xóa diễn viên theo ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Xóa thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy diễn viên")
    })
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của diễn viên", required = true)
            @PathVariable Integer id) {
        actorService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa diễn viên thành công", null));
    }

    @GetMapping
    @Operation(summary = "Lấy tất cả diễn viên", description = "Lấy danh sách tất cả diễn viên (không phân trang)")
    public ResponseEntity<ApiResponse<List<ActorResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(actorService.getAll()));
    }

    @GetMapping("/paged")
    @Operation(summary = "Lấy danh sách diễn viên có phân trang", description = "Lấy danh sách diễn viên với phân trang")
    public ResponseEntity<ApiResponse<PageResponse<ActorResponse>>> getAllPaged(@ModelAttribute PageRequest pageRequest) {
        return ResponseEntity.ok(ApiResponse.success(
                actorService.getAllPaged(pageRequest.getPage(), pageRequest.getSize())));
    }

    @GetMapping("/search")
    @Operation(summary = "Tìm kiếm diễn viên", description = "Tìm kiếm diễn viên theo tên với phân trang")
    public ResponseEntity<ApiResponse<PageResponse<ActorResponse>>> search(
            @Parameter(description = "Tên diễn viên (tìm kiếm gần đúng)")
            @RequestParam(required = false) String name,
            @ModelAttribute PageRequest pageRequest) {
        return ResponseEntity.ok(ApiResponse.success(
                actorService.search(name, pageRequest.getPage(), pageRequest.getSize())));
    }
}
