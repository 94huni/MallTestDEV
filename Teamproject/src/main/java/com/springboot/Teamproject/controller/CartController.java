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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        User user = this.userService.getUser(principal.getName());

        List<Cart> cart = this.cartService.getUserCart(user);
        model.addAttribute("cartList", cart);
        return "cart/main";
    }
    //장바구니 등록
    @GetMapping("/cart-pro/{product}")
    public String save(Model model, @PathVariable("product") int product_id, Principal principal){

        Product product = this.productService.getProduct(product_id);
        User user = this.userService.getUser(principal.getName());

        cartService.savedCart(product, user);
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
