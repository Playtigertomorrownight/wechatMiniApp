package com.smallrain.wechat.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:application-info.yml"},factory=CustomPropertySourceFactory.class)
@ConfigurationProperties(prefix = "app")
public class ApplicationInfoProperties {

  private boolean ssl;
  private String serverName;
  private String title;
  
  /**
   * 获取根域名
   * @return
   */
  public String getDomain() {
    if(ssl)
      return String.format("https://%s", serverName);
    return String.format("http://%s", serverName);
  }
  
}
