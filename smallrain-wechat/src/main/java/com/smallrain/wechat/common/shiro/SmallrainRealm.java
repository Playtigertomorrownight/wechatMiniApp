package com.smallrain.wechat.common.shiro;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smallrain.wechat.common.Constants;
import com.smallrain.wechat.common.exception.SmallrainException;
import com.smallrain.wechat.models.menu.entity.SysMenu;
import com.smallrain.wechat.models.menu.service.SysMenuService;
import com.smallrain.wechat.models.role.entity.SysRole;
import com.smallrain.wechat.models.role.service.SysRoleService;
import com.smallrain.wechat.models.user.entity.SysUser;
import com.smallrain.wechat.models.user.service.SysUserService;
import com.smallrain.wechat.utils.AuthUtil;
import com.smallrain.wechat.utils.ShiroUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SmallrainRealm extends AuthorizingRealm {

  // 用户对应的角色信息与权限信息都保存在数据库中，通过UserService he RoleService获取数据

  @Autowired
  private SysUserService userService;

  @Autowired
  private SysRoleService roleService;
  
  @Autowired
  private SysMenuService menuService;

  /**
   * 获取身份验证信息 Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
   *
   * @param authenticationToken
   *          用户身份信息 token
   * @return 返回封装了用户信息的 AuthenticationInfo 实例
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    log.info("————身份认证方法————");
    UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
    // 从数据库获取对应用户名密码的用户
    SysUser user = null;
    try {
      user = userService.getUserByUserName(token.getUsername());
    } catch (SmallrainException e) {
      throw new AccountException("根据用户名获取用户信息失败：" + e.getMessage());
    }
    if (null == user) {
      throw new AccountException("用户名不存在！");
    }
    String password = new String((char[]) token.getCredentials()); 
    //内部加密
    password = AuthUtil.encryptPassword(password, user.getCredentialsSalt());
    if (!password.equals(user.getPassword())) {
      throw new AccountException("您的密码不正确");
    }
    if (user.getStatus() != Constants.USER_STATUS_NORMAL) {
      throw new IncorrectCredentialsException("用户无效状态，请联系管理员");
    }
    ShiroUtil.setUserSession(user);
    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(),
        ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
    return authenticationInfo;
  }

  /**
   * 提供用户信息返回权限信息
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    log.info("————权限认证————");
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    SysUser user = ShiroUtil.getCurrentUser();
    if (null == user) {
      String username = (String) SecurityUtils.getSubject().getPrincipal();
      try {
        user = userService.getUserByUserName(username);
      } catch (SmallrainException e) {
        log.error("获取用户角色失败：{}", e.getMessage());
      }
    }
    //获取并设置用户角色
    List<SysRole> roles = roleService.getListByUserId(user.getId());
    Set<String> roleNames = roles.stream().filter(r -> null!=r)
        .map(SysRole::getName).collect(Collectors.toSet());
    info.setRoles(roleNames);
    //获取并设置用户菜单权限
    List<SysMenu> menuList = menuService.getListByUserId(user.getId());
    ShiroUtil.setPermissionSession(menuList);
    Set<String> permissions = menuList.stream().filter(m -> null!=m && !StringUtils.isEmpty(m.getPermission()))
        .map(SysMenu::getPermission).collect(Collectors.toSet());
    info.setStringPermissions(permissions);
    return info;
  }

  /**
   * 重写缓存key，否则集群下session共享时，会重复执行doGetAuthorizationInfo权限配置
   */
  @Override
  protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
    SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) principals;
    Object object = principalCollection.getPrimaryPrincipal();
    if (object instanceof SysUser) {
    	SysUser user = (SysUser) object;
      return StringUtils.join("authorization:cache:key:users:", user.getId());
    }
    return super.getAuthorizationCacheKey(principals);
  }

}