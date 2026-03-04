package com.example.movie_web_be.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body để tạo/cập nhật user")
public class UserRequest {

    @NotBlank(message = "Username không được để trống")
    @Size(min = 3, max = 50, message = "Username phải từ 3 đến 50 ký tự")
    @Schema(description = "Tên người dùng", example = "johndoe")
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = 255, message = "Email không được vượt quá 255 ký tự")
    @Schema(description = "Địa chỉ email", example = "john@example.com")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải ít nhất 6 ký tự")
    @Schema(description = "Mật khẩu", example = "secret123")
    private String password;

    @Size(max = 1000, message = "URL avatar không được vượt quá 1000 ký tự")
    @Schema(description = "URL ảnh đại diện", example = "https://example.com/avatar.jpg")
    private String avatar;
}
