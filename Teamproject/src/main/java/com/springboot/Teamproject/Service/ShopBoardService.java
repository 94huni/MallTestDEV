package com.springboot.Teamproject.Service;

import com.springboot.Teamproject.Service.Impl.ShopBoardServiceImpl;
import com.springboot.Teamproject.entity.ShopBoard;
import com.springboot.Teamproject.repository.ShopBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ShopBoardService implements ShopBoardServiceImpl {


    private final ShopBoardRepository shopBoardRepository;

    @Autowired
    public ShopBoardService(ShopBoardRepository shopBoardRepository) {

        this.shopBoardRepository = shopBoardRepository;
    }

    @Override
    public ShopBoard selectShopBoard(int bno) {

        return shopBoardRepository.getById(bno);
    }

    @Override
    public ShopBoard createShopBoard(ShopBoard shopBoard) {
        shopBoard.setCreateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss")));

        return shopBoardRepository.save(shopBoard);
    }

    @Override
    public ShopBoard modifyShopBoard(int bno, String title, String content, String imagePath) throws Exception {
        Optional<ShopBoard> selectShopBoard = shopBoardRepository.findById(bno);

        ShopBoard modifyShopBoard;
        if(selectShopBoard.isPresent()){
            ShopBoard shopBoard = selectShopBoard.get();
            shopBoard.setTitle(title);
            shopBoard.setContent(content);
            shopBoard.setImagePath(imagePath);

            modifyShopBoard = shopBoardRepository.save(shopBoard);
        }else {
            throw new Exception();
        }

        return modifyShopBoard;
    }

    @Override
    public void deleteShopBoard(int bno) throws Exception {
        Optional<ShopBoard> selectShopBoard = shopBoardRepository.findById(bno);
        if(selectShopBoard.isPresent()){
            ShopBoard shopBoard = selectShopBoard.get();

            shopBoardRepository.delete(shopBoard);

        }else {
            throw new Exception();
        }
    }

    @Override
    public Page<ShopBoard> searchShopBoard(String searchKeyword, Pageable pageable) {
        return shopBoardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    @Override
    public Page<ShopBoard> shopBoardList(Pageable pageable) {

        return shopBoardRepository.findAll(pageable);
    }


}
