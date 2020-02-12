package com.example.Bar.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order")
@Data
public class Order extends BaseEntity {

    @Column(name = "table_number")
    private Integer tableNumber;

    @Column(name = "time_open")
    private LocalDateTime timeOpen;

    @Column(name = "time_close")
    private LocalDateTime timeClose;

    @ManyToMany
    @JoinTable(name = "order_menu_items",
                joinColumns = @JoinColumn(name = "order_id"),
                inverseJoinColumns = @JoinColumn(name = "menu_item_id"))
    private List<MenuItem> menuItems;
}
