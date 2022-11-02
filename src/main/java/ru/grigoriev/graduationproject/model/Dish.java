package ru.grigoriev.graduationproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = " dish_unique_name_idx")})
public class Dish extends AbstractNamedEntity {

    @Override
    public String toString() {
        return "Dish:" +
                " id = " + id +
                ", name = " + name;
    }
}
