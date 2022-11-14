package com.springboot.Teamproject.controller;

import com.springboot.Teamproject.Service.ShopBoardService;
import com.springboot.Teamproject.entity.ShopBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class ShopBoardController {

    @Autowired
    private ShopBoardService shopBoardService;

    @GetMapping("/shop/board/list")
    public String shopBoardMain(Model model, @PageableDefault(page = 10, sort = "bno", direction = Sort.Direction.DESC)
                                Pageable pageable, String searchKeyword){
        Page<ShopBoard> list =null;

        if(searchKeyword == null){
            list = shopBoardService.shopBoardList(pageable);
        }else {
            list = shopBoardService.searchShopBoard(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber()+1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage+5, list.getTotalPages());

        model.addAttribute("list", shopBoardService.shopBoardList(pageable));
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "shop/boardList";
    }

    @GetMapping("/shop/board/write")
    public String shopBoardWrite() {
        return "shop/boardWrite";
    }
    @GetMapping("/shop/board/modify/{bno}")
    public String shopBoardModify(@PathVariable("bno") int bno, Model model){
        model.addAttribute("shopBoard", shopBoardService.selectShopBoard(bno));

        return "shop/boardModify";
    }

    @GetMapping("/shop/board/view")
    public String shopBoardView(Model model , int bno){
        model.addAttribute("shopBoard", shopBoardService.selectShopBoard(bno));
        return "shop/boardView";
    }

    @PostMapping("/shop/board/write-pro")
    public String writePro(ShopBoard shopBoard, Model model){
        model.addAttribute("shopBoard", shopBoardService.createShopBoard(shopBoard));
        shopBoardService.createShopBoard(shopBoard);
        return "redirect:/shop/board/list";
    }

    @PutMapping("/shop/board/update/{bno}")
    public String update(@PathVariable("bno") int bno, ShopBoard shopBoard, Model model){
        ShopBoard shopTemp = shopBoardService.selectShopBoard(bno);

        shopTemp.setTitle(shopBoard.getTitle());
        shopTemp.setContent(shopBoard.getContent());
        shopTemp.setImagePath(shopBoard.getImagePath());

        shopBoardService.createShopBoard(shopTemp);

        return "redirect:/shop/board/view";
    }

    @DeleteMapping("/shop/board/delete/{bno}")
    String delete(@PathVariable int bno)throws Exception{
        shopBoardService.deleteShopBoard(bno);

        return "redirect:/shop/board/list";
    }

}
