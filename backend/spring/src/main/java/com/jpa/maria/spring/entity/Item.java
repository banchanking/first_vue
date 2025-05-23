package com.jpa.maria.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "items")
@Getter
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100)
    private String imgPath;

    @Column
    private int price;

    @Column
    private int discountPer;
}
