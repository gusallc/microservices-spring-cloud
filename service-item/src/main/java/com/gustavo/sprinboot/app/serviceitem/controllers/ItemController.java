package com.gustavo.sprinboot.app.serviceitem.controllers;

import com.gustavo.sprinboot.app.serviceitem.models.Item;
import com.gustavo.sprinboot.app.serviceitem.models.Product;
import com.gustavo.sprinboot.app.serviceitem.models.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/items")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;

    private final CircuitBreakerFactory cbFactory;

    public ItemController(@Qualifier("itemFeign") ItemService itemService, CircuitBreakerFactory cbFactory) {
        this.itemService = itemService;
        this.cbFactory = cbFactory;
    }

    @GetMapping
    public List<Item> list(@RequestParam(name = "lastname", required = false) String lastname, @RequestHeader(name = "X-Request-red", required = false) String requestHeader) {
        System.out.println("lastname = " + lastname);
        System.out.println("requestHeader = " + requestHeader);
        return itemService.findAll();
    }

    //@HystrixCommand(fallbackMethod = "alternativeMethod")
    @GetMapping("/{id}")
    public Item details(@PathVariable Long id, @RequestParam Integer quantity) {
        return cbFactory.create("items")
                .run(() -> itemService.findById(id, quantity), throwable -> alternativeMethod(id, quantity, throwable));
    }

    //Here, we can consume another service via feign, restTemplate or other.
    public Item alternativeMethod(Long id, Integer quantity, Throwable throwable) {
        logger.info(throwable.getMessage());
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