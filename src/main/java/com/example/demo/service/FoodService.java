package com.example.demo.service;

import com.example.demo.domain.Food;
import com.example.demo.repository.FoodRepository;
import jakarta.transaction.InvalidTransactionException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.min;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    public List<Food> recommend() {
        return foodRepository.findRandom(3);
    }

    public Slice<Food> searchFood(String keyword, Pageable pageable) {
        return foodRepository.findByFoodNameContaining(keyword, pageable);
    }

    public Integer click(Integer apiIndex) {
        Food food = foodRepository.findByApiIndex(apiIndex).orElseThrow(RuntimeException::new);
        food.increaseView();
        return food.getView();
    }
}
