package com.example.demo.repository;

import com.example.demo.domain.Food;
import com.example.demo.dto.HelloRequestDTO;
import com.example.demo.dto.SliceDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface FoodRepositoryCustom{
    public SliceDTO<Food> findFoodCardsWithMany(HelloRequestDTO helloRequestDTO, Pageable pageable);
}
