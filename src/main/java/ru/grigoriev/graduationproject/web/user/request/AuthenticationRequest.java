package ru.grigoriev.graduationproject.web.user.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String name;
    private String password;
}
