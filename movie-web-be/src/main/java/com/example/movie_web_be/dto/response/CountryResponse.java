package com.example.movie_web_be.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Thông tin quốc gia")
public class CountryResponse {

    @Schema(description = "ID quốc gia", example = "1")
    private Integer id;

    @Schema(description = "Tên quốc gia", example = "Việt Nam")
    private String name;

    @Schema(description = "Slug", example = "viet-nam")
    private String slug;
}
