package com.springboot.Teamproject.ProductTest;

import com.springboot.Teamproject.Service.ProductService;
import com.springboot.Teamproject.entity.Product;
import com.springboot.Teamproject.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductTest {
    private final ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public ProductTest(ProductService productService) {
        this.productService = productService;

    }

    @Test
    @Rollback(value = true)
    @Transactional
    @DisplayName(value = "productListTest")
    public void  testList() {
        Product product = new Product();
        product.setPno(1);
        product.setName("test");
        product.setPrice(1234);
        product.setCode("test123");
        product.setImageFileName("testimage");

        productService.getProduct(product.getPno());

        System.out.println("getProduct :" +  product);

    }
}
