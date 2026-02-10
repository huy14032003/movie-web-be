package com.example.movie_web_be.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body để tạo/cập nhật tập phim")
public class EpisodeRequest {

    @Size(max = 255, message = "Tiêu đề tập không được vượt quá 255 ký tự")
    @Schema(description = "Tiêu đề tập phim", example = "Tập 1")
    private String title;

    @NotNull(message = "Số tập không được để trống")
    @Min(value = 1, message = "Số tập phải từ 1 trở lên")
    @Schema(description = "Số thứ tự tập", example = "1")
    private Integer episodeNumber;

    @Size(max = 1000, message = "URL video không được vượt quá 1000 ký tự")
    @Schema(description = "Link video", example = "https://example.com/video.mp4")
    private String videoUrl;

    @Size(max = 100, message = "Tên server không được vượt quá 100 ký tự")
    @Schema(description = "Tên server phát", example = "Server R2")
    private String serverName;

    @Size(max = 50, message = "Chất lượng không được vượt quá 50 ký tự")
    @Schema(description = "Chất lượng video", example = "1080p")
    private String quality;
}
