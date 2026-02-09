package com.example.movie_web_be.controller;

import com.example.movie_web_be.dto.request.GenreRequest;
import com.example.movie_web_be.dto.response.ApiResponse;
import com.example.movie_web_be.dto.response.GenreResponse;
import com.example.movie_web_be.dto.response.PageResponse;
import com.example.movie_web_be.service.GenreService;
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
@RequestMapping("/api/genres")
@RequiredArgsConstructor
@Tag(name = "Genre", description = "API quản lý thể loại phim - CRUD, tìm kiếm, phân trang")
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    @Operation(summary = "Tạo thể loại mới", description = "Tạo một thể loại phim mới")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Tạo thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Tên thể loại đã tồn tại")
    })
    public ResponseEntity<ApiResponse<GenreResponse>> create(@Valid @RequestBody GenreRequest request) {
        GenreResponse response = genreService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo thể loại thành công", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thể loại theo ID", description = "Lấy thông tin chi tiết của một thể loại")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy thể loại")
    })
    public ResponseEntity<ApiResponse<GenreResponse>> getById(
            @Parameter(description = "ID của thể loại", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(genreService.getById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật thể loại", description = "Cập nhật thông tin thể loại theo ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy thể loại"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Tên thể loại đã tồn tại")
    })
    public ResponseEntity<ApiResponse<GenreResponse>> update(
            @Parameter(description = "ID của thể loại", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody GenreRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thể loại thành công", genreService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa thể loại", description = "Xóa thể loại theo ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Xóa thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy thể loại")
    })
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của thể loại", required = true)
            @PathVariable Integer id) {
        genreService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa thể loại thành công", null));
    }

    @GetMapping
    @Operation(summary = "Lấy tất cả thể loại", description = "Lấy danh sách tất cả thể loại (không phân trang)")
    public ResponseEntity<ApiResponse<List<GenreResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(genreService.getAll()));
    }

    @GetMapping("/paged")
    @Operation(summary = "Lấy danh sách thể loại có phân trang", description = "Lấy danh sách thể loại với phân trang")
    public ResponseEntity<ApiResponse<PageResponse<GenreResponse>>> getAllPaged(
            @Parameter(description = "Số trang (bắt đầu từ 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng mỗi trang", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(genreService.getAllPaged(page, size)));
    }

    @GetMapping("/search")
    @Operation(summary = "Tìm kiếm thể loại", description = "Tìm kiếm thể loại theo tên với phân trang")
    public ResponseEntity<ApiResponse<PageResponse<GenreResponse>>> search(
            @Parameter(description = "Tên thể loại (tìm kiếm gần đúng)")
            @RequestParam(required = false) String name,
            @Parameter(description = "Số trang", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng mỗi trang", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(genreService.search(name, page, size)));
    }
}
