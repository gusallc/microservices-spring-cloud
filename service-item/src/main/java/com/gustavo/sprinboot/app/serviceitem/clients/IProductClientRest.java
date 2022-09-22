package com.gustavo.sprinboot.app.serviceitem.clients;

import com.gustavo.sprinboot.app.serviceitem.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "service-products", url = "localhost:8001/v1/products")
public interface IProductClientRest {

    @GetMapping
    List<Product> list();

    @GetMapping("/{id}")
    Product details(@PathVariable Long id);
}
