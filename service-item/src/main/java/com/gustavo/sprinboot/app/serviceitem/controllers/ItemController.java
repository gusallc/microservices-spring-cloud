package com.gustavo.sprinboot.app.serviceitem.controllers;

import com.gustavo.sprinboot.app.serviceitem.models.Item;
import com.gustavo.sprinboot.app.serviceitem.models.Product;
import com.gustavo.sprinboot.app.serviceitem.models.service.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/items")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;

    public ItemController(@Qualifier("itemFeign") ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> list(@RequestParam(name = "lastname", required = false) String lastname, @RequestHeader(name = "X-Request-red", required = false) String requestHeader) {
        System.out.println("lastname = " + lastname);
        System.out.println("requestHeader = " + requestHeader);
        return itemService.findAll();
    }

    // This annotation only takes properties from the .yml or .properties configuration file.
    @CircuitBreaker(name = "items", fallbackMethod = "alternativeMethod")
    @GetMapping("/{id}")
    public Item details(@PathVariable Long id, @RequestParam Integer quantity) {
        return itemService.findById(id, quantity);
    }

    @TimeLimiter(name = "items", fallbackMethod = "alternativeMethodWithTimeLimiter")
    @GetMapping("/timeLimiter/{id}")
    public CompletableFuture<Item> detailsWithTimeLimiter(@PathVariable Long id, @RequestParam Integer quantity) {
        return CompletableFuture.supplyAsync(() -> itemService.findById(id, quantity));
    }

    //the "fallbackMethod" of the circuit is the one that predominates when these 2 annotations are combined
    // "alternativeMethodWithTimeLimiter" is used since the return type is "CompletableFuture"
    @CircuitBreaker(name = "items", fallbackMethod = "alternativeMethodWithTimeLimiter")
    @TimeLimiter(name = "items")
    @GetMapping("/limiterAndCBreaker/{id}")
    public CompletableFuture<Item> detailsWithTimeLimiterAndBreaker(@PathVariable Long id, @RequestParam Integer quantity) {
        return CompletableFuture.supplyAsync(() -> itemService.findById(id, quantity));
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

    public CompletableFuture<Item> alternativeMethodWithTimeLimiter(Long id, Integer quantity, Throwable throwable) {
        logger.info(throwable.getMessage());
        Item item = new Item();
        Product product = new Product();
        product.setId(id);
        product.setName("Attribute fallback");
        product.setPrice(0.0);
        item.setQuantity(quantity);
        item.setProduct(product);
        return CompletableFuture.supplyAsync(() -> item);
    }

}