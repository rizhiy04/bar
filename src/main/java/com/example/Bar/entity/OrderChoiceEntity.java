package com.example.Bar.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_choice")
public class OrderChoiceEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItemEntity menuItemEntity;

    @Column(name = "count")
    private Integer count;
}
