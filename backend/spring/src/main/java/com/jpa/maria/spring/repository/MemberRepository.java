package com.jpa.maria.spring.repository;

import com.jpa.maria.spring.entity.Item;
import com.jpa.maria.spring.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findByEmailAndPassword(String email, String password);
}
