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
import com.smallrain.wechat.common.model.Response;
import com.smallrain.wechat.common.exception.SmallrainException;
import com.smallrain.wechat.models.user.service.SysUserService;
import com.smallrain.wechat.models.user.entity.SysUser;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author wangying
 * @since 2019-10-30
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/models/user")
public class SysUserController {

    @Autowired
    public SysUserService sysUserService;

    @GetMapping("")
    public Response list() throws SmallrainException {
      log.info("获取  SysUser 列表");
      return Response.success(sysUserService.getList());
    }
    
    @PostMapping("")
    public Response add(@RequestBody SysUser entity) throws SmallrainException  {
      log.info("添加一条 SysUser 记录");
      return Response.success(sysUserService.add(entity));
    }
    
    @PutMapping("")
    public Response put(@RequestBody SysUser entity) throws SmallrainException  {
      log.info("更新一条 SysUser 记录");
      return Response.success(sysUserService.update(entity));
    }
    
    @GetMapping("/{id}")
    public Response get(@PathVariable String id) throws SmallrainException  {
      log.info("根据 ID：{} 获取一条 SysUser 记录",id);
      return Response.success(sysUserService.getOne(id));
    }
    
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable String id) throws SmallrainException  {
      log.info("根据 ID：{} 删除一条 SysUser 记录",id);
      return Response.success(sysUserService.delete(id));
    }
}
