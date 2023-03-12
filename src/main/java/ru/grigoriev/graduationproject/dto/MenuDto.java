package ru.grigoriev.graduationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.grigoriev.graduationproject.model.Dish;
import ru.grigoriev.graduationproject.model.Restaurant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuDto {
    private int id;
    private Restaurant restaurant;
    private Dish dish;
    private double price;
    private String dat;

    public String getRestaurant() {
        return "id = " + restaurant.getId() + ", name = " + restaurant.getName();
    }

    public String getDish() {
        return "id = " + dish.getId() + ", name = " + dish.getName();
    }
}
