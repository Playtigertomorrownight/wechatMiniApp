package com.smallrain.wechat.common.shiro;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Base64Utils;

import com.smallrain.wechat.common.filter.LogoutFilter;
import com.smallrain.wechat.common.filter.RestfulFilter;
import com.smallrain.wechat.utils.AuthUtil;

import lombok.extern.slf4j.Slf4j; 

@Slf4j
@Configuration
public class ShiroConfig {

  @Autowired
  private ShiroProperties shiroProperties;
  @Autowired
  private SmallrainRealm smallrainRealm;
  
  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
      ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
      shiroFilterFactoryBean.setSecurityManager(securityManager);  // 设置 securityManager
      shiroFilterFactoryBean.setLoginUrl(shiroProperties.getLoginUrl()); // 登录的 url
      //shiroFilterFactoryBean.setSuccessUrl(shiroProperties.getSuccessUrl());  // 登录成功后跳转的 url
      shiroFilterFactoryBean.setUnauthorizedUrl(shiroProperties.getUnauthorizedUrl());  // 未授权 url
      LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();  
      // 设置免认证 url
      String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(shiroProperties.getAnonUrl(), ",");
      for (String url : anonUrls) {
          filterChainDefinitionMap.put(url, "anon");
      }
      // 配置退出过滤器，其中具体的退出代码 Shiro已经替我们实现了
      filterChainDefinitionMap.put(shiroProperties.getLogoutUrl(), "logout");
      // 除上以外所有 url都必须认证通过才可以访问，未通过认证自动访问 LoginUrl
      filterChainDefinitionMap.put("/**", "authc");
      shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
      //配置自定义退出过滤器 
      LogoutFilter logoutFilter = new LogoutFilter();
	    logoutFilter.setRedirectUrl("/login");
	    shiroFilterFactoryBean.getFilters().put("logout", logoutFilter);
	    //配置restFul 接口过滤器
	    RestfulFilter restfulFilter = new RestfulFilter();
	    shiroFilterFactoryBean.getFilters().put("authc", restfulFilter);
	    
      log.info("Shiro拦截器工厂类注入成功");
      return shiroFilterFactoryBean;
  }

  @Bean
  public SecurityManager securityManager() {
      DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
      // 配置 SecurityManager，并注入 shiroRealm
      securityManager.setRealm(smallrainRealm());
      // 配置 shiro session管理器
      securityManager.setSessionManager(sessionManager());
      // 配置 rememberMeCookie
      securityManager.setRememberMeManager(rememberMeManager());
      return securityManager;
  }
  
  public SmallrainRealm smallrainRealm() {
      smallrainRealm.setCredentialsMatcher(hashedCredentialsMatcher()); // 原来在这里
      return smallrainRealm;
  }
  
  @Bean
  public HashedCredentialsMatcher hashedCredentialsMatcher() {
      HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
      hashedCredentialsMatcher.setHashAlgorithmName(AuthUtil.ALGORITHM_NAME); // 散列算法
      hashedCredentialsMatcher.setHashIterations(AuthUtil.HASH_ITERATIONS); // 散列次数
      return hashedCredentialsMatcher;
  }

  /**
   * rememberMe cookie 效果是重开浏览器后无需重新登录
   *
   * @return SimpleCookie
   */
  private SimpleCookie rememberMeCookie() {
      // 设置 cookie 名称，对应 login.html 页面的 <input type="checkbox" name="rememberMe"/>
      SimpleCookie cookie = new SimpleCookie("rememberMe");
      // 设置 cookie 的过期时间，单位为秒，这里为一天
      cookie.setMaxAge(shiroProperties.getCookieTimeout());
      return cookie;
  }

  /**
   * cookie管理对象
   *
   * @return CookieRememberMeManager
   */
  private CookieRememberMeManager rememberMeManager() {
      CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
      cookieRememberMeManager.setCookie(rememberMeCookie());
      // rememberMe cookie 加密的密钥
      String encryptKey = "febs_shiro_key";
      byte[] encryptKeyBytes = encryptKey.getBytes(StandardCharsets.UTF_8);
      String rememberKey = Base64Utils.encodeToString(Arrays.copyOf(encryptKeyBytes, 16));
      cookieRememberMeManager.setCipherKey(Base64.decode(rememberKey));
      return cookieRememberMeManager;
  }

  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
      AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
      authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
      return authorizationAttributeSourceAdvisor;
  }

  /**
   * session 管理对象
   *
   * @return DefaultWebSessionManager
   */
  @Bean
  public DefaultWebSessionManager sessionManager() {
      DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
      Collection<SessionListener> listeners = new ArrayList<>();
      listeners.add(new ShiroSessionListener());
      // 设置 session超时时间
      sessionManager.setGlobalSessionTimeout(shiroProperties.getSessionTimeout() * 1000L);
      sessionManager.setSessionListeners(listeners);
     // sessionManager.setSessionDAO(redisSessionDAO());
      sessionManager.setSessionIdUrlRewritingEnabled(false);
      return sessionManager;
  }
  
}
