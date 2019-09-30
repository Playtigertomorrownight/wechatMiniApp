package com.smallrain.wechat.common;

/**
 * 静态常量
 * @author wangying.dz3
 *
 */
public interface Constants {

  /**
   * 用户状态 - 正常
   */
  Integer USER_STATUS_NORMAL = 0;
  
  /**
   * 用户状态 - 异常
   */
  Integer USER_STATUS_EXCEPTION = -1;
  
  
  /**
   * 登录后请求头带的 authtoken key
   */
  String LOGIN_AUTH_TOKEN_KEY = "smallrainAuthToken";
  
}
