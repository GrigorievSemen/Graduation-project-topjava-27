package ru.grigoriev.graduationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MenuDto {
    private int id;
    private RestaurantDto restaurant;
    private DishDto dish;
    private double price;
    private LocalDate dayMenu;
}
