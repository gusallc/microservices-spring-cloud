package com.gustavo.sprinboot.app.serviceitem.controllers;

import com.gustavo.sprinboot.app.serviceitem.models.Item;
import com.gustavo.sprinboot.app.serviceitem.models.service.ItemService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(@Qualifier("itemFeign") ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> list() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public Item details(@PathVariable Long id, @RequestParam Integer quantity) {
        return itemService.findById(id, quantity);
    }

}