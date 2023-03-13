package ru.grigoriev.graduationproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Table(name = "users", indexes = @Index(columnList = "name"))
public class User extends AbstractNamedEntity {

    public User(@NotBlank(message = "Name cannot be empty") @Size(min = 2, max = 128, message = "Name length must be between 2 and 128 characters") String name
            , String email, String password) {
        super(name);
        this.email = email;
        this.password = password;
    }

//
//    public User(Integer id, String name, String email, String password, boolean enabled, Collection<Role> roles) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.enabled = enabled;
//        setRoles(roles);
//        this.status = Status.ACTIVE;
//    }

    @NotBlank(message = "Email cannot be empty")
    @Column(name = "email", nullable = false, unique = true)
    @Email
    @Size(max = 128)
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 5, max = 128, message = "Password length must be between 5 and 128 characters")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "ACTIVE")
    protected Status status;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), indexes = @Index(columnList = "role"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public void setRoles(Collection<Role> usersRoles) {
        this.roles = CollectionUtils.isEmpty(usersRoles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(usersRoles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email=" + email +
                ", name=" + name +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}