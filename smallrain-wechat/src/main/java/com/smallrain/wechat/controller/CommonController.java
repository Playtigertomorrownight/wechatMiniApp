package com.smallrain.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.smallrain.wechat.models.user.entity.User;
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
  
  @GetMapping("/index")
  public ModelAndView loginSuccess() {
    User currentUser = ShiroUtil.getCurrentUser();
    if(null == currentUser) {
      return new ModelAndView("redirect:/login");
    }
    log.info("当前登陆用户：{}",currentUser.getAccount());
    //后期加上根据用户角色跳转前台页面或者后台
    //roleService.getListByUserId(userId);
    ModelAndView mv = new ModelAndView("back/index");
    mv.addObject("user", currentUser);
    return mv;
  }

}
