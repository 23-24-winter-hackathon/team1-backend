package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class FoodIngredient {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "food_ingredient_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
}
