package ru.grigoriev.graduationproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "menu", indexes = @Index(columnList = "restaurant_id"))
public class Menu extends AbstractBaseEntity {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @JoinColumn(name = "restaurant_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @JoinColumn(name = "dish_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Dish dish;

    @Column(name = "price", nullable = false)
    @Min(value = 1, message = "Price should be over 1")
    private double price;

    @Column(name = "dat", columnDefinition = "timestamp default now()", nullable = false, updatable = false)
    @NotNull
    @CreatedDate
    private LocalDate dat;

    public String getDat() {
        return this.dat.format(formatter);
    }
}
