package com.smallrain.wechat.models.role.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallrain.wechat.common.exception.SmallrainException;
import com.smallrain.wechat.models.role.entity.Role;
import com.smallrain.wechat.models.role.mapper.RoleMapper;
import com.smallrain.wechat.models.role.service.RoleService;
import com.smallrain.wechat.utils.BaseUtils;
import com.smallrain.wechat.utils.EntityCheckUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangying
 * @since 2019-09-27
 */
@Slf4j
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

  @Override
  public List<Role> getList() throws SmallrainException {
    // TODO Auto-generated method stub
    return this.list();
  }

  @Override
  public Role getOne(String id) throws SmallrainException {
    return this.getById(id);
  }

  @Override
  public Role add(Role entity) throws SmallrainException {
    log.info("添加角色：{}", entity);
    EntityCheckUtil.roleFieldCheck(entity);
    if(StringUtils.isNotBlank(entity.getId())) {
      update(entity);
    }
    entity.setId(BaseUtils.createUuid("role"));
    entity.setDeleteFlag(0);
    save(entity);
    return entity;
  }

  @Override
  public Role update(Role entity) throws SmallrainException {
    log.info("更新角色：{}", entity);
    EntityCheckUtil.roleFieldCheck(entity);
    if(StringUtils.isBlank(entity.getId())||!entity.getId().startsWith("user")) {
      entity.setId(null);
      add(entity);
    }
    Role oldRole = getOne(entity.getId());
    if(null == oldRole) {
      log.info("不存在 id 为：{} 的用户",entity.getId());
      add(entity);
    }
    BaseUtils.copyNotNullProperties(entity, oldRole, "id");
    this.updateById(entity);
    return entity;
  }

  @Override
  public boolean delete(String... ids) throws SmallrainException {
    // TODO Auto-generated method stub
    log.info("根据Id：{} 删除角色。",String.join(",", ids));
    if(null==ids||ids.length==0) {
      throw new SmallrainException(601,"删除角色失败，传入的用户id 为空!");
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
