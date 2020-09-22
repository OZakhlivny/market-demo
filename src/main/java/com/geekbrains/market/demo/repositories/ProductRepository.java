package com.geekbrains.market.demo.repositories;

import com.geekbrains.market.demo.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByPriceGreaterThanEqual(int price, Pageable pageable);
    Page<Product> findAllByPriceLessThanEqual(int price, Pageable pageable);
    Page<Product> findAllByPriceGreaterThanEqualAndPriceLessThanEqual(int min, int max, Pageable pageable);

}
