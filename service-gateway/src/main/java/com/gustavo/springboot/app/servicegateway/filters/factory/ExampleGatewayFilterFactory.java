package com.gustavo.springboot.app.servicegateway.filters.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ExampleGatewayFilterFactory extends AbstractGatewayFilterFactory<ExampleGatewayFilterFactory.Configuration> {

    private final Logger logger = LoggerFactory.getLogger(ExampleGatewayFilterFactory.class);

    public ExampleGatewayFilterFactory() {
        super(Configuration.class);
    }

    // With this, we can customize the filters; where, either in one microservice or in all microservices
    @Override
    public GatewayFilter apply(Configuration config) {
        return (exchange, chain) -> {
            logger.info("Running pre-gateway FILTER FACTORY: {}", config.message);
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        Optional.ofNullable(config.cookieValue).ifPresent(cookie ->
                                exchange.getResponse().addCookie(ResponseCookie.from(config.cookieName, config.cookieValue).build())
                        );
                        logger.info("Running post-gateway FILTER FACTORY: {}", config.message);
                    }));
        };
    }

    //Override name method of gateway.filter.factory.GatewayFilterFactory
    //Change prefix name
    @Override
    public String name() {
        return "ExampleCookie";
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("message", "cookieName", "cookieValue");
    }

    public static class Configuration {
        private String message;
        private String cookieValue;
        private String cookieName;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCookieValue() {
            return cookieValue;
        }

        public void setCookieValue(String cookieValue) {
            this.cookieValue = cookieValue;
        }

        public String getCookieName() {
            return cookieName;
        }

        public void setCookieName(String cookieName) {
            this.cookieName = cookieName;
        }
    }
}
