package com.gustavo.springboot.app.serviceproducts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient //-> Explicitly enable eureka client
@SpringBootApplication
public class ServiceProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProductsApplication.class, args);
    }

}
