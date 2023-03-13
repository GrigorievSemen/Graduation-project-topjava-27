package ru.grigoriev.graduationproject.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Data
@Table(name = "dish", indexes = @Index(columnList = "name"))

public class Dish extends AbstractNamedEntity {

    public Dish(@NotBlank(message = "Name cannot be empty") @Size(min = 2, max = 128, message = "Name length must be between 2 and 128 characters") String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Dish:" +
                " id = " + id +
                ", name = " + name;
    }
}
