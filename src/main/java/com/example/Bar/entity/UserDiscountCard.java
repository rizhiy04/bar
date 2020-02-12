package com.example.Bar.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "discount_card")
@Data
public class UserDiscountCard extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "client_discount")
    private Double clientDiscount;

    @Column(name = "all_spent_money")
    private Double allSpentMoney;

    @OneToOne(mappedBy = "userDiscountCard")
    private User user;
}
