package com.smallrain.wechat.common.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smallrain.wechat.common.exception.SmallrainException;
import com.smallrain.wechat.models.role.entity.Role;
import com.smallrain.wechat.models.role.service.RoleService;
import com.smallrain.wechat.models.user.entity.User;
import com.smallrain.wechat.models.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SmallrainRealm extends AuthorizingRealm {
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private RoleService roleService;

  @Autowired
  private void setUserMapper(UserService userService) {
      this.userService = userService;
  }

  /**
   * 获取身份验证信息
   * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
   *
   * @param authenticationToken 用户身份信息 token
   * @return 返回封装了用户信息的 AuthenticationInfo 实例
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
      log.info("————身份认证方法————");
      UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
      // 从数据库获取对应用户名密码的用户
      User user = null;
      try {
        user = userService.getUserByUserName(token.getUsername());
      } catch (SmallrainException e) {
        throw new AccountException("根据用户名获取用户信息失败："+e.getMessage());
      }
      if (null == user) {
          throw new AccountException("用户名不正确");
      }
      String password = user.getPassword();
      if (!password.equals(new String((char[]) token.getCredentials()))) {
          throw new AccountException("密码不正确");
      }
      return new SimpleAuthenticationInfo(token.getPrincipal(), password, getName());
  }

  /**
   * 获取授权信息
   *
   * @param principalCollection
   * @return
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
      log.info("————权限认证————");
      String username = (String) SecurityUtils.getSubject().getPrincipal();
      SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
      //获得该用户角色
      String role = "";
      try {
        User user = userService.getUserByUserName(username);
        if(null!=user&&StringUtils.isNotBlank(user.getRole())) {
          Role roleEntity = roleService.getOne(user.getRole());
          if(null!=roleEntity) {
            role = roleEntity.getName();
          }
        }
      } catch (SmallrainException e) {
        log.error("获取用户角色失败：{}",e.getMessage());
      }
      Set<String> set = new HashSet<>();
      //需要将 role 封装到 Set 作为 info.setRoles() 的参数
      set.add(role);
      //设置该用户拥有的角色
      info.setRoles(set);
      return info;
  }
}