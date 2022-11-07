package ru.grigoriev.graduationproject.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private String name;
    private String email;
    //private Date registered;
    private String password;
}
