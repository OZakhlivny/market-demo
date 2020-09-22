package com.geekbrains.market.demo.services;

import com.geekbrains.market.demo.entities.Product;
import com.geekbrains.market.demo.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public Page<Product> findAll(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public List<Product> findAll(){return productRepository.findAll();}

    public Page<Product> findAllByPriceGreaterThanEqual(int price, int page, int size) {
        return productRepository.findAllByPriceGreaterThanEqual(price, PageRequest.of(page, size));
    }
    public Page<Product> findAllByPriceLessThanEqual(int price, int page, int size) { return productRepository.findAllByPriceLessThanEqual(price, PageRequest.of(page, size));}
    public Page<Product> findAllByPriceGreaterThanEqualAndPriceLessThanEqual(int min, int max, int page, int size) { return productRepository.findAllByPriceGreaterThanEqualAndPriceLessThanEqual(min, max, PageRequest.of(page, size));}
}