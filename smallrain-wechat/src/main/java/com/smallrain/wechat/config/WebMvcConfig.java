package com.smallrain.wechat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.smallrain.wechat.common.interceptor.AddAttributeInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
   
   @Autowired
   private AddAttributeInterceptor addAttributeInterceptor;
  
    //跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowCredentials(true)
            .allowedMethods("GET", "POST", "DELETE", "PUT")
            .maxAge(3600);
    }
    
    //静态资源访问目录配置
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
      //静态资源文件
      registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
      registry.addResourceHandler("/image/**").addResourceLocations("classpath:/static/image/");
      registry.addResourceHandler("/javascript/**").addResourceLocations("classpath:/static/javascript/");
      registry.addResourceHandler("/layer/**").addResourceLocations("classpath:/static/layer/");
      registry.addResourceHandler("/laydate/**").addResourceLocations("classpath:/static/laydate/");
      //swagger文档
      registry.addResourceHandler("swagger-ui.html")
      .addResourceLocations("classpath:/META-INF/resources/");
      registry.addResourceHandler("/webjars/**")
      .addResourceLocations("classpath:/META-INF/resources/webjars/");
      
      super.addResourceHandlers(registry);
    }
 
    //拦截器配置,对需要加公共信息的请求进行拦截
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //这里可以添加多个拦截器
        registry.addInterceptor(addAttributeInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/druid/**","/layui/**","/login/web","/login/rest","/error/**","/swagger*/**","/webjars/**","/v2/**","/css/**","/image/**","/javascript/**","/api/**");
        super.addInterceptors(registry);
    }
}