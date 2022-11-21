package com.springboot.Teamproject.Service;

import com.springboot.Teamproject.entity.Cart;
import com.springboot.Teamproject.entity.Product;
import com.springboot.Teamproject.entity.Purchase;
import com.springboot.Teamproject.entity.User;
import com.springboot.Teamproject.repository.CartRepository;
import com.springboot.Teamproject.repository.ProductRepository;
import com.springboot.Teamproject.repository.PurchaseRepository;
import com.springboot.Teamproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    /* 구매 */
    public void saveBuy(int cartNumber){

        Cart cart = this.cartRepository.findById(cartNumber).get();

        Product product = this.productRepository.findById(cart.getProduct().getPno()).get();

        User user = this.userRepository.findById(cart.getUserprofile().getId()).get();

        Purchase purchase = new Purchase();
        purchase.setProduct(product);
        purchase.setUserprofile(user);
        purchase.setProductCount(cart.getProductCount());
        purchase.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss")));

        this.purchaseRepository.save(purchase);
    }
    /* 구매 리스트 */
    public List<Purchase> getList(String user_id){

        User user = this.userRepository.findById(user_id).get();

        return this.purchaseRepository.findAllByUserprofile(user);
    }

    /*구매 버튼 동작시 장바구니 삭제*/
//    public void deletePurchaseCart(Cart cart) {
//        //cartRepository.deleteById(user_id);
//        cartRepository.delete(cart);
}

