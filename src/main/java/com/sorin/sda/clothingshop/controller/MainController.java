package com.sorin.sda.clothingshop.controller;

import com.sorin.sda.clothingshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final ProductService productService;

    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String root(Model model) {
        model.addAttribute("products", productService.getAll());
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
