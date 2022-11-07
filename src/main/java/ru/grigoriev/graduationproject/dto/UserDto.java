package ru.grigoriev.graduationproject.dto;

import lombok.*;
import ru.grigoriev.graduationproject.model.Role;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private String name;
    private String email;
    private Date registered = new Date();
    private Set<Role> roles;
}
