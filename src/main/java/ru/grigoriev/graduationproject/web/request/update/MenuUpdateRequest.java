package ru.grigoriev.graduationproject.web.request.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MenuUpdateRequest {
    @NotBlank(message = "The entry id field cannot be empty")
    private int id;

    @NotBlank(message = "The restaurant_id field cannot be empty")
    private int restaurant_id;

    @NotBlank(message = "The dish_id field cannot be empty")
    private int dish_id;

    @NotBlank(message = "The price field cannot be empty")
    private double price;
}
