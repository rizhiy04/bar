package com.example.Bar.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "menu_items")
@Data
public class MenuItem extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;


}
