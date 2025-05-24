package com.jpa.maria.spring.dto;

import lombok.Getter;

@Getter
public class OrderDTO {
    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private String items;
}
