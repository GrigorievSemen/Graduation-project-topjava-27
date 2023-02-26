package ru.grigoriev.graduationproject.web.user.request.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuCreateRequest {
    private int restaurant_id;
    private int dish_id;
    private double price;
}
