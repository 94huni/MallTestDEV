package com.springboot.Teamproject.controller;

import com.springboot.Teamproject.Service.CartService;
import com.springboot.Teamproject.entity.Cart;
import com.springboot.Teamproject.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    CartService cartService;
    //장바구니목록
    @GetMapping("/cart")
    public String main(Model model, Product product){
        List<Cart> cart = cartService.getCart(product);
        model.addAttribute("cartList", cart);
        return "cart/main";
    }
    //장바구니 등록
    @PostMapping("/cart-pro")
    public String save(Model model, Product product){
        Cart savedCart = cartService.savedCart(product);
        model.addAttribute("savedCart", savedCart);
        return "redirect:/cart";
    }
    //장바구니 삭제
    @DeleteMapping("/cart/delete")
    public String delete(Product product){
        cartService.deleteCart(product.getCart().getProduct());
        return "redirect:/cart";
    }
    //구매페이지
    @GetMapping("/buy")
    public String buyPage(Product product, Model model){
        List<Cart> buyCart = cartService.getCart(product);
        model.addAttribute("buyCart", buyCart);
        return "cart/buy";
    }

}
