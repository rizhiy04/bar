package com.example.Bar.entity;


import com.example.Bar.security.Roles;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class UserEntity extends BaseEntity{

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private Roles roles;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private UserDiscountCardEntity userDiscountCardEntity;
}
