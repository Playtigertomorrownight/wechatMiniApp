package com.smallrain.wechat.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallrain.wechat.common.annotations.ModelEditField;

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

  public static JSONObject resolveEntity(Class<?> clazz) {
    JSONObject result = new JSONObject();
    if (null == clazz)
      return result;
    List<Field> fieldsList = new ArrayList<>();
    Field[] fields = clazz.getDeclaredFields();
    fieldsList.addAll(Arrays.asList(fields));
    Class<?> superClazz = clazz.getSuperclass();
    if (superClazz != null) {
      Field[] superFields = superClazz.getDeclaredFields();
      fieldsList.addAll(Arrays.asList(superFields));
    }
    List<String> names = new ArrayList<>();
    for (Field field : fieldsList) {
      // 设置访问对象权限，保证对私有属性的访问
      field.setAccessible(true);
      ModelEditField mef = field.getAnnotation(ModelEditField.class);
      if (null == mef)
        continue; // 没有该注解，跳过
      String name = field.getName();
      names.add(field.getName());
      result.put(name, JSON.toJSON(mef));
    }
    result.put("FIELD_ITEM_LIST", names);
    return result;
  }

  /**
   * 检查独享是否包含必须属性
   * 
   * @param source
   * @param neededProperties
   * @return
   */
  public static String checkBeanNeededField(Object source, @Nullable JSONObject neededProperties) {
    if (null == neededProperties || neededProperties.isEmpty())
      return null;
    Class<?> actualEditable = source.getClass();
    PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
    StringBuilder result = new StringBuilder();
    for (PropertyDescriptor targetPd : targetPds) {
      Method readMethod = targetPd.getReadMethod();
      if (readMethod != null && neededProperties.containsKey((targetPd.getName()))) {
        try {
          Object value = readMethod.invoke(source);
          if (null == value || StringUtils.isEmpty(value.toString())) {
            result.append(neededProperties.get(targetPd.getName())).append("、");
          }
        } catch (Throwable ex) {
          throw new FatalBeanException("Could not read property '" + targetPd.getName() + "' from source to target",
              ex);
        }
      }
    }
    if (result.length() > 0) {
      result.insert(0, "缺少必传字段：");
      return result.substring(0, result.length() - 1);
    }
    return null;
  }
  
  /**
   * 检查独享是否包含必须属性
   * 
   * @param source
   * @param neededProperties
   * @return
   */
  public static boolean checkBeanHasField(Class<?> clazz, String field) {
    if(StringUtils.isEmpty(field)) return false;
    PropertyDescriptor[] targetPds = getPropertyDescriptors(clazz);
    for (PropertyDescriptor targetPd : targetPds) {
      if(targetPd.getName().equals(field)) return true;
    }
    return false;
  }

  /**
   * 生成Uuid,带前缀
   * 
   * @return
   */
  public static String createUuid(String... prefixs) {
    String prefix = null != prefixs ? prefixs[0] : "";
    // 穿建一个Uuid 去掉“-”符号
    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
    // 大于32就截取32
    uuid = uuid.length() > 32 ? uuid.substring(0, 32) : uuid;
    // 加前缀
    if (StringUtils.isBlank(prefix) || prefix.length() > 31)
      return uuid;
    return String.format("%s_%s", prefix, uuid);
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
        InetAddress inet = null; // 根据网卡取本机配置的IP
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

  private static Pattern humpPattern = Pattern.compile("[A-Z]");

  /** 驼峰转下划线,效率比上面高 */
  public static String humpToLine(String str) {
    Matcher matcher = humpPattern.matcher(str);
    StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
    }
    matcher.appendTail(sb);
    return sb.toString();
  }

}
