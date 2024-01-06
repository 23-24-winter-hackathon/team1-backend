package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Nutrition {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "nutrition_id")
    private Long id;

    private Double calorie;
    private Double carbon;
    private Double protein;
    private Double fat;

    public Nutrition(Double calorie, Double carbon, Double protein, Double fat) {
        this.calorie = calorie;
        this.carbon = carbon;
        this.protein = protein;
        this.fat = fat;
    }
}
