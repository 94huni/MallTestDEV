package com.springboot.Teamproject.Service;

import com.springboot.Teamproject.entity.Product;
import com.springboot.Teamproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> getList(Pageable pageable){


        return this.productRepository.findAll(pageable);
    }

    public Product getProduct(int pno){

        return this.productRepository.getById(pno);
    }

    public Page<Product> searchProduct(Pageable pageable, String searchKeyword){
        return productRepository.findByNameContaining(searchKeyword, pageable);
    }

    public void findAll(Pageable pageable){
        Product product = new Product();
        productRepository.findByPnoOrderByPnoDesc(product, pageable);
    }

    public List<Product> category(String code){
        return productRepository.findAllByCode(code);
    }



}
