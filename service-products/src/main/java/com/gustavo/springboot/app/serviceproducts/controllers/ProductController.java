package com.gustavo.springboot.app.serviceproducts.controllers;

import com.gustavo.springboot.app.serviceproducts.models.entity.Product;
import com.gustavo.springboot.app.serviceproducts.models.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    private final Environment environment; // spring framework

    @GetMapping
    public List<Product> list() {
        return productService.findAll().stream().map(product -> {
            product.setPort(Integer.parseInt(Objects.requireNonNull(environment.getProperty("local.server.port"))));
            return product;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Product details(@PathVariable Long id) {
        Product product = productService.findById(id);
        product.setPort(Integer.parseInt(Objects.requireNonNull(environment.getProperty("local.server.port"))));
        return product;
    }

}
