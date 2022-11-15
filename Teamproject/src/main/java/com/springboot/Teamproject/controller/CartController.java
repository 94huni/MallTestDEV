package com.springboot.Teamproject.controller;

import com.springboot.Teamproject.Service.CartService;
import com.springboot.Teamproject.Service.ProductService;
import com.springboot.Teamproject.entity.Cart;
import com.springboot.Teamproject.entity.Product;
import com.springboot.Teamproject.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CartController {
    @Autowired
    CartService cartService;

    private final com.springboot.Teamproject.service.UserService userService;
    private final ProductService productService;
    //장바구니목록
    @GetMapping("/cart")
    public String main(Model model, Principal principal){
        User user = this.userService.getUser(principal.getName()); //현재 로그인정보

        List<Cart> cart = this.cartService.getUserCart(user); //유저정보의 장바구니정보 가져오기

        model.addAttribute("cartList", cart);
        return "cart/main";
    }

    @GetMapping("/modify")
    public String count(@RequestParam int cart_id, @RequestParam int count){

        Cart cart = this.cartService.getCart(cart_id); //장바구니 정보 가져오기

        this.cartService.modifyCart(cart, count); // 수량설정

        return "redirect:/cart";
    }

    @GetMapping("/cart/plus/{number}")
    public String plus(@PathVariable int number){
        Cart cart = this.cartService.getCart(number);
        cartService.plusCart(cart);
        return "redirect:/cart";
    }

    @GetMapping("/cart/minus/{number}")
    public String minus(@PathVariable int number){
        Cart cart = this.cartService.getCart(number);
        cartService.minusCart(cart);
        return "redirect:/cart";
    }

    //장바구니 등록
    @GetMapping("/cart/{product}")
    public String save(Model model, @PathVariable("product") int product_id, Principal principal){

        Product product = this.productService.getProduct(product_id); //장바구니에 등록
        User user = this.userService.getUser(principal.getName()); //로그인된 유저정보 가져오기

        cartService.savedCart(product, user); //저장
        return "redirect:/cart";
    }


    //장바구니 삭제
    @DeleteMapping("/cart/delete/{number}")
    public String delete(@PathVariable int number){
        cartService.deleteCart(number);
        return "redirect:/cart";
    }
    //구매페이지
    @GetMapping("/buy")
    public String buyPage(Model model, Principal principal){
        User user = userService.getUser(principal.getName());
        List<Cart> buyCart = cartService.getUserCart(user);
        model.addAttribute("buyCart", buyCart);
        return "cart/buy";
    }

}
