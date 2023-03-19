package ru.grigoriev.graduationproject.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@SuperBuilder
@Table(name = "restaurant", indexes = @Index(columnList = "name"))
public class Restaurant extends AbstractNamedEntity {

    public Restaurant(@NotBlank(message = "Name cannot be empty") @Size(min = 2, max = 128, message = "Name length must be between 2 and 128 characters") String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Restaurant:" +
                " id = " + id +
                ", name = " + name;
    }
}
