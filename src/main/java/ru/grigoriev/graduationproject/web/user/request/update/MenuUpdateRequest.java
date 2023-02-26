package ru.grigoriev.graduationproject.web.user.request.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuUpdateRequest {
    @NotBlank(message = "Поле id записи не может быть пустым")
    private int id;

    @NotBlank(message = "Поле restaurant_id не может быть пустым")
    private int restaurant_id;

    @NotBlank(message = "Поле dish_id не может быть пустым")
    private int dish_id;

    @NotBlank(message = "Поле price не может быть пустым")
    private double price;
}
