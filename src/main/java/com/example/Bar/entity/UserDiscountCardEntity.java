package com.example.Bar.entity;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "discount_card")
@Data
public class UserDiscountCardEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "client_discount")
    private Double clientDiscount;

    @Column(name = "all_spent_money")
    private BigDecimal allSpentMoney;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;
}
