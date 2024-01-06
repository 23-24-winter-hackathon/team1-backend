package com.example.demo.repository;

import com.example.demo.domain.Food;
import com.example.demo.domain.QFood;
import com.example.demo.domain.QNutrition;
import com.example.demo.domain.enums.FoodType;
import com.example.demo.domain.enums.WayToCook;
import com.example.demo.dto.HelloRequestDTO;
import com.example.demo.dto.SliceDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.domain.QFood.food;
import static com.example.demo.domain.QNutrition.nutrition;

@Slf4j
@RequiredArgsConstructor
public class FoodRepositoryImpl implements FoodRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public SliceDTO<Food> findFoodCardsWithMany(HelloRequestDTO helloRequestDTO, Pageable pageable) {
        List<Food> foods = jpaQueryFactory.select(
                        food
                ).from(food)
                .leftJoin(food.nutrition, nutrition).fetchJoin()
                .where(foodEq(helloRequestDTO))
                .limit(pageable.getPageSize() + 1)
                .offset(pageable.getOffset())
                .orderBy(getOrderSpecifier(pageable.getSort())
                        .stream().toArray(OrderSpecifier[]::new))
                .fetch();
        Boolean hasNext = false;
        Integer size = foods.size();
        if(foods.size() > pageable.getPageSize()){
            hasNext = true;
            size -= 1;
            foods.remove(size);
        }
       return new SliceDTO<>(size, hasNext, pageable.getPageNumber(), foods);
    }


    private BooleanBuilder foodEq(HelloRequestDTO req) {
        BooleanBuilder foodEq = new BooleanBuilder();
        req.getCalorieMax().ifPresent(
                clue -> foodEq.and(nutrition.calorie.loe(clue))
        );
        req.getCalorieMin().ifPresent(
                clue -> foodEq.and(nutrition.calorie.goe(clue))
        );
        for (String ingredient : req.getIngredient()) {
            log.info("ingredient : {}", ingredient);
            foodEq.and(food.ingredients.contains(ingredient));
        }
        log.info("foodEq.getValue() : {}", foodEq.getValue());
        if(!req.getFoodType().isEmpty()) foodEq.and(food.foodType.in(req.getFoodType()));
        if(!req.getWayToCook().isEmpty()) foodEq.and(food.wayToCook.in(req.getWayToCook()));
        return foodEq;
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder pathBuilder = new PathBuilder(Food.class, "food");
            orders.add(new OrderSpecifier(direction, pathBuilder.get(prop)));
        });
        return orders;
    }

}
