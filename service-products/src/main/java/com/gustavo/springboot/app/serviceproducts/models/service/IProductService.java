package com.gustavo.springboot.app.serviceproducts.models.service;

import com.gustavo.springboot.app.serviceproducts.models.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> findAll();

    Product findById(Long id);
}
