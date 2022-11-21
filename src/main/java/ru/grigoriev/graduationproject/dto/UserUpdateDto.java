package ru.grigoriev.graduationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.grigoriev.graduationproject.model.Role;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateDto {
    private String old_name;
    private String old_password;
    private String new_name;
    private String new_password;
    private String email;
}
