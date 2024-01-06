package com.example.demo.dto;

import com.example.demo.domain.Food;
import com.example.demo.domain.Nutrition;
import com.example.demo.domain.enums.WayToCook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodCardDTO {
    private String foodName;
    private Integer index;
    private String imgUrl;
    private WayToCook wayToCook;
    private NutritionDTO nutritionDTO;

    public FoodCardDTO(Food food) {
        foodName = food.getFoodName();
        index = food.getApiIndex();
        imgUrl = food.getImgSrc();
        wayToCook = food.getWayToCook();
        nutritionDTO = new NutritionDTO(food.getNutrition());
    }
}
