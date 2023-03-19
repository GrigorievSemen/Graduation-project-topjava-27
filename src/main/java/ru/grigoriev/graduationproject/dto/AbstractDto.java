package ru.grigoriev.graduationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public abstract class AbstractDto {
    private String name;
    private Integer id;

    @Override
    public String toString() {
        return "AbstractDto{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
