package com.smallrain.wechat.models.miniapp.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 微信小程序先相关配置
 * 
 * @author wangying.dz3
 *
 */
@Data
@ConfigurationProperties(prefix = "wx.miniapp")
public class WechatMiniAppProperties {

  private List<Config> configs;

  @Data
  public static class Config {
    /**
     * 设置微信小程序的appid
     */
    private String appid;

    /**
     * 设置微信小程序的Secret
     */
    private String secret;

    /**
     * 设置微信小程序消息服务器配置的token
     */
    private String token;

    /**
     * 设置微信小程序消息服务器配置的EncodingAESKey
     */
    private String aesKey;

    /**
     * 消息格式，XML或者JSON
     */
    private String msgDataFormat;
  }

}
