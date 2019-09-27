package com.smallrain.wechat.models.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smallrain.wechat.common.Response;
import com.smallrain.wechat.common.exception.SmallrainException;
import com.smallrain.wechat.models.user.entity.User;
import com.smallrain.wechat.models.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author wangying
 * @since 2019-09-26
 */
@Slf4j
@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping("")
    public Response list() throws SmallrainException {
      log.info("获取  User 列表");
      return Response.success(userService.getList());
    }
    
    @PostMapping("")
    public Response add(@RequestBody User entity) throws SmallrainException {
      log.info("添加一条 User 记录");
      return Response.success(userService.add(entity));
    }
    
    @PutMapping("")
    public Response put(@RequestBody User entity) throws SmallrainException {
      log.info("更新一条 User 记录");
      return Response.success(userService.update(entity));
    }
    
    @GetMapping("/{id}")
    public Response get(@PathVariable String id) throws SmallrainException {
      log.info("根据 ID：{} 获取一条 User 记录",id);
      return Response.success(userService.getOne(id));
    }
    
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable String id) throws SmallrainException {
      log.info("根据 ID：{} 删除一条 User 记录",id);
      return Response.success(userService.delete(id));
    }
}
