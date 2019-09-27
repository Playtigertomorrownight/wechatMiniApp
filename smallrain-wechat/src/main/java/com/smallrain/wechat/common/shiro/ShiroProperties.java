package com.smallrain.wechat.common.shiro;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:auth/shiro.properties"})
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {
  private long sessionTimeout;
  private int cookieTimeout;
  private String anonUrl;
  private String loginUrl;
  private String successUrl;
  private String logoutUrl;
  private String unauthorizedUrl;
}
