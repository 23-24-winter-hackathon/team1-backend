package com.example.demo.dto;

import com.example.demo.domain.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SliceDTO <T> {
    private Integer count;
    private Boolean hasNext;
    private Integer page;
    private List<T> data;

    public static SliceDTO<FoodCardDTO> createFoodCards(Slice<Food> foods) {
        return new SliceDTO<>(foods.getNumberOfElements(), foods.hasNext(), foods.getNumber(), foods.stream()
                .map(FoodCardDTO::new)
                .collect(Collectors.toList()));
    }
}
