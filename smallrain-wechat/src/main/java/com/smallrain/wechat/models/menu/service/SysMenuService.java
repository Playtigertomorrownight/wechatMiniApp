package com.smallrain.wechat.models.menu.service;

import com.smallrain.wechat.models.menu.entity.SysMenu;

import com.smallrain.wechat.common.exception.SmallrainException;
import java.util.List;

/**
 *
 * @author wangying
 * @since 2019-10-30
 */
public interface SysMenuService {
  
  public List<SysMenu> getList() throws SmallrainException;
  
  public SysMenu getOne(String id) throws SmallrainException;
  
  public SysMenu add(SysMenu entity) throws SmallrainException;
  
  public SysMenu update(SysMenu entity) throws SmallrainException;
  
  public boolean delete(String... ids) throws SmallrainException;

  public List<SysMenu> getListByUserId(String userId);
}
