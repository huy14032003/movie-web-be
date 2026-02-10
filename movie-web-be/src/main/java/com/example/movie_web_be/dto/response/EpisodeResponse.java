package com.example.movie_web_be.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Thông tin tập phim")
public class EpisodeResponse {

    @Schema(description = "ID tập phim", example = "1")
    private Integer id;

    @Schema(description = "ID phim", example = "1")
    private Integer movieId;

    @Schema(description = "Tiêu đề tập", example = "Tập 1")
    private String title;

    @Schema(description = "Số thứ tự tập", example = "1")
    private Integer episodeNumber;

    @Schema(description = "Link video")
    private String videoUrl;

    @Schema(description = "Tên server", example = "Server R2")
    private String serverName;

    @Schema(description = "Chất lượng", example = "1080p")
    private String quality;

    @Schema(description = "Ngày tạo")
    private LocalDateTime createdAt;
}
