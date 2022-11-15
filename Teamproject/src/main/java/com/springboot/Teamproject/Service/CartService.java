package com.springboot.Teamproject.Service;

import com.springboot.Teamproject.entity.Cart;
import com.springboot.Teamproject.entity.Product;
import com.springboot.Teamproject.entity.User;
import com.springboot.Teamproject.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    //제품정보 장바구니 저장
    public void savedCart (Product product, User user){

        Cart cart = new Cart();
        cart.setUserprofile(user);
        cart.setProduct(product);

        cartRepository.save(cart);

    }

    public List<Cart> getUserCart(User user){
        return cartRepository.findAllByuserprofile(user);
    }

    //장바구니삭제
    public void deleteCart(int number){

        cartRepository.deleteById(number);
    }
}
