package com.geekbrains.market.demo.controllers;

import com.geekbrains.market.demo.entities.Product;
import com.geekbrains.market.demo.services.ProductService;
import com.geekbrains.market.demo.utils.ProductFilter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    public static final int ITEMS_ON_PAGE = 5;

    @GetMapping
    public String allProducts(Model model, @RequestParam(defaultValue = "1", name = "p") Integer page, @RequestParam Map<String, String> params) {
        page = page < 1 ? 1 : page;
        ProductFilter productFilter = new ProductFilter(params);
        model.addAttribute("products", productService.findAll(productFilter.getSpec(), page - 1, ProductController.ITEMS_ON_PAGE));
        model.addAttribute("filterDefinition", productFilter.getFilterDefinition());
        return "products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, @RequestParam(defaultValue = "1", name = "p") Integer page, @RequestParam Map<String, String> params) {
        model.addAttribute("product", productService.findById(id));
        ProductFilter productFilter = new ProductFilter(params);
        model.addAttribute("p", page);
        model.addAttribute("filterDefinition", productFilter.getFilterDefinition());
        return "edit_product";
    }

    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute Product updatedProduct, Model model, @RequestParam(defaultValue = "1", name = "p") Integer page, @RequestParam Map<String, String> params) {
        productService.updateProduct(updatedProduct);
        return allProducts(model, page, params);
    }
}