package com.smallrain.wechat.models.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smallrain.wechat.common.exception.SmallrainException;
import com.smallrain.wechat.common.model.QueryParam;
import com.smallrain.wechat.models.user.entity.SysUser;

/**
 *
 * @author wangying
 * @since 2019-10-30
 */
public interface SysUserService {
  
  public IPage<SysUser> getList(QueryParam<SysUser> param) throws SmallrainException;
  
  public SysUser getOne(String id) throws SmallrainException;
  
  public SysUser add(SysUser entity) throws SmallrainException;
  
  public SysUser update(SysUser entity) throws SmallrainException;
  
  public boolean delete(String... ids) throws SmallrainException;

  public SysUser getUserByUserName(String useName) throws SmallrainException;
}
