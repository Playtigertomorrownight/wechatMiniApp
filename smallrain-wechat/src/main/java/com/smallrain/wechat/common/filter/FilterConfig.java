package com.smallrain.wechat.common.filter;

import javax.servlet.DispatcherType;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
public class FilterConfig {
	
	@Bean(name="commonFilter")
	public CommonFilter commonFilter(){
	return new CommonFilter();
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
    public FilterRegistrationBean registerAuthFilter() {
    	FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("commonFilter"));
        registration.addUrlPatterns("/login");
        registration.addUrlPatterns("/index");
        registration.addInitParameter("targetFilterLifecycle","true");
       // registration.addInitParameter("exclusions", "*/static/*");
        //registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setEnabled(false);
        return registration;
    }
    
    //如果有多个Filter，再写一个public FilterRegistrationBean registerOtherFilter(){...}即可。
}