package com.example.movie_web_be.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body để gửi comment hoặc reply")
public class CommentRequest {

    @NotNull(message = "userId không được để trống")
    @Schema(description = "ID người dùng", example = "1")
    private Integer userId;

    @NotNull(message = "movieId không được để trống")
    @Schema(description = "ID phim", example = "10")
    private Integer movieId;

    @NotBlank(message = "Nội dung comment không được để trống")
    @Size(max = 5000, message = "Nội dung không được vượt quá 5000 ký tự")
    @Schema(description = "Nội dung bình luận", example = "Bộ phim rất hay!")
    private String content;

    @Schema(description = "ID comment cha (nếu là reply, để null nếu là comment gốc)", example = "5")
    private Integer parentId;
}
