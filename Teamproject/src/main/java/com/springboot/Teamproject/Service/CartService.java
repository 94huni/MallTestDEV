package com.springboot.Teamproject.service;

import com.springboot.Teamproject.entity.Cart;
import com.springboot.Teamproject.entity.Product;
import com.springboot.Teamproject.entity.User;
import com.springboot.Teamproject.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    //제품정보 장바구니 저장
    public void savedCart (Product product, User user){
        //장바구니 정보를 저장해줄 Cart
        Cart cart = new Cart();
        //Cart Entity 의 userProfile 에 값을 넣어줌
        cart.setUserprofile(user);
        //Cart Entity 의 product 에 값을 넣어줌
        cart.setProduct(product);
        //Cart Entity 의 productCount 값을 초기설정 1개로 넣어줌
        cart.setProductCount(1);

        //CartRepository 의 save 를 사용해서 cart 의 정보를 저장
        this.cartRepository.save(cart);

    }

    //유저의 정보를 가져옴
    public List<Cart> getUserCart(User user){
        //CartRepository 에서 만들어둔 findAllByUserprofile 을 사용해서 user 정보에 대한 장바구니 리스트를 가져옴
        return cartRepository.findAllByUserprofile(user);
    }

    public void modifyCart(Cart cart, int count){ //카운트의 값을 받아서 받아왔던 cart 의 수량을 변경
        //Cart Entity 의 productCount 에 입력받은 count 를 입력
        cart.setProductCount(count);
        //CartRepository 의 save 를 이용하여 저장
        this.cartRepository.save(cart);
    }

    //수량 1씩 추가
    public void plusCart(Cart cart){ //카트의 정보에서 카운트 수정
        //Cart Entity 의 productCount 를 1씩 증가시킴
        cart.setProductCount(cart.getProductCount()+1);
        //CartRepository 의 save 를 이용하여 저장
        cartRepository.save(cart);
    }
    //수량 1씩 감소
    public void minusCart(Cart cart){
        //Cart 의 productCount 가 1보다 클때만 적용
        if(cart.getProductCount() > 1) {
            //Cart Entity 의 productCount 를 1씩 감소시킴
            cart.setProductCount(cart.getProductCount() - 1);
            //CartRepository 의 save 를 이용하여 저장
            this.cartRepository.save(cart);
        }
    }

    public Cart getCart(int cart_id){  //카트 id 를 통해 카트정보 가져오기
        //카트의 id 로 CartRepository 에서 findById 에 cart_id를 매개변수로 찾아옴
        return this.cartRepository.findById(cart_id).get();
    }

    //장바구니삭제
    public void deleteCart(int number){
        //CartRepository 의 deleteById 를 매개변수 cart 의 number 를 통해서 한개씩 삭제 하는 기능
        this.cartRepository.deleteById(number);
    }

    //장바구니 전체리스트 삭제
//    public void deleteCart(User user){
//
//        List<Cart> cart =cartRepository.findAllByUserprofile(user);
//        this.cartRepository.deleteAll(cart);
//    }
}
