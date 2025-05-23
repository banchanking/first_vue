package com.jpa.maria.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
@Entity
@Getter
@Table(name= "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement
    private int id;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;
}
