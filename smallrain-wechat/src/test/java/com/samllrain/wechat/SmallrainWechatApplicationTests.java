package com.samllrain.wechat;

import com.smallrain.wechat.utils.BaseUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmallrainWechatApplicationTests {

  public static void main(String[] args) {
    String id = BaseUtils.createUuid("user");
     log.info("id: {}",id);
     log.info("length: {}",id.length());
  }

}
