package com.example.Bar.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
}
