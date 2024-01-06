package com.example.demo.controller;

import com.example.demo.domain.Food;
import com.example.demo.dto.FoodCardDTO;
import com.example.demo.dto.HelloRequestDTO;
import com.example.demo.dto.ListDTO;
import com.example.demo.dto.SliceDTO;
import com.example.demo.repository.FoodRepository;
import com.example.demo.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;
    private final FoodRepository foodRepository;

    @GetMapping("/recommend")
    public ListDTO<FoodCardDTO> recommend() {
        List<Food> foods = foodService.recommend();
        return ListDTO.createFoodCards(foods);
    }

    @PostMapping("/hello")
    public SliceDTO<FoodCardDTO> hello(@RequestBody HelloRequestDTO helloRequestDTO) {
        log.info("helloRequestDTO.getIngredient() : {}", helloRequestDTO.getIngredient());
        Sort sort = Sort.by(helloRequestDTO.getOrderBy());
        if (helloRequestDTO.getSortBy().equals("desc")) sort = sort.descending();
        PageRequest pageRequest = PageRequest.of(helloRequestDTO.getPageNumber(), helloRequestDTO.getPageSize(), sort.and(Sort.by("view").descending()));
        SliceDTO<Food> cards = foodRepository.findFoodCardsWithMany(helloRequestDTO, pageRequest);
        List<FoodCardDTO> cardDatas = cards.getData().stream().map(FoodCardDTO::new).collect(Collectors.toList());
        return new SliceDTO<>(cards.getCount(), cards.getHasNext(), cards.getPage(), cardDatas);
    }

    @GetMapping("/search")
    public SliceDTO<FoodCardDTO> searchFoodCard(@RequestParam String keyword, Pageable pageable) {
        Pageable newPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().and(Sort.by("view").descending()));

        return SliceDTO.createFoodCards(foodService.searchFood(keyword, newPageable));

    }

}
