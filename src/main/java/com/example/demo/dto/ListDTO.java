package com.example.demo.dto;

import com.example.demo.domain.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListDTO <T> {
    private Integer count;
    private List<T> data;

    public static ListDTO<FoodCardDTO> createFoodCards(List<Food> foods) {
        return new ListDTO<>(foods.size(), foods.stream()
                .map(FoodCardDTO::new)
                .collect(Collectors.toList()));
    }
}
