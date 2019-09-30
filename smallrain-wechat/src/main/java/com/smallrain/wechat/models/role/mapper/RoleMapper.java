package com.smallrain.wechat.models.role.mapper;

import com.smallrain.wechat.models.role.entity.Role;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangying
 * @since 2019-09-27
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

  @Select("select * from role r inner join role_user ru on r.id = ru.role_id where ru.user_id = #{userId}")
  List<Role> listByUserId(String userId);
  
}
