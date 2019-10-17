package com.smallrain.wechat.common.model;

import lombok.Data;

@Data
public class LoginData {
  
  private String account;
  private String password;
  private boolean rememberMe;

}
