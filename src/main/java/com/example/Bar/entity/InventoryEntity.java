package com.example.Bar.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "inventory")
@Data
public class InventoryEntity extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "count")
    private Integer count;
}
