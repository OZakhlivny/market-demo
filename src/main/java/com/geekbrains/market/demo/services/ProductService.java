package com.geekbrains.market.demo.services;

import com.geekbrains.market.demo.entities.Product;
import com.geekbrains.market.demo.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public Page<Product> findAll(Specification<Product> spec, int page, int size) {
        return productRepository.findAll(spec, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void updateProduct(Product updatedProduct) {
        productRepository.save(updatedProduct);
    }
}