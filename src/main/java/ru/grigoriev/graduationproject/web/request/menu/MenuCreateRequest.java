package ru.grigoriev.graduationproject.web.request.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MenuCreateRequest {
    @NotBlank(message = "restaurantId field number cannot be empty")
    private int restaurantId;

    @NotBlank(message = "dishId field number cannot be empty")
    private int dishId;

    @NotBlank(message = "price field number cannot be empty")
    private double price;

    @NotBlank(message = "dayMenu field number cannot be empty")
    private LocalDate dayMenu;
}
