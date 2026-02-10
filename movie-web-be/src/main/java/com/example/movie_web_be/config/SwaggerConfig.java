package com.example.movie_web_be.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Movie Web API")
                        .version("1.0.0")
                        .description("API quản lý phim, thể loại và diễn viên cho ứng dụng Movie Web. " +
                                "Hỗ trợ đầy đủ CRUD, tìm kiếm và phân trang.")
                        .contact(new Contact()
                                .name("Movie Web Team")
                                .email("support@movieweb.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
