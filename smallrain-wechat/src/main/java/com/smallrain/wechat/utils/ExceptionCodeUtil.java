package com.smallrain.wechat.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionCodeUtil {
  
  /**
   * 百家号文章状态信息
   */
  private static final Properties EXCEPTION_CODES = new Properties();

  // 加载基本配置
  static {
    // 加载基础配置默认值
    ClassPathResource exceptionCode = new ClassPathResource("smallrain/exception-code.properties");
    try (InputStream rc = exceptionCode.getInputStream()) {
      EXCEPTION_CODES.load(new InputStreamReader(rc, "UTF-8"));
    } catch (Exception e) {
      log.info("加载默认值配置失败：{}", e.getMessage());
    }
  }
  
  public static String getExceptionMessage(Object key) {
    if (null == key)
      return "";
    Object msg = EXCEPTION_CODES.get(key);
    return null == msg ? "" : msg.toString();
  }
  
  /**
   * 获取异常的返回信息
   * 
   * @return
   */
  public static String getFailMessage(String prefix, Integer code, String... extend) {
    String message = getExceptionMessage(code);
    message = StringUtils.isBlank(message) ? "异常（exception）": message;
    if (null != extend && extend.length > 0) {
      StringBuilder ms = new StringBuilder(message);
      ms.append(": ");
      for(int i=1;i<extend.length;i++) {
        ms.append(extend[i]).append(",");
      }
      message=ms.substring(0,ms.length()-1);
    }
    return String.format("%s，%s，错误码（%s）", prefix, message, code);
  }

}
