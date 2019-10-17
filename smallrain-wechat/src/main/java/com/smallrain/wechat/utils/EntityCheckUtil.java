package com.smallrain.wechat.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

import com.alibaba.fastjson.JSONObject;
import com.smallrain.wechat.common.exception.SmallrainException;
import com.smallrain.wechat.models.menu.entity.Menu;
import com.smallrain.wechat.models.role.entity.Role;
import com.smallrain.wechat.models.user.entity.User;

import lombok.extern.slf4j.Slf4j;

/**
 * 实体类检查工具类
 * @author wangying.dz3
 *
 */
@Slf4j
public class EntityCheckUtil {

  // 初始化实体类检查配置内存
  private static JSONObject checkConfig = new JSONObject();
  
//静态代码块，启动时加载本地配置文件
 static {
   // 获取配置文件输入流
   ClassPathResource resource = new ClassPathResource("check/beans-field-check.yml");
   try (InputStream inputStream = resource.getInputStream()){
     Yaml yaml = new Yaml();
     Object ticketsCheckConfig = yaml.load(inputStream);
     checkConfig = JSONObject.parseObject(JSONObject.toJSONString(ticketsCheckConfig));
     if (null != inputStream)
       inputStream.close();
   } catch (IOException e) {
     e.printStackTrace();
   }
 }
  
  /**
   * 检测用户对象某些必须属性
   * @param user
   * @return
   * @throws SmallrainException 
   */
  public static void userFieldCheck(User user) throws SmallrainException {
    if(null == user) {
      throw new SmallrainException(601,"用户信息为空");
    }
    String check = BaseUtils.checkBeanNeededField(user, checkConfig.getJSONObject("USER"));
    if(StringUtils.isNotBlank(check)) {
      log.info("user check error:{}",check);
      throw new SmallrainException(600,check);
    }
//    if(StringUtils.isBlank(user.getAccount())) {
//      throw new SmallrainException(600,"用户信息缺少账号");
//    }
//    if(StringUtils.isBlank(user.getPassword())) {
//      throw new SmallrainException(600,"用户信息缺少密码");
//    }
//    return ;
  }
  
  /**
   * 检测角色对象某些必须属性
   * @param user
   * @return
   * @throws SmallrainException 
   */
  public static void roleFieldCheck(Role role) throws SmallrainException {
    if(null == role) {
      throw new SmallrainException(601,"角色信息为空");
    }
    String check = BaseUtils.checkBeanNeededField(role, checkConfig.getJSONObject("USER"));
    if(StringUtils.isNotBlank(check)) {
      throw new SmallrainException(600,check);
    }
//    if(StringUtils.isBlank(role.getName())) {
//      throw new SmallrainException(600,"角色信息名称");
//    }
    return ;
  }
  
  /**
   * 检测角色对象某些必须属性
   * @param user
   * @return
   * @throws SmallrainException 
   */
  public static void menuFieldCheck(Menu menu) throws SmallrainException {
    if(null == menu) {
      throw new SmallrainException(601,"角色信息为空");
    }
    String check = BaseUtils.checkBeanNeededField(menu, checkConfig.getJSONObject("USER"));
    if(StringUtils.isNotBlank(check)) {
      throw new SmallrainException(600,check);
    }
    return ;
  }
  
}
