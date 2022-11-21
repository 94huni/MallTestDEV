package com.springboot.Teamproject.controller;

import com.springboot.Teamproject.Service.CartService;
import com.springboot.Teamproject.Service.PurchaseService;
import com.springboot.Teamproject.entity.Cart;
import com.springboot.Teamproject.entity.Purchase;
import com.springboot.Teamproject.entity.User;
import com.springboot.Teamproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final CartService cartService;
    private final UserService userService;

    // 제품 구매 후 장바구니 삭제
    @GetMapping("/purchase/buy")
    public String purchaseMain(Principal principal){

        User user = userService.getUser(principal.getName());
        List<Cart> cartList = cartService.getUserCart(user);

        for(Cart cart : cartList){
            this.purchaseService.saveBuy(cart.getNumber());
            this.cartService.deleteCart(cart.getNumber());
        }


        return "redirect:/purchase/list";
    }

    // 결제 리스트
    @GetMapping("/purchase/list")
    public String getPurchaseList(Principal principal, Model model){

        List<Purchase> getList =  this.purchaseService.getList(principal.getName());
        model.addAttribute("getList", getList);

        return "purchase/buy";

    }

}
