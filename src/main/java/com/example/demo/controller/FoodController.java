package com.example.demo.controller;

import com.example.demo.domain.Food;
import com.example.demo.dto.FoodCardDTO;
import com.example.demo.dto.ListDTO;
import com.example.demo.dto.SliceDTO;
import com.example.demo.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/recommend")
    public ListDTO<FoodCardDTO> recommend() {
        List<Food> foods = foodService.recommend();
        return ListDTO.createFoodCards(foods);
    }

    @GetMapping("/search")
    public SliceDTO<FoodCardDTO> searchFoodCard(@RequestParam String keyword, Pageable pageable) {
        Pageable newPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().and(Sort.by("view").descending()));

        return SliceDTO.createFoodCards(foodService.searchFood(keyword, newPageable));
    }
}
