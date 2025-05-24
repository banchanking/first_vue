package com.jpa.maria.spring.controller;

import com.jpa.maria.spring.entity.Item;
import com.jpa.maria.spring.entity.Member;
import com.jpa.maria.spring.repository.ItemRepository;
import com.jpa.maria.spring.repository.MemberRepository;
import com.jpa.maria.spring.service.JwtService;
import com.jpa.maria.spring.service.JwtServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@lombok.extern.slf4j.Slf4j
public class AccountController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    JwtService jwtService;

    @PostMapping("/account/login") // 로그인 요청이 왔을시에
    public ResponseEntity<?> login(@RequestBody Map<String, String> params,
                                   HttpServletResponse res) {
        Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));

        if (member != null) { //멤버 값이 null 이 아닐시에
            int id = member.getId(); //id값을
            String token = jwtService.getToken("id", id); // 토큰화 시켜서
            log.info("로그인 성공");
            Cookie cookie = new Cookie("token", token); //토큰을 쿠키에 넣은다음에
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            res.addCookie(cookie); // 응답값에 쿠키 추가

            return new ResponseEntity<>(id, HttpStatus.OK);

        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/account/logout")
    public ResponseEntity<?> logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        log.info("로그아웃");
        res.addCookie(cookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/account/check")
    public ResponseEntity<?> check(@CookieValue(value= "token", required = false) String token) {
        Claims claims = jwtService.getClaims(token);
        int id = 0;
        if (claims != null) {
            id = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
