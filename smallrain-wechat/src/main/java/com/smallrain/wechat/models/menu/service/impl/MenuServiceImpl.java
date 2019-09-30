package com.smallrain.wechat.models.menu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallrain.wechat.common.exception.SmallrainException;
import com.smallrain.wechat.models.menu.entity.Menu;
import com.smallrain.wechat.models.menu.mapper.MenuMapper;
import com.smallrain.wechat.models.menu.service.MenuService;
import com.smallrain.wechat.utils.BaseUtils;
import com.smallrain.wechat.utils.EntityCheckUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangying
 * @since 2019-09-29
 */
@Slf4j
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

  @Autowired
  private MenuMapper menuMapper;
  
  @Override
  public List<Menu> getList() throws SmallrainException {
    // TODO Auto-generated method stub
    return this.list();
  }
  

  @Override
  public List<Menu> getListByUserId(String userId) {
    // TODO Auto-generated method stub
    return menuMapper.listByUserId(userId);
  }

  @Override
  public Menu getOne(String id) throws SmallrainException {
    // TODO Auto-generated method stub
    return this.getById(id);
  }

  @Override
  public Menu add(Menu entity) throws SmallrainException {
    log.info("添加菜单：{}", entity);
    EntityCheckUtil.menuFieldCheck(entity);
    if(StringUtils.isNotBlank(entity.getId())) {
      update(entity);
    }
    entity.setId(BaseUtils.createUuid("role"));
    entity.setStatus(0);
    save(entity);
    return entity;
  }

  @Override
  public Menu update(Menu entity) throws SmallrainException {
    log.info("更新菜单：{}", entity);
    EntityCheckUtil.menuFieldCheck(entity);
    if(StringUtils.isBlank(entity.getId())||!entity.getId().startsWith("user")) {
      entity.setId(null);
      add(entity);
    }
    Menu oldMenu = getOne(entity.getId());
    if(null == oldMenu) {
      log.info("不存在 id 为：{} 的用户",entity.getId());
      add(entity);
    }
    BaseUtils.copyNotNullProperties(entity, oldMenu, "id");
    this.updateById(entity);
    return entity;
  }

  @Override
  public boolean delete(String... ids) throws SmallrainException {
 // TODO Auto-generated method stub
    log.info("根据Id：{} 删除菜单。",String.join(",", ids));
    if(null==ids||ids.length==0) {
      throw new SmallrainException(601,"删除菜单失败，传入的菜单id 为空!");
    }
    boolean result = true;
    if(ids.length==1) {
      result = this.removeById(ids[0]);
    }else {
      List<String> idsList = new ArrayList<String>();
      for(String id:ids) {
        idsList.add(id);
      }
      result = this.removeByIds(idsList);
    }
    return result;
  }

}
