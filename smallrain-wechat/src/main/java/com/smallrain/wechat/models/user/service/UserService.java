package com.smallrain.wechat.models.user.service;

import com.smallrain.wechat.models.user.entity.User;

import com.smallrain.wechat.common.exception.SmallrainException;
import java.util.List;

/**
 *
 * @author wangying
 * @since 2019-09-29
 */
public interface UserService {
  
  public List<User> getList() throws SmallrainException;
  
  public User getOne(String id) throws SmallrainException;
  
  public User add(User entity) throws SmallrainException;
  
  public User update(User entity) throws SmallrainException;
  
  public boolean delete(String... ids) throws SmallrainException;
  
  public User getUserByUserName(String useName) throws SmallrainException;
  
}
