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
@Schema(description = "Thông tin người dùng")
public class UserResponse {

    @Schema(description = "ID người dùng", example = "1")
    private Integer id;

    @Schema(description = "Tên người dùng", example = "johndoe")
    private String username;

    @Schema(description = "Email", example = "john@example.com")
    private String email;

    @Schema(description = "URL ảnh đại diện")
    private String avatar;

    @Schema(description = "Ngày tạo tài khoản")
    private LocalDateTime createdAt;
}
