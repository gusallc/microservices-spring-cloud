package com.gustavo.sprinboot.app.serviceitem.models.service.impl;

import com.gustavo.sprinboot.app.serviceitem.models.Item;
import com.gustavo.sprinboot.app.serviceitem.models.Product;
import com.gustavo.sprinboot.app.serviceitem.models.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final String URL_HOST_PRODUCT = "http://localhost:8001/v1/products";

    @Qualifier(value = "restClient")
    private final RestTemplate restTemplate;

    @Override
    public List<Item> findAll() {
        List<Product> products = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(URL_HOST_PRODUCT, Product[].class)));
        return products.stream().map(product -> new Item(product, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer quantity) {
        Map<String, String> pathVariable = new HashMap<>();
        pathVariable.put("id", id.toString());
        Product product = Objects.requireNonNull(restTemplate.getForObject(URL_HOST_PRODUCT + "/{id}", Product.class, pathVariable));
        return new Item(product, quantity);
    }
}
