package com.smallrain.wechat.models.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smallrain.wechat.models.user.entity.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangying
 * @since 2019-09-29
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
