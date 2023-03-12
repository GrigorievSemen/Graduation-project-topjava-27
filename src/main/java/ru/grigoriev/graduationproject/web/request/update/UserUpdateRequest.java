package ru.grigoriev.graduationproject.web.request.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class UserUpdateRequest extends AbstractNamedEntityUpdateRequest {
    private String old_password;
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 5, max = 128, message = "Длина пароля должна быть в диапазоне от 5 до 128 символов")
    private String new_password;
    @Email
    @Size(max = 128)
    private String email;
}
