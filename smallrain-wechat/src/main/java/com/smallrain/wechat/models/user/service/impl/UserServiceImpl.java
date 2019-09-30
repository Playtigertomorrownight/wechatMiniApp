package com.smallrain.wechat.models.user.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallrain.wechat.common.exception.SmallrainException;
import com.smallrain.wechat.models.user.entity.User;
import com.smallrain.wechat.models.user.mapper.UserMapper;
import com.smallrain.wechat.models.user.service.UserService;
import com.smallrain.wechat.utils.AuthUtil;
import com.smallrain.wechat.utils.BaseUtils;
import com.smallrain.wechat.utils.EntityCheckUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangying
 * @since 2019-09-26
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Override
  public List<User> getList() throws SmallrainException  {
    // TODO Auto-generated method stub
    return this.list();
  }

  @Override
  public User getOne(String id) throws SmallrainException  {
    // TODO Auto-generated method stub
    return this.getById(id);
  }

  @Override
  public User add(User entity) throws SmallrainException  {
    // TODO Auto-generated method stub
    log.info("添加用户：{}",entity);
    EntityCheckUtil.userFieldCheck(entity);
    if(StringUtils.isNotBlank(entity.getId())) {
      update(entity);
    }
    //密码加密
    AuthUtil.encryptPassword(entity, true);
    entity.setId(BaseUtils.createUuid("user"));
    entity.setRegisterTime(LocalDateTime.now());
    entity.setStatus(0);
    this.save(entity);
    return entity;
  }

  @Override
  public User update(User entity) throws SmallrainException  {
    log.info("更新用户：{}",entity);
    EntityCheckUtil.userFieldCheck(entity);
    if(StringUtils.isBlank(entity.getId())||!entity.getId().startsWith("user")) {
      entity.setId(null);
      add(entity);
    }
    User oldUser = getOne(entity.getId());
    if(null == oldUser) {
      log.info("不存在 id 为：{} 的用户",entity.getId());
      add(entity);
    }
    BaseUtils.copyNotNullProperties(entity, oldUser, "id", "registerTime", "bandIp");
    log.info("更新用户。。");
    this.updateById(entity);
    return entity;
  }

  @Override
  public boolean delete(String... ids) throws SmallrainException {
    // TODO Auto-generated method stub
    log.info("根据Id：{} 删除用户。",String.join(",", ids));
    if(null==ids||ids.length==0) {
      throw new SmallrainException(601,"删除用户失败，传入的用户id 为空!");
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

  @Override
  public User getUserByUserName(String useName) throws SmallrainException {
    // TODO Auto-generated method stub
    log.info("根据用户名：{} 获取用户信息",useName);
    if(StringUtils.isBlank(useName)) {
      throw new SmallrainException(601,"获取用户失败，用户名为空");
    }
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("account", useName);
    return this.getOne(queryWrapper);
  }
  
}
