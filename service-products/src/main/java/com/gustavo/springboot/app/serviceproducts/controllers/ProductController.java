package com.gustavo.springboot.app.serviceproducts.controllers;

import com.gustavo.springboot.app.serviceproducts.models.entity.Product;
import com.gustavo.springboot.app.serviceproducts.models.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @GetMapping
    public List<Product> list() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product details(@PathVariable Long id) {
        return productService.findById(id);
    }

}
