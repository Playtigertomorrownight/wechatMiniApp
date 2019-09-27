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
import com.smallrain.wechat.common.Response;
import com.smallrain.wechat.common.exception.SmallrainException;
import com.smallrain.wechat.models.role.service.RoleService;
import com.smallrain.wechat.models.role.entity.Role;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author wangying
 * @since 2019-09-27
 */
@Slf4j
@RestController
@RequestMapping("/v1/role")
public class RoleController {

    @Autowired
    public RoleService roleService;

    @GetMapping("")
    public Response list() throws SmallrainException {
      log.info("获取  Role 列表");
      return Response.success(roleService.getList());
    }
    
    @PostMapping("")
    public Response add(@RequestBody Role entity) throws SmallrainException  {
      log.info("添加一条 Role 记录");
      return Response.success(roleService.add(entity));
    }
    
    @PutMapping("")
    public Response put(@RequestBody Role entity) throws SmallrainException  {
      log.info("更新一条 Role 记录");
      return Response.success(roleService.update(entity));
    }
    
    @GetMapping("/{id}")
    public Response get(@PathVariable String id) throws SmallrainException  {
      log.info("根据 ID：{} 获取一条 Role 记录",id);
      return Response.success(roleService.getOne(id));
    }
    
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable String id) throws SmallrainException  {
      log.info("根据 ID：{} 删除一条 Role 记录",id);
      return Response.success(roleService.delete(id));
    }
}
