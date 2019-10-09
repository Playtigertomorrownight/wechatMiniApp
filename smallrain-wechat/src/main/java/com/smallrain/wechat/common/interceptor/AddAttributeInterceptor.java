package com.smallrain.wechat.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.smallrain.wechat.config.ApplicationInfoProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AddAttributeInterceptor implements HandlerInterceptor {
 
    @Autowired
    private ApplicationInfoProperties applicationInfoProperties;
  
     // 在请求处理之前进行调用（Controller方法调用之前）
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
      return true;
    }
 
    // 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
      log.info("AddAttributeInterceptor posthandle begin: {}",httpServletRequest.getRequestURL());
      //设置必要的参数
      httpServletRequest.setAttribute("domain", applicationInfoProperties.getDomain());
      httpServletRequest.setAttribute("title", applicationInfoProperties.getTitle());
    }
 
    // 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {}
 
}
