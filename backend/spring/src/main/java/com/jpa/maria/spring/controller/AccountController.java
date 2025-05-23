package com.jpa.maria.spring.controller;

import com.jpa.maria.spring.entity.Item;
import com.jpa.maria.spring.entity.Member;
import com.jpa.maria.spring.repository.ItemRepository;
import com.jpa.maria.spring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/account/login")
    public int login(@RequestBody Map<String, String> params) {
        Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));

        if(member != null) {
            return member.getId();
        }
        return 0;
    }
}
