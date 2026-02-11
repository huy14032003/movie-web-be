package com.example.movie_web_be.controller;

import com.example.movie_web_be.dto.request.CountryRequest;
import com.example.movie_web_be.dto.request.PageRequest;
import com.example.movie_web_be.dto.response.ApiResponse;
import com.example.movie_web_be.dto.response.CountryResponse;
import com.example.movie_web_be.dto.response.PageResponse;
import com.example.movie_web_be.service.CountryService;
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
@RequestMapping("/api/countries")
@RequiredArgsConstructor
@Tag(name = "Country", description = "API quản lý quốc gia - CRUD, tìm kiếm, phân trang")
public class CountryController {

    private final CountryService countryService;

    @PostMapping
    @Operation(summary = "Tạo quốc gia mới", description = "Tạo một quốc gia mới")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Tạo thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Tên quốc gia đã tồn tại")
    })
    public ResponseEntity<ApiResponse<CountryResponse>> create(@Valid @RequestBody CountryRequest request) {
        CountryResponse response = countryService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo quốc gia thành công", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy quốc gia theo ID", description = "Lấy thông tin chi tiết của một quốc gia")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy quốc gia")
    })
    public ResponseEntity<ApiResponse<CountryResponse>> getById(
            @Parameter(description = "ID của quốc gia", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(countryService.getById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật quốc gia", description = "Cập nhật thông tin quốc gia theo ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy quốc gia"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Tên quốc gia đã tồn tại")
    })
    public ResponseEntity<ApiResponse<CountryResponse>> update(
            @Parameter(description = "ID của quốc gia", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody CountryRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật quốc gia thành công", countryService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa quốc gia", description = "Xóa quốc gia theo ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Xóa thành công"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy quốc gia")
    })
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của quốc gia", required = true)
            @PathVariable Integer id) {
        countryService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa quốc gia thành công", null));
    }

    @GetMapping
    @Operation(summary = "Lấy tất cả quốc gia", description = "Lấy danh sách tất cả quốc gia (không phân trang)")
    public ResponseEntity<ApiResponse<List<CountryResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(countryService.getAll()));
    }

    @GetMapping("/paged")
    @Operation(summary = "Lấy danh sách quốc gia có phân trang", description = "Lấy danh sách quốc gia với phân trang")
    public ResponseEntity<ApiResponse<PageResponse<CountryResponse>>> getAllPaged(@ModelAttribute PageRequest pageRequest) {
        return ResponseEntity.ok(ApiResponse.success(
                countryService.getAllPaged(pageRequest.getPage(), pageRequest.getSize())));
    }

    @GetMapping("/search")
    @Operation(summary = "Tìm kiếm quốc gia", description = "Tìm kiếm quốc gia theo tên với phân trang")
    public ResponseEntity<ApiResponse<PageResponse<CountryResponse>>> search(
            @Parameter(description = "Tên quốc gia (tìm kiếm gần đúng)")
            @RequestParam(required = false) String name,
            @ModelAttribute PageRequest pageRequest) {
        return ResponseEntity.ok(ApiResponse.success(
                countryService.search(name, pageRequest.getPage(), pageRequest.getSize())));
    }
}
