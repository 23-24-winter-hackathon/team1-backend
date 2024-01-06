package com.example.demo.domain;

import com.example.demo.domain.enums.FoodType;
import com.example.demo.domain.enums.WayToCook;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Food {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "food_id")
    private Long id;

    private String foodName;
    @Enumerated(EnumType.STRING)
    private FoodType foodType;
    @Enumerated(EnumType.STRING)
    private WayToCook wayToCook;

    private Integer view = 0;
    private Integer apiIndex;
    private String imgSrc;

    @OneToOne
    @JoinColumn(name = "nutrition_id")
    private Nutrition nutrition;

    private String ingredients;

    public Food(String foodName, FoodType foodType, WayToCook wayToCook, Integer index, String imgSrc, Nutrition nutrition, String ingredients) {
        this.foodName = foodName;
        this.foodType = foodType;
        this.wayToCook = wayToCook;
        this.apiIndex = index;
        this.imgSrc = imgSrc;
        this.nutrition = nutrition;
        this.ingredients = ingredients;
    }
}
