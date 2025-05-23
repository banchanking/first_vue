package com.jpa.maria.spring.repository;

import com.jpa.maria.spring.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {

}
