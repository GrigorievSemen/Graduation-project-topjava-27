package ru.grigoriev.graduationproject.web.user.request.delete;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDeleteRequest extends AbstractNamedEntityDeleteRequest {
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 5, max = 128, message = "Длина пароля должна быть в диапазоне от 5 до 128 символов")
    private String password;
}
