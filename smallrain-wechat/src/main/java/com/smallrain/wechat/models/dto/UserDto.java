package com.smallrain.wechat.models.dto;

import com.smallrain.wechat.common.annotations.InputType;
import com.smallrain.wechat.common.annotations.ModelEditField;

import lombok.Data;

/**
 * 用户数据转换表 -前台展示数据
 * @author wangying.dz3
 *
 */
@Data
public class UserDto {
  
  @ModelEditField(name="账号",required=true,show=true)
  private String account;

  @ModelEditField(name="密码",required=true,type=InputType.PASSWORD)
  private String password;

  @ModelEditField(name="昵称",edit=false,show=true,required=true)
  private String name;
  
  @ModelEditField(name="真实姓名")
  private String realName;

  @ModelEditField(name="签名")
  private String signature;
  
  @ModelEditField(name="性别",show=true,required=true,type=InputType.RADIO)
  private Integer sex;

  @ModelEditField(name="手机号",show=true)
  private String phone;

  @ModelEditField(name="邮箱",show=true)
  private String email;

  @ModelEditField(name="身份证号")
  private String identity;

  @ModelEditField(name="角色",type=InputType.CHECKBOX)
  private String role;

  @ModelEditField(name="头像",type=InputType.IMAGE)
  private String headImage="";

}
