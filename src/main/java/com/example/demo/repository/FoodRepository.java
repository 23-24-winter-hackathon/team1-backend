package com.example.demo.repository;

import com.example.demo.domain.Food;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query(value = "SELECT * FROM food order by RAND() limit :num ", nativeQuery = true)
    List<Food> findRandom(@Param("num") int num);


}
