package com.smallrain.wechat.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smallrain.wechat.common.manager.token.TokenManager;
import com.smallrain.wechat.common.model.LoginData;
import com.smallrain.wechat.common.model.Response;
import com.smallrain.wechat.common.model.Token;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 登陆相关接口
 * 
 *
 */
@Slf4j
@Api(tags = "登陆相关接口")
@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private ServerProperties serverProperties;
//	@Autowired
//	private RoleService roleService;

	@ApiIgnore
	@GetMapping("")
  public ModelAndView login() {
	  ModelAndView mv = new ModelAndView("login");
    return mv;
  }
	
	@ApiOperation(value = "web 端登陆")
	@ResponseBody
	@PostMapping("/web")
	public Response login(@RequestBody LoginData loginData) {
	  log.info("web 用户登录: account： {} , password： {} , rememberMe: {}",loginData.getAccount(),loginData.getPassword(),loginData.isRememberMe());
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginData.getAccount(), loginData.getPassword());
		usernamePasswordToken.setRememberMe(loginData.isRememberMe());
		try {
		  SecurityUtils.getSubject().login(usernamePasswordToken);
		  // 设置shiro的session过期时间
		  SecurityUtils.getSubject().getSession().setTimeout(serverProperties.getServlet().getSession().getTimeout().toMillis());
		}catch(AuthenticationException e) {
		  return Response.fail(-1, "登录失败："+e.getMessage());
		}
		return Response.success("验证成功");
  }

	@ApiOperation(value = "Restful 方式登陆,前后端分离时登录接口")
	@ResponseBody
	@PostMapping("/rest")
	public Token restfulLogin(@RequestBody LoginData loginData) {
	  log.info("web 用户登录: account： {} , password： {} , rememberMe: {}",loginData.getAccount(),loginData.getPassword(),loginData.isRememberMe());
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginData.getAccount(), loginData.getPassword());
		SecurityUtils.getSubject().login(usernamePasswordToken);
		return tokenManager.saveToken(usernamePasswordToken);
	}

}
