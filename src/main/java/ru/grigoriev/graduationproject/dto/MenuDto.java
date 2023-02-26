package ru.grigoriev.graduationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuDto {
    private int id;
    private int restaurant_id;
    private int dish_id;
    private double price;
    private String day_menu;
}
