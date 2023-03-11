package ru.grigoriev.graduationproject.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseWebResponse {
    private String errorMessage;
}
