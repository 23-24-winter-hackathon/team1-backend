package com.example.demo.dto;

import com.example.demo.domain.Nutrition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionDTO {
    private Double calorie;
    private Double carbon;
    private Double protein;
    private Double fat;

    public NutritionDTO(Nutrition nutrition) {
        this.calorie = nutrition.getCalorie();
        this.carbon = nutrition.getCarbon();
        this.protein = nutrition.getProtein();
        this.fat = nutrition.getFat();
    }
}
