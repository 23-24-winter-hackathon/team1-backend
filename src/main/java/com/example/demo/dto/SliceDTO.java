package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SliceDTO <T> {
    private Integer count;
    private Boolean hasNext;
    private Integer page;
    private Integer size;
    private List<T> data;
}
