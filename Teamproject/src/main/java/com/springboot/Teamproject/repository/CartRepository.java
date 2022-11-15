package com.springboot.Teamproject.repository;

import com.springboot.Teamproject.entity.Cart;
import com.springboot.Teamproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByuserprofile(User user);

}
