package com.smallrain.wechat.utils;

import org.apache.commons.lang3.StringUtils;

import com.smallrain.wechat.common.exception.SmallrainException;
import com.smallrain.wechat.models.role.entity.Role;
import com.smallrain.wechat.models.user.entity.User;

/**
 * 实体类检查工具类
 * @author wangying.dz3
 *
 */
public class EntityCheckUtil {

  
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
    if(StringUtils.isBlank(user.getAccount())) {
      throw new SmallrainException(600,"用户信息缺少账号");
    }
    if(StringUtils.isBlank(user.getPassword())) {
      throw new SmallrainException(600,"用户信息缺少密码");
    }
    return ;
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
    if(StringUtils.isBlank(role.getName())) {
      throw new SmallrainException(600,"角色信息名称");
    }
    return ;
  }
  
}
