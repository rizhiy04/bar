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

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToOne(mappedBy = "userEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private UserDiscountCardEntity userDiscountCardEntity;
}
