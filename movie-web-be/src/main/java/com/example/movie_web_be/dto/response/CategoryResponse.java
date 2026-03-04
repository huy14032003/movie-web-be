package com.example.movie_web_be.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Thông tin danh mục phim")
public class CategoryResponse {

    @Schema(description = "ID danh mục", example = "1")
    private Integer id;

    @Schema(description = "Tên danh mục", example = "Phim lẻ")
    private String name;

    @Schema(description = "Slug", example = "phim-le")
    private String slug;
}
