package com.samllrain.wechat;

import com.smallrain.wechat.models.dto.UserDto;
import com.smallrain.wechat.utils.BaseUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmallrainWechatApplicationTests {

  public static void main(String[] args) {
    log.info(BaseUtils.resolveEntity(UserDto.class).toJSONString());
  }

}
