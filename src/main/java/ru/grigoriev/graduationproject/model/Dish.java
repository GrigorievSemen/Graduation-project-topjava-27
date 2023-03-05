package ru.grigoriev.graduationproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "dish",  indexes = @Index(columnList = "name"))
public class Dish extends AbstractNamedEntity {

    @Override
    public String toString() {
        return "Dish:" +
                " id = " + id +
                ", name = " + name;
    }
}
