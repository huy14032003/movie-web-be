package com.example.movie_web_be.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body để tạo/cập nhật phim")
public class MovieRequest {

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 255, message = "Tiêu đề không được vượt quá 255 ký tự")
    @Schema(description = "Tiêu đề phim", example = "Avengers: Endgame")
    private String title;

    @Schema(description = "Mô tả phim", example = "After the devastating events...")
    private String description;

    @Min(value = 1888, message = "Năm không hợp lệ")
    @Max(value = 2100, message = "Năm không hợp lệ")
    @Schema(description = "Năm phát hành", example = "2019")
    private Integer year;

    @DecimalMin(value = "0.0", message = "Điểm đánh giá phải từ 0")
    @DecimalMax(value = "10.0", message = "Điểm đánh giá không được vượt quá 10")
    @Schema(description = "Điểm đánh giá (0-10)", example = "8.5")
    private BigDecimal rating;

    @Size(max = 50, message = "Thời lượng không được vượt quá 50 ký tự")
    @Schema(description = "Thời lượng phim", example = "2h 15m")
    private String duration;

    @Size(max = 1000, message = "URL poster không được vượt quá 1000 ký tự")
    @Schema(description = "URL ảnh poster", example = "https://example.com/poster.jpg")
    private String poster;

    @Size(max = 1000, message = "URL backdrop không được vượt quá 1000 ký tự")
    @Schema(description = "URL ảnh backdrop", example = "https://example.com/backdrop.jpg")
    private String backdrop;

    @Size(max = 255, message = "Tên đạo diễn không được vượt quá 255 ký tự")
    @Schema(description = "Tên đạo diễn", example = "Anthony Russo")
    private String director;

    @Size(max = 500, message = "URL trailer không được vượt quá 500 ký tự")
    @Schema(description = "URL trailer", example = "https://youtube.com/watch?v=xxx")
    private String trailer;

    @Schema(description = "Phim nổi bật", example = "true")
    private Boolean featured;

    @Schema(description = "Là phim bộ", example = "true")
    private Boolean isSeries;

    @Min(value = 1, message = "Tổng số tập phải từ 1 trở lên")
    @Schema(description = "Tổng số tập (dự kiến)", example = "24")
    private Integer totalEpisodes;

    @Size(max = 255, message = "Slug không được vượt quá 255 ký tự")
    @Schema(description = "Slug cho SEO", example = "avengers-endgame")
    private String slug;

    @Schema(description = "Danh sách ID thể loại", example = "[1, 2, 3]")
    private Set<Integer> genreIds;

    @Schema(description = "Danh sách ID diễn viên", example = "[1, 2, 3]")
    private Set<Integer> actorIds;
}
