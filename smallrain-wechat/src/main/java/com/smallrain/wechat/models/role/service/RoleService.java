package com.smallrain.wechat.models.role.service;

import com.smallrain.wechat.models.role.entity.Role;

import com.smallrain.wechat.common.exception.SmallrainException;
import java.util.List;

/**
 *
 * @author wangying
 * @since 2019-09-27
 */
public interface RoleService {
  
  public List<Role> getList() throws SmallrainException;
  
  public Role getOne(String id) throws SmallrainException;
  
  public Role add(Role entity) throws SmallrainException;
  
  public Role update(Role entity) throws SmallrainException;
  
  public boolean delete(String... id) throws SmallrainException;
}
