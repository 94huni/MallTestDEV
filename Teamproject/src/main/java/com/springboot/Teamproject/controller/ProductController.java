package com.springboot.Teamproject.controller;

import com.springboot.Teamproject.Service.ProductService;
import com.springboot.Teamproject.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.SimpleTimeZone;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //상품리스트
    @GetMapping("/product")
    public String productMain(Model model, Pageable pageable){

        Page<Product> productList = this.productService.getList(pageable);
        model.addAttribute("list", productList);

        //페이지
        int nowPage = productList.getPageable().getPageNumber()+1;
        int startPage = Math.max(nowPage, 1);
        int endPage = Math.min(nowPage + 5, productList.getTotalPages());
        model.addAttribute("nowPage" , nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "product/main";
    }

    //상품정보페이지
    @GetMapping("/product/view/{pno}")
    public String productView(@PathVariable("pno") int pno, Model model){

        Product productList = this.productService.getProduct(pno);
        model.addAttribute("productView", productList);

        return "product/view";
    }


    //검색결과
    @GetMapping("/product/search")
    public String productSearchResult(String searchKeyword, Pageable pageable, Model model){
        Page<Product> list;

        list = productService.searchProduct(pageable, searchKeyword);
        model.addAttribute("searchKeyword", list);


        return "redirect:/product/search";
    }




}
