package com.smallrain.template.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallrain.template.entity.User;
import com.smallrain.template.mapper.UserMapper;
import com.smallrain.template.service.UserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangying
 * @since 2019-08-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
