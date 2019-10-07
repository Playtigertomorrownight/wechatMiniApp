package com.smallrain.wechat.common.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSONObject;
import com.smallrain.wechat.common.Constants;
import com.smallrain.wechat.common.manager.token.TokenManager;
import com.smallrain.wechat.common.model.Response;
import com.smallrain.wechat.utils.SpringUtil;

/**
 * Restful方式登陆<br>
 * 在参数中或者header里加参数login-token作为登陆凭证<br>
 * 参数值是登陆成功后的返回值中获取
 * 
 */
public class RestfulFilter extends UserFilter {

	private static String info = JSONObject.toJSONString(Response.fail(HttpStatus.UNAUTHORIZED.value(), "token不存在或者过期"));

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		// 允许跨域请求
		if (HttpMethod.OPTIONS.name().equalsIgnoreCase(WebUtils.toHttp(request).getMethod())) {
			return Boolean.TRUE;
		}
		String loginToken = getToken(request);
		if (StringUtils.isBlank(loginToken)) { // 非Restful方式
			return super.isAccessAllowed(request, response, mappedValue);
		}
		//根据token获取用户信息
		TokenManager tokenManager = SpringUtil.getBean(TokenManager.class);
		UsernamePasswordToken token = tokenManager.getToken(loginToken);
		if (token != null) {
			try {
				Subject subject = getSubject(request, response);
				if (subject.getPrincipal() == null) {
					subject.login(token);
				}
				return Boolean.TRUE;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 根据参数或者header获取auth-token
	 * 
	 * @param request
	 * @return
	 */
	public static String getToken(ServletRequest request) {
		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		String loginToken = httpServletRequest.getParameter(Constants.LOGIN_AUTH_TOKEN_KEY);
		if (StringUtils.isBlank(loginToken)) {
			loginToken = httpServletRequest.getHeader(Constants.LOGIN_AUTH_TOKEN_KEY);
		}
		return loginToken;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		String loginToken = getToken(request);
		if (StringUtils.isBlank(loginToken)) {
			return super.onAccessDenied(request, response);
		}
		writeResponse(WebUtils.toHttp(response), HttpStatus.UNAUTHORIZED.value(), info);
		return false;
	}

	/**
	 * 设置跨域
	 * 
	 * @param response
	 * @param status
	 * @param json
	 */
	public static void writeResponse(HttpServletResponse response, int status, String json) {
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "*");
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(status);
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
