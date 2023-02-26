package ru.grigoriev.graduationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import ru.grigoriev.graduationproject.model.Dish;
import ru.grigoriev.graduationproject.model.Restaurant;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuDto {
    private int id;
    private Restaurant restaurant;
    private Dish dish;
    private double price;
    private String day_menu;

    public String getRestaurant(){
        return "id = " + restaurant.getId() + ", name = " + restaurant.getName();
    }

    public String getDish(){
        return "id = " + dish.getId() + ", name = " + dish.getName();
    }
}
