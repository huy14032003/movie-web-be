package com.example.movie_web_be.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Thông tin chi tiết phim")
public class MovieResponse {

    @Schema(description = "ID phim", example = "1")
    private Integer id;

    @Schema(description = "Tiêu đề phim", example = "Avengers: Endgame")
    private String title;

    @Schema(description = "Mô tả phim")
    private String description;

    @Schema(description = "Năm phát hành", example = "2019")
    private Integer year;

    @Schema(description = "Điểm đánh giá", example = "8.5")
    private BigDecimal rating;

    @Schema(description = "Thời lượng", example = "2h 15m")
    private String duration;

    @Schema(description = "URL poster")
    private String poster;

    @Schema(description = "URL backdrop")
    private String backdrop;

    @Schema(description = "Đạo diễn", example = "Anthony Russo")
    private String director;

    @Schema(description = "URL trailer")
    private String trailer;

    @Schema(description = "Phim nổi bật", example = "true")
    private Boolean featured;

    @Schema(description = "Là phim bộ", example = "true")
    private Boolean isSeries;

    @Schema(description = "Tổng số tập", example = "24")
    private Integer totalEpisodes;

    @Schema(description = "Slug SEO", example = "avengers-endgame")
    private String slug;

    @Schema(description = "Ngày tạo")
    private LocalDateTime createdAt;

    @Schema(description = "Danh sách thể loại")
    private Set<GenreResponse> genres;

    @Schema(description = "Danh sách diễn viên")
    private Set<ActorResponse> actors;

    @Schema(description = "Danh sách quốc gia")
    private Set<CountryResponse> countries;

    @Schema(description = "Danh sách các tập phim")
    private List<EpisodeResponse> episodes;
}
