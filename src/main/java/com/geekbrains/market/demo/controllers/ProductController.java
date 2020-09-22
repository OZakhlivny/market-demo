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
    private int currentPage;
    public static final int ITEMS_ON_PAGE = 5;

    public ProductController(ProductService productService) {
        this.productService = productService;
        currentPage = 0;
    }

    @GetMapping
    public String allProducts(Model model) {
        model.addAttribute("products", productService.findAll(currentPage, ProductController.ITEMS_ON_PAGE));
        return "products";
    }

    @GetMapping("/filter_products")
    public String filterProduct(@RequestParam Integer min, @RequestParam Integer max, Model model) {
        Page<Product> productList = productService.findAll(currentPage, ProductController.ITEMS_ON_PAGE);
        currentPage = 0;
        if ((min != null) && (max != null)) {
            if (min == 0) {
                if (max > 0)
                    productList = productService.findAllByPriceLessThanEqual(max, currentPage, ProductController.ITEMS_ON_PAGE);
            } else
                productList = productService.findAllByPriceGreaterThanEqualAndPriceLessThanEqual(min, max, currentPage, ProductController.ITEMS_ON_PAGE);
            if ((max == 0) && (min > 0))
                productList = productService.findAllByPriceGreaterThanEqual(min, currentPage, ProductController.ITEMS_ON_PAGE);
        } else {
            if ((min == null) && (max != null)) if (max > 0)
                productList = productService.findAllByPriceLessThanEqual(max, currentPage, ProductController.ITEMS_ON_PAGE);
            if ((max == null) && (min != null)) if (min > 0)
                productList = productService.findAllByPriceGreaterThanEqual(min, currentPage, ProductController.ITEMS_ON_PAGE);
        }

        model.addAttribute("products", productList);
        return "products";
    }

   @GetMapping("/goto_page")
    public String goToPage(@RequestParam Integer page) {
        if(page != null) currentPage = page > 1 ? page - 1 : 0;
        return "redirect:/products";
    }
}