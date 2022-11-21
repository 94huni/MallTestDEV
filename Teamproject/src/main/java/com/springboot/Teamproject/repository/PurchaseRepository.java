package com.springboot.Teamproject.repository;

import com.springboot.Teamproject.entity.Purchase;
import com.springboot.Teamproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findAllByUserprofile(User user);
}
