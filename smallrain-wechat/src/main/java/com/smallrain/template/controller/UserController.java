package com.smallrain.template.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smallrain.template.common.Response;
import com.smallrain.template.entity.User;
import com.smallrain.template.manager.UserManager;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wangying
 * @since 2019-08-16
 */
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserManager userManager;
  
  @PostMapping("")
  public Response addUser(@RequestBody User user) {
    return Response.success(userManager.addOrUpdateUser(user));
  }
  
  @PutMapping("")
  public Response putUser(@RequestBody User user) {
    return Response.success(userManager.addOrUpdateUser(user));
  }
  
  @GetMapping("/{userId}")
  public Response getUser(@PathVariable String userId) {
    return Response.success("");
  }
  
  @DeleteMapping("/{userId}")
  public Response deleteUser(@PathVariable String userId) {
    return Response.success("");
  }
  
}
