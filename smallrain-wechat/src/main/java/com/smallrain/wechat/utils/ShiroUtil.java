package com.smallrain.wechat.utils;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.smallrain.wechat.models.menu.entity.SysMenu;
import com.smallrain.wechat.models.user.entity.SysUser;

public class ShiroUtil {

  private static final String SESSION_LOGIN_USER_KEY =  "SESSION_LOGIN_USER_KEY";
  private static final String SESSION_USER_PERMISSIONS_KEY =  "SESSION_USER_PERMISSIONS_KEY";
  
	public static SysUser getCurrentUser() {
		SysUser user = (SysUser) getSession().getAttribute(SESSION_LOGIN_USER_KEY);
		return user;
	}

	public static void setUserSession(SysUser user) {
		getSession().setAttribute(SESSION_LOGIN_USER_KEY, user);
	}

	@SuppressWarnings("unchecked")
	public static List<SysMenu> getCurrentPermissions() {
		List<SysMenu> list = (List<SysMenu>) getSession().getAttribute(SESSION_USER_PERMISSIONS_KEY);
		return list;
	}

	public static void setPermissionSession(List<SysMenu> list) {
		getSession().setAttribute(SESSION_USER_PERMISSIONS_KEY, list);
	}

	public static Session getSession() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		return session;
	}
}
