package com.gustavo.springboot.app.servicezuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PostTimeElapsedFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(PostTimeElapsedFilter.class);

    @Override
    public String filterType() {
        return "post"; //key word -> always has to be called that
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    //shouldFilter -> validation for example
    //we can validate if there is any parameter in the path,
    // if there is a request param or if the user is authenticated
    @Override
    public boolean shouldFilter() {
        //validate anything
        return true;
    }

    //Logic after filter (shouldFilter)
    @Override
    public Object run() throws ZuulException {

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        log.info("{} POST - routed request to {}", request.getMethod(), request.getRequestURL());

        Long startTime = (Long) request.getAttribute("startTime");
        Long endTime = System.currentTimeMillis();
        Long timeElapsed = endTime - startTime;

        log.info("Time Elapse in seconds: {}", timeElapsed.doubleValue() / 1000.00);
        log.info("Time Elapse in milliseconds: {}", timeElapsed);

        return null;
    }
}
