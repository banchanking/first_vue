package com.jpa.maria.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "carts")
@Getter
@Setter
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement
    private int id;

    @Column
    private int memberId;

    @Column
    private int itemId;
}
