package com.gustavo.sprinboot.app.serviceitem.models.service;

import com.gustavo.sprinboot.app.serviceitem.models.Item;

import java.util.List;

public interface ItemService {

    List<Item> findAll();
    Item findById(Long id, Integer quantity);
}
