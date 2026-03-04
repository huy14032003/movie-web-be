package com.example.movie_web_be.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Thông tin bình luận")
public class CommentResponse {

    @Schema(description = "ID comment", example = "1")
    private Integer id;

    @Schema(description = "ID người dùng", example = "1")
    private Integer userId;

    @Schema(description = "Tên người dùng", example = "johndoe")
    private String username;

    @Schema(description = "URL ảnh đại diện người dùng")
    private String userAvatar;

    @Schema(description = "ID phim", example = "10")
    private Integer movieId;

    @Schema(description = "Nội dung bình luận")
    private String content;

    @Schema(description = "ID comment cha (null nếu là comment gốc)")
    private Integer parentId;

    @Schema(description = "Danh sách replies")
    private List<CommentResponse> replies;

    @Schema(description = "Thời điểm tạo")
    private LocalDateTime createdAt;
}
