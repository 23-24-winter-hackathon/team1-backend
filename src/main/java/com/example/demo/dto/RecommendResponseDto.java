package com.example.demo.dto;

import com.example.demo.domain.Food;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecommendResponseDto {
    private int count;
    private List<Food> recipes;
}
