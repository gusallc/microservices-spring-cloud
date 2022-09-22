package com.gustavo.sprinboot.app.serviceitem.models.service.impl;

import com.gustavo.sprinboot.app.serviceitem.clients.IProductClientRest;
import com.gustavo.sprinboot.app.serviceitem.models.Item;
import com.gustavo.sprinboot.app.serviceitem.models.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("itemFeign")
@RequiredArgsConstructor
public class ItemServiceFeignImpl implements ItemService {

    private final IProductClientRest productClientRest;

    @Override
    public List<Item> findAll() {
        System.out.println("findAll - Feign");
        return productClientRest.list().stream()
                .map(product -> new Item(product, 1))
                .collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer quantity) {
        System.out.println("findById - Feign");
        return new Item(productClientRest.details(id), quantity);
    }
}
