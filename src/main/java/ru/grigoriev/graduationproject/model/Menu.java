package ru.grigoriev.graduationproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "dish_id"}, name = "menu_unique_restaurant_dish_idx")})
public class Menu extends AbstractBaseEntity {

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

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;
}
