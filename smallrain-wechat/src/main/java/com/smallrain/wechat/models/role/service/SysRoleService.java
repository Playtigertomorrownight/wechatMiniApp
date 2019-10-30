package com.smallrain.wechat.models.role.service;

import com.smallrain.wechat.models.role.entity.SysRole;

import com.smallrain.wechat.common.exception.SmallrainException;
import java.util.List;

/**
 *
 * @author wangying
 * @since 2019-10-30
 */
public interface SysRoleService {
  
  public List<SysRole> getList() throws SmallrainException;
  
  public SysRole getOne(String id) throws SmallrainException;
  
  public SysRole add(SysRole entity) throws SmallrainException;
  
  public SysRole update(SysRole entity) throws SmallrainException;
  
  public boolean delete(String... ids) throws SmallrainException;

  public List<SysRole> getListByUserId(String userId);
}
