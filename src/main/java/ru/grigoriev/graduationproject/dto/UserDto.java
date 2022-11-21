package ru.grigoriev.graduationproject.dto;

import lombok.*;
import ru.grigoriev.graduationproject.model.Role;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private static SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private String name;
    private String email;
    private Set<Role> roles;
    private String created_at;
    private String updated_at;

    public void setCreated_at(Date date) {
        this.created_at = format.format(date);
    }

    public void setUpdated_at(Date date) {
        this.updated_at =  format.format(date);
    }
}
