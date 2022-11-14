package com.springboot.Teamproject.ShopBoardTest;

import com.springboot.Teamproject.Service.ShopBoardService;
import com.springboot.Teamproject.entity.ShopBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ShopBoardTest {

    @Autowired
    private ShopBoardService shopBoardService;


    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("게시판리스트테스트")
    public void shopBoardTest(){
        final int bno = 1234;
        final String title = "testTitle";
        final String content = "testContent";
        final String createDate = "22-22-22 22:22:22";
        final String userprofile = "test";


        ShopBoard shopBoardlist = new ShopBoard();
        shopBoardlist.setBno(bno);
        shopBoardlist.setTitle(title);
        shopBoardlist.setContent(content);
        shopBoardlist.setCreateDate(createDate);
        shopBoardlist.setUserprofile(userprofile);


        ShopBoard shopBoardResult = shopBoardService.createShopBoard(shopBoardlist);

        System.out.println(shopBoardResult);
        //assertThat(shopBoardResult).isNotNull();

    }

}
