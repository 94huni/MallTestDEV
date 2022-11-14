package com.springboot.Teamproject.Service.Impl;

import com.springboot.Teamproject.entity.ShopBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShopBoardServiceImpl {
    ShopBoard selectShopBoard(int bno);

    ShopBoard createShopBoard(ShopBoard shopBoard);

    ShopBoard modifyShopBoard(int bno, String title, String content, String imagePath)throws Exception;

    void deleteShopBoard(int bno)throws Exception;

    Page<ShopBoard> searchShopBoard(String searchKeyword, Pageable pageable);

    Page<ShopBoard> shopBoardList(Pageable pageable);
}
