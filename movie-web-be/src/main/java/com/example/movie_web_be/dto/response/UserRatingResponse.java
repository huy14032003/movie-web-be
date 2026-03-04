package com.example.movie_web_be.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Thông tin đánh giá phim")
public class UserRatingResponse {

    @Schema(description = "ID đánh giá", example = "1")
    private Integer id;

    @Schema(description = "ID người dùng", example = "1")
    private Integer userId;

    @Schema(description = "Tên người dùng", example = "johndoe")
    private String username;

    @Schema(description = "ID phim", example = "10")
    private Integer movieId;

    @Schema(description = "Điểm đánh giá (1-10)", example = "8")
    private Integer rating;

    @Schema(description = "Thời điểm đánh giá")
    private LocalDateTime createdAt;
}
