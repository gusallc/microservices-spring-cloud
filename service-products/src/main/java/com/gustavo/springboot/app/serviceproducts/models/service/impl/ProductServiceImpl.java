package com.gustavo.springboot.app.serviceproducts.models.service.impl;

import com.gustavo.springboot.app.serviceproducts.models.dao.ProductDao;
import com.gustavo.springboot.app.serviceproducts.models.entity.Product;
import com.gustavo.springboot.app.serviceproducts.models.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductDao productDao;

    @Override
    @Transactional(readOnly = true) // SpringFramework
    public List<Product> findAll() {
        return (List<Product>) productDao.findAll();
    }

    @Override
    @Transactional(readOnly = true) // SpringFramework
    public Product findById(Long id) {
        return productDao.findById(id).orElse(null);
    }
}
