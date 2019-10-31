package com.smallrain.wechat.models.menu.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smallrain.wechat.common.exception.SmallrainException;
import com.smallrain.wechat.common.model.QueryParam;
import com.smallrain.wechat.models.menu.entity.SysMenu;

/**
 *
 * @author wangying
 * @since 2019-10-30
 */
public interface SysMenuService {
  
  public IPage<SysMenu> getList(QueryParam<SysMenu> param) throws SmallrainException;
  
  public SysMenu getOne(String id) throws SmallrainException;
  
  public SysMenu add(SysMenu entity) throws SmallrainException;
  
  public SysMenu update(SysMenu entity) throws SmallrainException;
  
  public boolean delete(String... ids) throws SmallrainException;

  public List<SysMenu> getListByUserId(String userId);
}
