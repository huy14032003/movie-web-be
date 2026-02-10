package com.example.movie_web_be;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
// ... các import cũ của bạn
@SpringBootApplication
public class MovieWebBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieWebBeApplication.class, args);
	}

	@Bean
	public CommandLineRunner printUrl(Environment env) {
		return args -> {
			String port = env.getProperty("server.port", "8080");
			String contextPath = env.getProperty("server.servlet.context-path", "");

			System.out.println("\n----------------------------------------------------------");
			System.out.println("Application is running! Access URLs:");
			System.out.println("Local:      http://localhost:" + port + contextPath);
			System.out.println("Swagger UI: http://localhost:" + port + contextPath + "/swagger-ui.html");
			System.out.println("----------------------------------------------------------\n");
		};
	}

}
