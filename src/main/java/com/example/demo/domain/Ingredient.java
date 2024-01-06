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
public class Ingredient {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    private String ingredientName;
}
