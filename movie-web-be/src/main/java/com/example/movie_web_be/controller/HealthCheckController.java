package com.example.movie_web_be.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/healthcheck")
    public String healthcheck() {
        return "OK";
    }
}
