package com.example.movie_web_be.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body để tạo/cập nhật quốc gia")
public class CountryRequest {

    @NotBlank(message = "Tên quốc gia không được để trống")
    @Size(max = 100, message = "Tên quốc gia không được vượt quá 100 ký tự")
    @Schema(description = "Tên quốc gia", example = "Việt Nam")
    private String name;

    @Size(max = 100, message = "Slug không được vượt quá 100 ký tự")
    @Schema(description = "Slug cho SEO", example = "viet-nam")
    private String slug;
}
