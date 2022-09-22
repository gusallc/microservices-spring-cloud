package com.gustavo.sprinboot.app.serviceitem.clients;

import com.gustavo.sprinboot.app.serviceitem.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "service-products")
public interface IProductClientRest {

    @GetMapping("/v1/products")
    List<Product> list();

    @GetMapping("/v1/products/{id}")
    Product details(@PathVariable Long id);
}
