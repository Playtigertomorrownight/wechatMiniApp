package com.smallrain.wechat.models.role.controller;

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
import com.smallrain.wechat.models.role.service.SysRoleService;
import com.smallrain.wechat.models.role.entity.SysRole;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author wangying
 * @since 2019-10-30
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/models/sysRole")
public class SysRoleController {

    @Autowired
    public SysRoleService sysRoleService;

    @GetMapping("")
    public Response list() throws SmallrainException {
      log.info("获取  SysRole 列表");
      return Response.success(sysRoleService.getList());
    }
    
    @PostMapping("")
    public Response add(@RequestBody SysRole entity) throws SmallrainException  {
      log.info("添加一条 SysRole 记录");
      return Response.success(sysRoleService.add(entity));
    }
    
    @PutMapping("")
    public Response put(@RequestBody SysRole entity) throws SmallrainException  {
      log.info("更新一条 SysRole 记录");
      return Response.success(sysRoleService.update(entity));
    }
    
    @GetMapping("/{id}")
    public Response get(@PathVariable String id) throws SmallrainException  {
      log.info("根据 ID：{} 获取一条 SysRole 记录",id);
      return Response.success(sysRoleService.getOne(id));
    }
    
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable String id) throws SmallrainException  {
      log.info("根据 ID：{} 删除一条 SysRole 记录",id);
      return Response.success(sysRoleService.delete(id));
    }
}
