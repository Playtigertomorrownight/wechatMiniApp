package com.smallrain.wechat.models.user.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallrain.wechat.models.user.entity.User;
import com.smallrain.wechat.models.user.mapper.UserMapper;
import com.smallrain.wechat.models.user.service.UserService;

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
