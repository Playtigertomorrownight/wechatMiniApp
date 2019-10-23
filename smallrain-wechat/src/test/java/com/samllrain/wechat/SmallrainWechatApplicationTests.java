package com.samllrain.wechat;

import java.time.LocalDateTime;

import com.smallrain.wechat.common.exception.SmallrainException;
import com.smallrain.wechat.models.dto.UserDto;
import com.smallrain.wechat.models.user.entity.User;
import com.smallrain.wechat.utils.BaseUtils;
import com.smallrain.wechat.utils.EntityCheckUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmallrainWechatApplicationTests {

  public static void main(String[] args) {
    log.info(BaseUtils.resolveEntity(UserDto.class).toJSONString());
  }

}
