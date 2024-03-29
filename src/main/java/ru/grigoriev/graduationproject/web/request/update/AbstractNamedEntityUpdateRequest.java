package ru.grigoriev.graduationproject.web.request.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public abstract class AbstractNamedEntityUpdateRequest {
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 128, message = "Длина имени должна быть в диапазоне от 2 до 128 символов")
    private String old_name;
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 128, message = "Длина имени должна быть в диапазоне от 2 до 128 символов")
    private String new_name;
}
