package com.example.demo.controller;

import com.example.demo.dto.RecommendResponseDto;
import com.example.demo.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping("recommend")
    public RecommendResponseDto recommend() {
        return foodService.recommend();
    }

}
