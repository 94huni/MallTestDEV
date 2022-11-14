package com.springboot.Teamproject.Service;

import com.springboot.Teamproject.entity.Cart;
import com.springboot.Teamproject.entity.Product;
import com.springboot.Teamproject.entity.User;
import com.springboot.Teamproject.repository.CartRepository;
import com.springboot.Teamproject.repository.ProductRepository;
import com.springboot.Teamproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;


    public Cart savedCart (Product product){
        Cart cart = savedCart(product);
        return cartRepository.save(cart);
    }

    public List<Cart> getCart(Product product){
        return getCart(product);
    }

    public void deleteCart(Product product){

        this.cartRepository.delete(product.getCart());


    }
}
