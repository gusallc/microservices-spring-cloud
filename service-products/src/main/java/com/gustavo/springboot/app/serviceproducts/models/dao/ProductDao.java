package com.gustavo.springboot.app.serviceproducts.models.dao;

import com.gustavo.springboot.app.serviceproducts.models.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductDao extends CrudRepository<Product, Long> {

}
