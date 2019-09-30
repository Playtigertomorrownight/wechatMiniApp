package com.samllrain.wechat;

import java.time.LocalDateTime;

import com.smallrain.wechat.common.exception.SmallrainException;
import com.smallrain.wechat.models.user.entity.User;
import com.smallrain.wechat.utils.EntityCheckUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmallrainWechatApplicationTests {

  public static void main(String[] args) {
    User user = new User();
    user.setAccount("5564");
    user.setPassword("4651");
    user.setRegisterTime(LocalDateTime.now());
    user.setStatus(0);
    try {
      EntityCheckUtil.userFieldCheck(user);
    } catch (SmallrainException e) {
      log.error(e.getMessage());
    }
    log.info("user: {}", user);
  }

}
