package com.smallrain.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.smallrain.wechat.models.user.entity.User;
import com.smallrain.wechat.utils.ShiroUtil;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 管理后台相关控制类
 * 
 *
 */

@ApiIgnore
@Controller
@RequestMapping("/back")
public class BackManagerController {

	/**
	 * 后台管理 - 首页
	 * 
	 * @return
	 */
	@GetMapping("/main")
	public ModelAndView backMain() {
		User currentUser = ShiroUtil.getCurrentUser();
		ModelAndView mv = new ModelAndView("back/main");
		mv.addObject("user", currentUser);
		return mv;
	}

	/**
	 * 后台管理 - 菜单管理
	 * 
	 * @return
	 */
	//@RequiresPermissions(value = "admin:menu")
	@GetMapping("/menu")
	public ModelAndView backMenu() {
		ModelAndView mv = new ModelAndView("back/menu");
		return mv;
	}
	
	/**
	 * 后台管理 - 菜单管理
	 * 
	 * @return
	 */
	//@RequiresPermissions(value = "admin:menu")
	@GetMapping("/user")
	public ModelAndView backUser() {
		ModelAndView mv = new ModelAndView("back/user");
		return mv;
	}

}
