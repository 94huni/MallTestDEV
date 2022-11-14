package com.springboot.Teamproject.controller;

import com.springboot.Teamproject.Service.ProductService;
import com.springboot.Teamproject.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.SimpleTimeZone;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //상품리스트
    @GetMapping("/product")
    public String productMain(Model model,
                              @PageableDefault(page = 0,size = 10, sort = "pno", direction = Sort.Direction.DESC)
                              Pageable pageable){

        Page<Product> productList = this.productService.getList(pageable);
        model.addAttribute("list", productList);

        //페이지
        int nowPage = productList.getPageable().getPageNumber()+1;
        int totalPage = productList.getTotalPages();

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("totalPage", totalPage);


        return "product/main";
    }

    //카테고리별 페이지
    @GetMapping("/product/{code}")
    public String productCategory(@PathVariable("code")String code, Model model){
        List<Product> categoryProduct  = productService.category(code);

        model.addAttribute("categoryProductList",categoryProduct);

        System.out.println(code);

        return "product/category";
    }


    //상품정보페이지
    @GetMapping("/product/view/{pno}")
    public String productView(@PathVariable("pno") int pno, Model model){

        Product productList = this.productService.getProduct(pno);
        model.addAttribute("productView", productList);

        return "product/view";
    }


    //검색기능
    @GetMapping("/product/search")
    public String productSearchResult(@RequestParam(value = "searchKeyword") String searchKeyword, Pageable pageable, Model model){
        Page<Product> list = productService.searchProduct(pageable, searchKeyword);

        model.addAttribute("searchKeyword", list);


        return "redirect:/product/search";
    }




}
