package ru.grigoriev.graduationproject.web.request.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuCreateRequest {
    @NotBlank(message = "Номер поля restaurant_id не может быть пустым")
    private int restaurant_id;

    @NotBlank(message = "Номер поля dish_id не может быть пустым")
    private int dish_id;

    @NotBlank(message = "Номер поля price не может быть пустым")
    private double price;

    @NotBlank(message = "Номер поля day_menu не может быть пустым")
    private String day_menu;
}
