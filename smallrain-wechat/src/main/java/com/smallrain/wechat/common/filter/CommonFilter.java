package com.smallrain.wechat.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonFilter implements Filter {
	
    @Override
    public void destroy() {
    	log.info("common filter destory");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	HttpServletRequest httpServletRequest=(HttpServletRequest) request;
        log.info("common filter begin: {}",httpServletRequest.getRequestURL());
        request.setAttribute("domain", "http://localhost");
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    	log.info("common filter init");
    }

}
