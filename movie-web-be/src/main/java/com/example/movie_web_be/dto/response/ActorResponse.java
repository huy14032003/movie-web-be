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
@Schema(description = "Thông tin diễn viên")
public class ActorResponse {

    @Schema(description = "ID diễn viên", example = "1")
    private Integer id;

    @Schema(description = "Tên diễn viên", example = "Robert Downey Jr.")
    private String name;
}
