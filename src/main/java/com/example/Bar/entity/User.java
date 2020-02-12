package com.example.Bar.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity{

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    private Permission permission;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private UserDiscountCard userDiscountCard;
}
