package com.smallrain.wechat.common.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSONObject;
import com.smallrain.wechat.common.manager.token.TokenManager;
import com.smallrain.wechat.common.model.Response;
import com.smallrain.wechat.models.user.entity.SysUser;
import com.smallrain.wechat.utils.ShiroUtil;
import com.smallrain.wechat.utils.SpringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 退出<br>
 * web退出和restful方式退出<br>
 * 后者会删除缓存的token
 * 
 * @author wangying
 *
 */
@Slf4j
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {

	private static String SUCCESS_INFO = JSONObject.toJSONString(Response.success("退出成功"));
	private static String ERR_INFO = JSONObject.toJSONString(Response.fail(HttpStatus.BAD_REQUEST.value(), "退出失败,token不存在"));

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		String loginToken = RestfulFilter.getToken(request);
		SysUser user = ShiroUtil.getCurrentUser();
		if(null==user) {
		  log.info("用户为空，退出成功");
		  return super.preHandle(request, response);
		}
		if (StringUtils.isBlank(loginToken)) {// 非Restful方式
			boolean flag = super.preHandle(request, response);
			log.info("{}退出成功", user.getAccount());
			// SpringUtil.getBean(SysLogService.class).save(user.getId(), "退出", true, null);
			return flag;
		} else {
			TokenManager tokenManager = SpringUtil.getBean(TokenManager.class);
			boolean flag = tokenManager.deleteToken(loginToken);
			if (flag) {
				RestfulFilter.writeResponse(WebUtils.toHttp(response), HttpStatus.OK.value(), SUCCESS_INFO);
				log.debug("{}退出成功", user.getAccount());
			} else {
				RestfulFilter.writeResponse(WebUtils.toHttp(response), HttpStatus.BAD_REQUEST.value(), ERR_INFO);
			}
			// SpringUtil.getBean(SysLogService.class).save(user.getId(), "token方式退出", flag,
			// null);
			return false;
		}
	}

}
