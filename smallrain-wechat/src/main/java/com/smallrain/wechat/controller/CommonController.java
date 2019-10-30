package com.smallrain.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.smallrain.wechat.common.model.Response;
import com.smallrain.wechat.models.dto.DtoUtils;
import com.smallrain.wechat.models.user.entity.SysUser;
import com.smallrain.wechat.utils.ShiroUtil;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 通用页面控制类
 */
@Slf4j
@ApiIgnore
@Controller
public class CommonController {
  
  /**
   * 获取实体信息
   * 
   * @return
   */
  @ResponseBody
  @GetMapping("/model/info/{modelName}")
  public Response getModelInfo(@PathVariable String modelName) {
    JSONObject info = DtoUtils.getModelInfo(modelName);
    if(null==info) {
      return Response.fail(-1, "实体信息为空，请确认名称正确");
    }
    return Response.success(info);
  }

	/**
	 * 登录成功后的跳转
	 * 
	 * @return
	 */
	@GetMapping("/index")
	public ModelAndView loginSuccess() {
		SysUser currentUser = ShiroUtil.getCurrentUser();
		if (null == currentUser) {
			return new ModelAndView("redirect:/login");
		}
		log.info("当前登陆用户：{}", currentUser.getAccount());
		// 后期加上根据用户角色跳转前台页面或者后台
		// roleService.getListByUserId(userId);
		ModelAndView mv = new ModelAndView("back/index");
		mv.addObject("user", currentUser);
		return mv;
	}

	/**
	 * 未授权提示页面
	 * 
	 * @return
	 */
	@GetMapping("/unauthorized")
	public ModelAndView unauthorized() {
		ModelAndView mv = new ModelAndView("error/unauthorized");
		return mv;
	}

}
