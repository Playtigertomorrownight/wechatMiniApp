package com.smallrain.wechat.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smallrain.wechat.common.manager.token.TokenManager;
import com.smallrain.wechat.common.model.Token;
import com.smallrain.wechat.models.user.entity.User;
import com.zw.admin.server.annotation.LogAnnotation;
import com.zw.admin.server.utils.UserUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 登陆相关接口
 * 
 *
 */
@Api(tags = "登陆相关接口")
@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private ServerProperties serverProperties;

	@ApiOperation(value = "web端登陆")
	@PostMapping("/web")
	public void login(String username, String password) {
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
		SecurityUtils.getSubject().login(usernamePasswordToken);
		// 设置shiro的session过期时间
		SecurityUtils.getSubject().getSession().setTimeout(serverProperties.getServlet().getSession().getTimeout().toMillis());
	}

	@ApiOperation(value = "Restful 方式登陆,前后端分离时登录接口")
	@PostMapping("/rest")
	public Token restfulLogin(String username, String password) {
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
		SecurityUtils.getSubject().login(usernamePasswordToken);

		return tokenManager.saveToken(usernamePasswordToken);
	}

	@ApiOperation(value = "当前登录用户信息")
	@GetMapping("/user")
	public User getLoginInfo() {
		return UserUtil.getCurrentUser();
	}

}