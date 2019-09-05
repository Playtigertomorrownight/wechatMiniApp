package com.smallrain.wechat.models.user.manager;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smallrain.wechat.common.Response;
import com.smallrain.wechat.models.user.entity.User;
import com.smallrain.wechat.models.user.service.UserService;
import com.smallrain.wechat.utils.BaseUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户管理类，上层由 controller 类调用
 * @author wangying.dz3
 *
 */
@Slf4j
@Component
public class UserManager {
  
  @Autowired
  private UserService userService;
  
  /**
   * 添加或者更新用户
   * @return
   */
  public Response addOrUpdateUser(User user) {
    log.info("添加或更新用户：{}",user);
    String check = userFieldCheck(user);
    if(StringUtils.isNotBlank(check)) {
      return Response.fail(-1, String.format("缺少必传字段：%s", check));
    }
    if(StringUtils.isBlank(user.getId())) {
      log.info("新建用户。。");
      user.setId(BaseUtils.createUuid("user"));
      user.setRegisterTime(LocalDateTime.now());
      return Response.excute("更新用户",userService.save(user));
    }else {
      log.info("更新用户。。");
      return Response.excute("更新用户",userService.updateById(user));
    }
  }
  
  /**
   * 根据用户ID获取用户
   * @return
   */
  public Response getUserById(String userId) {
    return Response.success(userService.getById(userId));
  }
  
  
  
  /**
   * 检测某些必须属性
   * @param user
   * @return
   */
  private String userFieldCheck(User user) {
    StringBuilder checkResult = new StringBuilder();
    if(StringUtils.isBlank(user.getAccount())) {
      checkResult.append("用户账号、");
    }
    if(StringUtils.isBlank(user.getPassword())) {
      checkResult.append("用户密码、");
    }
    return checkResult.length()>0?checkResult.substring(0,checkResult.length()-1):"";
  }
  
}
