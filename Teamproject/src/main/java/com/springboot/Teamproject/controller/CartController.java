package com.springboot.Teamproject.controller;

import com.springboot.Teamproject.service.CartService;
import com.springboot.Teamproject.service.ProductService;
import com.springboot.Teamproject.entity.Cart;
import com.springboot.Teamproject.entity.Product;
import com.springboot.Teamproject.entity.User;
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

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    //장바구니목록
    @GetMapping("/cart")
    public String main(Model model, Principal principal){
        User user = this.userService.getUser(principal.getName()); //현재 로그인정보 (userService 의 getUser Method)

        List<Cart> cart = this.cartService.getUserCart(user); //유저정보의 장바구니정보 가져오기 (cartService 의 getUserCart method)

        //전체금액 계산
        int totalPrice=0;

        for(int i=0; i<cart.size();i++){
            totalPrice += cart.get(i).getProduct().getPrice() * cart.get(i).getProductCount();
        }

        model.addAttribute("totalPrice" , totalPrice); //전체금액 

        model.addAttribute("cartList", cart); //유저에 대한 카트정보
        return "cart/cart_main";

    }

    @GetMapping("/modify")//숫자로 변경기능 사용시 사용
    public String count(@RequestParam int cart_id, @RequestParam int count){

        Cart cart = this.cartService.getCart(cart_id); //장바구니 정보 가져오기

        this.cartService.modifyCart(cart, count); // 수량설정

        return "redirect:/cart";
    }

    @GetMapping("/cart/plus/{number}") //장바구니 상품갯수 +1
    public String plus(@PathVariable int number){
        Cart cart = this.cartService.getCart(number); //카트정보
        this.cartService.plusCart(cart); // + 1
        return "redirect:/cart";
    }

    @GetMapping("/cart/minus/{number}") //장바구니 상품개수 -1
    public String minus(@PathVariable int number){
        Cart cart = this.cartService.getCart(number); //카트정보
        this.cartService.minusCart(cart); // -1
        return "redirect:/cart";
    }

    //장바구니 등록
    @GetMapping("/cart/{product}")
    public String save(@PathVariable("product") int product_id, Principal principal){

        Product product = this.productService.getProduct(product_id); //장바구니에 등록 (productService 의 getProduct method
        User user = this.userService.getUser(principal.getName()); //로그인된 유저정보 가져오기
        List<Cart> cart = this.cartService.getUserCart(user); //유저의 장바구니 정보

        //중복제거
        if(cart.size() > 0){ // 유저의 장바구니 정보가 있으면 반복문 실행
            for (Cart value : cart) { //cart 의 크기만큼 반복
                if(value.getProduct().equals(product)){
                    System.out.println("이미등록된 상품");
                    break; //반복문 종료 (반복문을 종료하지않으면 리스트의 크기만큼 다시 저장됨)
                }else {
                    this.cartService.savedCart(product, user); //없으면 저장
                    break; //반복문종료
                }
            }
        }else
            this.cartService.savedCart(product, user); //저장

        return "redirect:/cart";
    }


    //장바구니 삭제
    @GetMapping("/cart/delete/{number}") 
    public String delete(@PathVariable int number){
        this.cartService.deleteCart(number); //카트 넘버로 가져와서 지우기
        return "redirect:/cart";
    }

    @GetMapping("/cart/buy") //구매확정, 수량변경 삭제불가능한 페이지
    public String buyPage(Model model, Principal principal){
        //로그인된 유저정보확인
        User user = userService.getUser(principal.getName());
        //유저에대한 장바구니 정보
        List<Cart> buyCart = cartService.getUserCart(user);

        //전체금액
        int totalPrice = 0;
        for(int i=0; i<buyCart.size();i++){
            totalPrice += buyCart.get(i).getProduct().getPrice() * buyCart.get(i).getProductCount();
        }
        model.addAttribute("totalPrice", totalPrice); //전체금액 계산된정보 넘겨줌

        model.addAttribute("buyCart", buyCart);//로그인된 유저정보에 대한 리스트를 넘겨줌
        return "cart/cart_buy";
    }

}
