package com.jpa.maria.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "orders")
@Getter
@Setter
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement
    private int id;

    @Column
    private int memberId;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 500, nullable = false)
    private String address;

    @Column(length = 10, nullable = false)
    private String payment;

    @Column(length = 16)
    private String cardNumber;

    @Column(length = 500, nullable = false)
    private String items;
}
