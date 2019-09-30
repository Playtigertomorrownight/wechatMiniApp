package com.smallrain.wechat.common;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:auth/base-config.properties"})
@ConfigurationProperties(prefix = "base.config")
public class BaseProperties {
  
  private int tokenExpire;
  
}
