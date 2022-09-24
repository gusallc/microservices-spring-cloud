package com.gustavo.sprinboot.app.serviceitem.controllers;

import com.gustavo.sprinboot.app.serviceitem.models.Item;
import com.gustavo.sprinboot.app.serviceitem.models.Product;
import com.gustavo.sprinboot.app.serviceitem.models.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand(fallbackMethod = "alternativeMethod")
    @GetMapping("/{id}")
    public Item details(@PathVariable Long id, @RequestParam Integer quantity) {
        return itemService.findById(id, quantity);
    }

    //Here, we can consume another service via feign, restTemplate or other.
    public Item alternativeMethod(Long id, Integer quantity) {
        Item item = new Item();
        Product product = new Product();
        product.setId(id);
        product.setName("Attribute fallback");
        product.setPrice(0.0);
        item.setQuantity(quantity);
        item.setProduct(product);
        return item;
    }

}