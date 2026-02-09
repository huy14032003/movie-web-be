package com.example.movie_web_be.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body để tạo/cập nhật diễn viên")
public class ActorRequest {

    @NotBlank(message = "Tên diễn viên không được để trống")
    @Size(max = 255, message = "Tên diễn viên không được vượt quá 255 ký tự")
    @Schema(description = "Tên diễn viên", example = "Robert Downey Jr.")
    private String name;
}
