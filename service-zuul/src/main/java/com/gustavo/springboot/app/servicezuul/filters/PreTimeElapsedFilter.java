package com.gustavo.springboot.app.servicezuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PreTimeElapsedFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(PostTimeElapsedFilter.class);

    @Override
    public String filterType() {
        return "pre"; //key word -> always has to be called that
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
        log.info("{} PRE - routed request to {}", request.getMethod(), request.getRequestURL());

        Long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return null;
    }
}
