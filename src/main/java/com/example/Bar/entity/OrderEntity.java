package com.example.Bar.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity extends BaseEntity {

    @Column(name = "table_number")
    private Integer tableNumber;

    @Column(name = "time_open")
    private LocalDateTime timeOpen;

    @Column(name = "time_close")
    private LocalDateTime timeClose;

    @OneToMany(mappedBy = "orderEntity")
    private List<OrderChoiceEntity> orderChoiceEntities = new ArrayList<>();
}
