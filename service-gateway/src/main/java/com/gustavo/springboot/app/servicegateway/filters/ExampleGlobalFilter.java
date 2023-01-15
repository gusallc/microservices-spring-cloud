package com.gustavo.springboot.app.servicegateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class ExampleGlobalFilter implements GlobalFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(ExampleGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // ------------- PRE
        logger.info("Running pre-filter");
        //add token in request input, modifying the header (first make the request mutate)
        exchange.getRequest().mutate().headers(h -> h.add("token", "123456"));
        // ------------ POST
        return chain.filter(exchange)
                //Create reactive object with "MONO"
                .then(Mono.fromRunnable(() -> {
                    logger.info("Running post-filter");
                    //Intercept and get header
//                    Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token"))
//                            //set token value in the request response
//                            .ifPresent(value -> exchange.getResponse().getHeaders().add("token", value)); // RESPONSE (writing header)

//                    exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build());
//                    exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
                }));
    }

    //For many global filters, you can also set an order of execution for it
    //Even on filter may eventually depend on another.
    //The "PRE" logic is executed FIRST, and its POST last
    @Override
    public int getOrder() {
        //presence > 0, if it is negative, it would give an error on line 35, because having a high priority command,
        // the RESPONSE is read-only, and on line 35 it would give a write error.
        return 1;
    }
}
