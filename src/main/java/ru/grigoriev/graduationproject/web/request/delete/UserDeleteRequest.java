package ru.grigoriev.graduationproject.web.request.delete;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDeleteRequest {
    @NotNull(message = "Id cannot be empty")
    private Integer id;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 5, max = 128, message = "Password length must be between 5 and 128 characters")
    private String password;
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 128, message = "Name length must be between 2 and 128 characters")
    private String name;
}
