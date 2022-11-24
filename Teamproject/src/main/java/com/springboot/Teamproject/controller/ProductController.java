package com.springboot.Teamproject.controller;

import com.springboot.Teamproject.DTO.ProductSearchForm;
import com.springboot.Teamproject.service.ProductService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //상품리스트
    @GetMapping("/product")
    //0페이지부터, 한화면에 게시글 12개, 상품번호기준으로 역순으로 정렬
    public String productMain(@Valid ProductSearchForm productSearchForm,Model model, Pageable pageable, @RequestParam(value = "page", defaultValue = "0")
    Integer pageNumber, @RequestParam (value = "size", defaultValue = "5") int pageSize) {

        Page<Product> productPage = this.productService.getList(pageNumber);

       model.addAttribute("productPage",productPage);
       model.addAttribute("productList",productPage.getContent());

       int recentBno = productPage.getContent().get(0).getPno();

       Product product = this.productService.getProduct(recentBno);
       model.addAttribute("productBoard",product);

       int nowPage = productPage.getPageable().getPageNumber()+1;
       int prevPage = nowPage-1;
       int afterPage = nowPage+1;
       int endPage = productPage.getTotalPages()+1;

       model.addAttribute("nowPage",nowPage);
       model.addAttribute("prevPage",prevPage);
       model.addAttribute("afterPage",afterPage);
       model.addAttribute("endPage",endPage);

        return "product/main";
    }

    //카테고리별 페이지
    @GetMapping("/product/{code}")
    public String productCategory(@PathVariable("code") String code, Pageable pageable,Model model,
                                  @RequestParam(value = "page", defaultValue = "0")
                                  Integer pageNumber, @RequestParam (value = "size", defaultValue = "5") int pageSize) {

        Page<Product> categoryProduct = this.productService.category(code,pageNumber);

        model.addAttribute("productPage",categoryProduct);
        model.addAttribute("productList",categoryProduct.getContent());

//        int recentBno = categoryProduct.getContent().get(0).getPno();
//
//        Product product = this.productService.getProduct(recentBno);
//        model.addAttribute("productBoard",product);

        int nowPage = categoryProduct.getPageable().getPageNumber()+1;
        int prevPage = nowPage-1;
        int afterPage = nowPage+1;
        int endPage = categoryProduct.getTotalPages()+1;

        model.addAttribute("nowPage",nowPage);
        model.addAttribute("prevPage",prevPage);
        model.addAttribute("afterPage",afterPage);
        model.addAttribute("endPage",endPage);

//        //카테고리를 변수로받아서 제품카테고리 리스트에 저장
//        Page<Product> categoryProduct = productService.category(code,pageable);
//
//        //모델로 제품카테고리를 넘겨준다.
//        model.addAttribute("categoryProduct", categoryProduct);

//        int nowPage = categoryProduct.getPageable().getPageNumber() + 1; //현재페이지 0페이지부터 시작하기때문에 +1
//        if(nowPage <=0){
//            nowPage=categoryProduct.getPageable().getPageNumber() + 1;
//        }
//        int totalPage = categoryProduct.getTotalPages();
//
//        //startPage Math.max는 매개변수로 들어온 두값중에 높은값 반환 nowPage-4는 ex(12페이지인경우 1페이지부터가 아닌 8페이지부터 화면에 나오게끔 처리)
//        int startPage = Math.max(nowPage - 4, 1); //블럭에서 보여줄 시작 페이지
//        //endPage Math.min은 매개변수로 들어온 두값중 작은값반환 ex(전체페이지가 10페이지인데 nowpage가 9인경우 14가되어 전체페이지를 넘는경우 전체페이지를 보여주게끔)
//        int endPage = Math.min(nowPage + 5, categoryProduct.getTotalPages()); //블럭에서 보여줄 마지막 페이지

        //모델로 변수를 넘겨준다
//        model.addAttribute("categoryProduct",categoryProduct);
//        model.addAttribute("nowPage", nowPage);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//
//        System.out.println(code);

        //카테고리 페이지 출력
        return "product/category";
    }


    //상품정보페이지
    @GetMapping("/product/view/{pno}")
    public String productView(@PathVariable("pno") int pno, Model model) {

        //제품번호를 변수로 받아와서 등록된 상품정보를 저장
        Product productList = this.productService.getProduct(pno);

        //모델에 상품정보를 넘겨준다
        model.addAttribute("productView", productList);

        //상품정보 페이지 출력
        return "product/detail";
    }


    //검색기능
    @PostMapping("/product/search")
    public String productSearchResult(@Valid ProductSearchForm productSearchForm, Pageable pageable, Model model) {
        Page<Product> list = productService.searchProduct(productSearchForm.getSearchKeyword(), pageable);

        if (list != null) {
            model.addAttribute("productPage", list);
            model.addAttribute("productList", list.getContent());



            int nowPage = list.getPageable().getPageNumber() + 1;
            int prevPage = nowPage - 1;
            int afterPage = nowPage + 1;
            int endPage = list.getTotalPages() + 1;

            model.addAttribute("nowPage", nowPage);
            model.addAttribute("prevPage", prevPage);
            model.addAttribute("afterPage", afterPage);
            model.addAttribute("endPage", endPage);




        }
        return "product/main";
    }

}