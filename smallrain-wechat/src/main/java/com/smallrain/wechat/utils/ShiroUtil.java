package com.smallrain.wechat.utils;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.smallrain.wechat.models.menu.entity.Menu;
import com.smallrain.wechat.models.user.entity.User;

public class ShiroUtil {

  private static final String SESSION_LOGIN_USER_KEY =  "SESSION_LOGIN_USER_KEY";
  private static final String SESSION_USER_PERMISSIONS_KEY =  "SESSION_USER_PERMISSIONS_KEY";
  
	public static User getCurrentUser() {
		User user = (User) getSession().getAttribute(SESSION_LOGIN_USER_KEY);
		return user;
	}

	public static void setUserSession(User user) {
		getSession().setAttribute(SESSION_LOGIN_USER_KEY, user);
	}

	@SuppressWarnings("unchecked")
	public static List<Menu> getCurrentPermissions() {
		List<Menu> list = (List<Menu>) getSession().getAttribute(SESSION_USER_PERMISSIONS_KEY);
		return list;
	}

	public static void setPermissionSession(List<Menu> list) {
		getSession().setAttribute(SESSION_USER_PERMISSIONS_KEY, list);
	}

	public static Session getSession() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		return session;
	}
}
