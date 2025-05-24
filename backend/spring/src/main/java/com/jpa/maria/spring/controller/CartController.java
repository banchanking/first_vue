package com.jpa.maria.spring.controller;

import com.jpa.maria.spring.entity.Cart;
import com.jpa.maria.spring.entity.Item;
import com.jpa.maria.spring.repository.CartRepository;
import com.jpa.maria.spring.repository.ItemRepository;
import com.jpa.maria.spring.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@lombok.extern.slf4j.Slf4j
public class CartController {

    @Autowired
    JwtService jwtService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/cart/items")
    public ResponseEntity<?> getCartItems(@CookieValue(value = "token", required = false) String token) {
        if (!jwtService.isValidate(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        int memberId = jwtService.getId(token);
        List<Cart> carts = cartRepository.findByMemberId(memberId);
        List<Integer> itemIds = carts.stream().map(Cart::getItemId).toList(); // itemId를 리스트로 추출

        List<Item> items = itemRepository.findByIdIn(itemIds);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping("/cart/items/{itemId}")
    public ResponseEntity<?> pushCartItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(value = "token", required = false) String token
    ) {
        if (!jwtService.isValidate(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setItemId(itemId);
            newCart.setMemberId(memberId);
            cartRepository.save(newCart);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/cart/items/{itemId}")
    public ResponseEntity<?> removeCartItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(value = "token", required = false) String token
    ) {
        log.info("장바구니 삭제");
        if (!jwtService.isValidate(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        cartRepository.delete(cart);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
