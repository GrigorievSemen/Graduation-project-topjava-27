package ru.grigoriev.graduationproject.web.request.delete;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class AbstractNamedEntityDeleteRequest {
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 128, message = "Длина имени должна быть в диапазоне от 2 до 128 символов")
    private String name;
}
