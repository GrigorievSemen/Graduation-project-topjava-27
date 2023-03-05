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
@Table(name = "restaurant",indexes = @Index(columnList = "name"))

public class Restaurant extends AbstractNamedEntity {

    @Override
    public String toString() {
        return "Restaurant:" +
                " id = " + id +
                ", name = " + name;
    }
}
