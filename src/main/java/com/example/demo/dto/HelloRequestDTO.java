package com.example.demo.dto;

import com.example.demo.domain.enums.WayToCook;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelloRequestDTO {
    private List<String> ingredient = new ArrayList<>();
    private List<WayToCook> wayToCook = new ArrayList<>();

    @Min(0)
    private Optional<Double> calorieMin = Optional.empty();
    private Optional<Double> calorieMax = Optional.empty();

    private Integer pageNumber;
    private Integer pageSize;
    private String orderBy;
    private String sortBy;
}
