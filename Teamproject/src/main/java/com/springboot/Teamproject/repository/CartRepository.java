package com.springboot.Teamproject.repository;

import com.springboot.Teamproject.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
