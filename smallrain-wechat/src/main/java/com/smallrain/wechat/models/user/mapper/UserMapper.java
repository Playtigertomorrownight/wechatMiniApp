package com.smallrain.wechat.models.user.mapper;

import com.smallrain.wechat.models.user.entity.User;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangying
 * @since 2019-09-26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
