package com.springboot.Teamproject.repository;

import com.springboot.Teamproject.entity.ShopBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopBoardRepository extends JpaRepository<ShopBoard, Integer> {
    Page<ShopBoard> findByTitleContaining(String searchKeyword, Pageable pageable);
}
