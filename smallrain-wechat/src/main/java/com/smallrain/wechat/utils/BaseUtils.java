package com.smallrain.wechat.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

public class BaseUtils extends BeanUtils {

  /**
   * 重写对象复制，忽略值为空的属性
   * 
   * @param source
   * @param target
   * @param ignoreProperties
   * @throws BeansException
   */
  public static void copyNotNullProperties(Object source, Object target, @Nullable String... ignoreProperties)
      throws BeansException {
    Class<?> actualEditable = target.getClass();
    PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
    List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
    for (PropertyDescriptor targetPd : targetPds) {
      Method writeMethod = targetPd.getWriteMethod();
      if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
        PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
        if (sourcePd != null) {
          Method readMethod = sourcePd.getReadMethod();
          if (readMethod != null
              && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
            try {
              Object value = readMethod.invoke(source);
              // 单且仅单属性值不为空的时候才进行复制
              if (null != value && value.toString().trim().length() != 0) {
                writeMethod.invoke(target, value);
              }
            } catch (Throwable ex) {
              throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target",
                  ex);
            }
          }
        }
      }
    }
  }

  /**
   * 生成Uuid,带前缀
   * 
   * @return
   */
  public static String createUuid(String... prefixs) {
    String prefix = null!=prefixs?prefixs[0]:"";
    // 穿建一个Uuid 去掉“-”符号
    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
    //大于32就截取32
    uuid = uuid.length()>32?uuid.substring(0,32):uuid;
    //加前缀
    if(StringUtils.isBlank(prefix) || prefix.length() > 10) return uuid;
    return String.format("%s_%s", prefix,uuid.substring(prefix.length()+1));
  }

  /**
   * 生成 随机字符串
   * 
   * @param length
   * @return
   */
  static final String RANDOM_STR_SOURCE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  public static String getRandomString(int length, String prefix, String end) {
    Random random = new Random();
    StringBuilder sb = new StringBuilder(prefix);
    for (int i = 0; i < length; i++) {
      int number = random.nextInt(RANDOM_STR_SOURCE.length() - 1);
      sb.append(RANDOM_STR_SOURCE.charAt(number));
    }
    sb.append(end);
    return sb.toString();
  }

  /**
   * 获取当前网络ip
   * 
   * @param request
   * @return
   */
  public String getIpAddr(HttpServletRequest request) {
    String ipAddress = request.getHeader("x-forwarded-for");
    if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getHeader("Proxy-Client-IP");
    }
    if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getHeader("WL-Proxy-Client-IP");
    }
    if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getRemoteAddr();
      if (StringUtils.isBlank(ipAddress) || ipAddress.equals("0:0:0:0:0:0:0:1")) {
        InetAddress inet = null;    // 根据网卡取本机配置的IP
        try {
          inet = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
          e.printStackTrace();
        }
        ipAddress = inet.getHostAddress();
      }
    }
    // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
    if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length() = 15
      if (ipAddress.indexOf(",") > 0) {
        ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
      }
    }
    return ipAddress;
  }

}
