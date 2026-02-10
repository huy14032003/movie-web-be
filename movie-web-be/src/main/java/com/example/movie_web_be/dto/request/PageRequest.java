package com.example.movie_web_be.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Thông tin phân trang")
public class PageRequest {

    @Schema(description = "Số trang (bắt đầu từ 0)", example = "0", defaultValue = "0")
    private int page = 0;

    @Schema(description = "Số lượng mỗi trang", example = "10", defaultValue = "10")
    private int size = 10;

    @Schema(description = "Sắp xếp theo trường", example = "createdAt", defaultValue = "createdAt")
    private String sortBy = "createdAt";

    @Schema(description = "Hướng sắp xếp (asc/desc)", example = "desc", defaultValue = "desc")
    private String sortDir = "desc";
}
