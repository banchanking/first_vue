package com.jpa.maria.spring.controller;

import com.jpa.maria.spring.dto.OrderDTO;
import com.jpa.maria.spring.entity.Item;
import com.jpa.maria.spring.entity.Order;
import com.jpa.maria.spring.repository.CartRepository;
import com.jpa.maria.spring.repository.ItemRepository;
import com.jpa.maria.spring.repository.OrderRepository;
import com.jpa.maria.spring.service.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    @Autowired
    JwtService jwtService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartRepository cartRepository;

    @GetMapping("/orders")
    public ResponseEntity<?> getOrder(
            @CookieValue(value = "token", required = false) String token
    ) {
        if (!jwtService.isValidate(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        int memberId = jwtService.getId(token);
        List<Order> orders = orderRepository.findByMemberIdOrderByIdDesc(memberId);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @Transactional
    @PostMapping("/orders")
    public ResponseEntity<?> pushOrder(
            @RequestBody OrderDTO dto,
            @CookieValue(value = "token", required = false) String token
    ) {

        if (!jwtService.isValidate(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Order newOrder = new Order();
        int memberId = jwtService.getId(token);
        newOrder.setMemberId(memberId);
        newOrder.setName(dto.getName());
        newOrder.setAddress(dto.getAddress());
        newOrder.setPayment(dto.getPayment());
        newOrder.setCardNumber(dto.getCardNumber());
        newOrder.setItems(dto.getItems());

        orderRepository.save(newOrder);
        cartRepository.deleteByMemberId(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
