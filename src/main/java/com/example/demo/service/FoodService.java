package com.example.demo.service;

import com.example.demo.domain.Food;
import com.example.demo.dto.RecommendResponseDto;
import com.example.demo.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;


@Slf4j
@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    public RecommendResponseDto recommend() {
        List<Food> foods = foodRepository.findRandom(3);

        return new RecommendResponseDto(min(3, foods.size()), foods);
    }
}
