package com.example.movie_web_be.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body để đánh giá phim")
public class UserRatingRequest {

    @NotNull(message = "userId không được để trống")
    @Schema(description = "ID người dùng", example = "1")
    private Integer userId;

    @NotNull(message = "movieId không được để trống")
    @Schema(description = "ID phim", example = "10")
    private Integer movieId;

    @NotNull(message = "Điểm đánh giá không được để trống")
    @Min(value = 1, message = "Điểm đánh giá phải từ 1")
    @Max(value = 10, message = "Điểm đánh giá không được vượt quá 10")
    @Schema(description = "Điểm đánh giá (1-10)", example = "8")
    private Integer rating;
}
