package ru.grigoriev.graduationproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 128, message = "Name length must be between 2 and 128 characters")
    @Column(name = "name", nullable = false, unique = true)
    protected String name;

    @Override
    public String toString() {
        return super.toString() + '(' + name + ')';
    }
}