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
@Schema(description = "Thông tin thể loại")
public class GenreResponse {

    @Schema(description = "ID thể loại", example = "1")
    private Integer id;

    @Schema(description = "Tên thể loại", example = "Action")
    private String name;
}
