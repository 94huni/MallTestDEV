package com.springboot.Teamproject.controller;

import com.springboot.Teamproject.entity.Cart;
import com.springboot.Teamproject.entity.Purchase;
import com.springboot.Teamproject.entity.User;
import com.springboot.Teamproject.repository.CartRepository;
import com.springboot.Teamproject.service.CartService;
import com.springboot.Teamproject.service.PurchaseService;
import com.springboot.Teamproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final CartService cartService;
    private final UserService userService;

    // 제품 구매 후 장바구니 삭제
    @GetMapping("/purchase/buy")
    public String purchaseMain(Principal principal){

        // 사용자 정보를 가져와 user에 담음
        User user = userService.getUser(principal.getName());

        // 아이디 정보를 기반으로 유저 정보를 가져와 cartList에 담음
        List<Cart> cartList = cartService.getUserCart(user);

        // for each 문으로 cart에 getNumber=구매번호 를 조회 후 purchase에 저장 한뒤 카트에 저장된 데이터 삭제
        for(Cart cart : cartList){
            this.purchaseService.saveBuy(cart.getNumber());
            this.cartService.deleteCart(cart.getNumber());
        }

        return "redirect:/purchase/list";
    }

    // 결제 리스트
    @GetMapping("/purchase/list")
    public String getPurchaseList(Principal principal, Model model){

        // 구매서비스에서 getList에 사용자이름을 불러와 리스트로 구매엔티티를 담음
        List<Purchase> getList =  this.purchaseService.getList(principal.getName());

        int totalPrice = 0;
        for(int i =0; i<getList.size(); i++){
            totalPrice += getList.get(i).getProduct().getPrice()*getList.get(i).getProductCount();
        }

        model.addAttribute("getList", getList);
        model.addAttribute("totalPrice", totalPrice);

        return "purchase/purchase_main";

    }


}
