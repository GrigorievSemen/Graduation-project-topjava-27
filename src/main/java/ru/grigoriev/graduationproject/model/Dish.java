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
@Data
@Table(name = "dish", indexes = @Index(columnList = "name"))
@SuperBuilder
public class Dish extends AbstractNamedEntity {

    @Override
    public String toString() {
        return "Dish:" +
                " id = " + id +
                ", name = " + name;
    }
}
