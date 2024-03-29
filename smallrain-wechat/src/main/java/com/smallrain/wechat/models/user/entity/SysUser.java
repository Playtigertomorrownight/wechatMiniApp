package com.smallrain.wechat.models.user.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangying
 * @since 2019-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysUser对象", description="")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户 id")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "用户账号")
    private String account;

    @ApiModelProperty(value = "用户密码")
    private String password;

    private String salt;

    @ApiModelProperty(value = "用户性别： 0- 男，1- 女")
    private Integer sex;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "用户邮箱   ")
    private String email;

    @ApiModelProperty(value = "用户身份证号")
    private String identity;

    @ApiModelProperty(value = "头像")
    private String headImage = "";

    @ApiModelProperty(value = "用户角色ID")
    private String role;

    @ApiModelProperty(value = "注册时间")
    private Date registerTime;

    @ApiModelProperty(value = "绑定 ip ")
    private String bandIp;

    @ApiModelProperty(value = "用户真实姓名")
    private String realName;

    @ApiModelProperty(value = "签名")
    private String signature;

    @ApiModelProperty(value = "删除标记")
    private Integer status;
    
    public String getCredentialsSalt() {
        // TODO Auto-generated method stub
        return "user-credentials-salt-"+salt;
      }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
