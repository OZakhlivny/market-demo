package com.geekbrains.market.demo.controllers;

import com.geekbrains.market.demo.entities.Product;
import com.geekbrains.market.demo.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private int min, max;
    private int currentPage;
    public static final int ITEMS_ON_PAGE = 5;


    public ProductController(ProductService productService) {
        this.productService = productService;
        currentPage = min = max = 0;
    }

    @GetMapping
    public String allProducts(Model model) {
        model.addAttribute("products", productService.findAll(currentPage, ProductController.ITEMS_ON_PAGE));
        return "products";
    }

    @GetMapping("/filter_products")
    public String filterProduct(@RequestParam Integer input_min, @RequestParam Integer input_max, Model model) {
        min = input_min != null ? input_min : 0;
        max = input_max != null ? input_max : 0;
        currentPage = 0;
        return doFilter(model);
    }

    public String doFilter(Model model){
        Page<Product> productList = productService.findAll(currentPage, ProductController.ITEMS_ON_PAGE);

        if (min > 0) {
            if (max > 0)
                productList = productService.findAllByPriceGreaterThanEqualAndPriceLessThanEqual(min, max, currentPage, ProductController.ITEMS_ON_PAGE);
            else
                productList = productService.findAllByPriceGreaterThanEqual(min, currentPage, ProductController.ITEMS_ON_PAGE);
        } else {
            if (max > 0)
                productList = productService.findAllByPriceLessThanEqual(max, currentPage, ProductController.ITEMS_ON_PAGE);
        }

        model.addAttribute("products", productList);
        return "products";
    }

   @GetMapping("/goto_page")
    public String goToPage(@RequestParam Integer page, Model model) {
        if(page != null) currentPage = page > 1 ? page - 1 : 0;
       return doFilter(model);
    }
}