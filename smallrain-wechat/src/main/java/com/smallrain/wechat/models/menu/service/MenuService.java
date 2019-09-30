package com.smallrain.wechat.models.menu.service;

import com.smallrain.wechat.models.menu.entity.Menu;

import com.smallrain.wechat.common.exception.SmallrainException;
import java.util.List;

/**
 *
 * @author wangying
 * @since 2019-09-29
 */
public interface MenuService {
  
  public List<Menu> getList() throws SmallrainException;
  
  public List<Menu> getListByUserId(String userId);
  
  public Menu getOne(String id) throws SmallrainException;
  
  public Menu add(Menu entity) throws SmallrainException;
  
  public Menu update(Menu entity) throws SmallrainException;
  
  public boolean delete(String... ids) throws SmallrainException;
}
