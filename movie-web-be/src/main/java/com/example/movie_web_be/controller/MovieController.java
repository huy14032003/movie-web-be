package com.example.movie_web_be.controller;

import com.example.movie_web_be.dto.request.MovieRequest;
import com.example.movie_web_be.dto.response.ApiResponse;
import com.example.movie_web_be.dto.response.MovieResponse;
import com.example.movie_web_be.dto.response.PageResponse;
import com.example.movie_web_be.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@Tag(name = "Movie", description = "API quản lý phim - CRUD, tìm kiếm, phân trang")
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    @Operation(summary = "Tạo phim mới", description = "Tạo một phim mới với đầy đủ thông tin bao gồm thể loại và diễn viên")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Tạo phim thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Slug đã tồn tại")
    })
    public ResponseEntity<ApiResponse<MovieResponse>> create(@Valid @RequestBody MovieRequest request) {
        MovieResponse response = movieService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo phim thành công", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy phim theo ID", description = "Lấy thông tin chi tiết của một phim theo ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy phim")
    })
    public ResponseEntity<ApiResponse<MovieResponse>> getById(
            @Parameter(description = "ID của phim", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getById(id)));
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "Lấy phim theo slug", description = "Lấy thông tin chi tiết của một phim theo slug (SEO friendly)")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy phim")
    })
    public ResponseEntity<ApiResponse<MovieResponse>> getBySlug(
            @Parameter(description = "Slug của phim", required = true, example = "avengers-endgame")
            @PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getBySlug(slug)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật phim", description = "Cập nhật thông tin phim theo ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy phim"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Slug đã tồn tại")
    })
    public ResponseEntity<ApiResponse<MovieResponse>> update(
            @Parameter(description = "ID của phim", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody MovieRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật phim thành công", movieService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa phim", description = "Xóa phim theo ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Xóa thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy phim")
    })
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của phim", required = true)
            @PathVariable Integer id) {
        movieService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa phim thành công", null));
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách phim", description = "Lấy danh sách phim với phân trang và sắp xếp")
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> getAll(
            @Parameter(description = "Số trang (bắt đầu từ 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng phim mỗi trang", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sắp xếp theo trường", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Hướng sắp xếp (asc/desc)", example = "desc")
            @RequestParam(defaultValue = "desc") String sortDir) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getAll(page, size, sortBy, sortDir)));
    }

    @GetMapping("/search")
    @Operation(summary = "Tìm kiếm phim", description = "Tìm kiếm phim theo tiêu đề, năm, đạo diễn với phân trang")
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> search(
            @Parameter(description = "Tiêu đề phim (tìm kiếm gần đúng)")
            @RequestParam(required = false) String title,
            @Parameter(description = "Năm phát hành")
            @RequestParam(required = false) Integer year,
            @Parameter(description = "Tên đạo diễn (tìm kiếm gần đúng)")
            @RequestParam(required = false) String director,
            @Parameter(description = "Số trang", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng mỗi trang", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(movieService.search(title, year, director, page, size)));
    }

    @GetMapping("/featured")
    @Operation(summary = "Lấy phim nổi bật", description = "Lấy danh sách phim được đánh dấu là nổi bật")
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> getFeatured(
            @Parameter(description = "Số trang", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng mỗi trang", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getFeatured(page, size)));
    }

    @GetMapping("/genre/{genreId}")
    @Operation(summary = "Lấy phim theo thể loại", description = "Lấy danh sách phim thuộc một thể loại cụ thể")
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> getByGenre(
            @Parameter(description = "ID thể loại", required = true)
            @PathVariable Integer genreId,
            @Parameter(description = "Số trang", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng mỗi trang", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getByGenre(genreId, page, size)));
    }

    @GetMapping("/actor/{actorId}")
    @Operation(summary = "Lấy phim theo diễn viên", description = "Lấy danh sách phim có sự tham gia của một diễn viên")
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> getByActor(
            @Parameter(description = "ID diễn viên", required = true)
            @PathVariable Integer actorId,
            @Parameter(description = "Số trang", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng mỗi trang", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getByActor(actorId, page, size)));
    }
}
