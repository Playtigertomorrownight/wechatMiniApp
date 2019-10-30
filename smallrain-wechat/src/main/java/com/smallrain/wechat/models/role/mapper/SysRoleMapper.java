package com.smallrain.wechat.models.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smallrain.wechat.models.role.entity.SysRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangying
 * @since 2019-10-30
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

	@Select("select * from sys_role r inner join sys_role_user ru on r.id = ru.role_id where ru.user_id = #{userId}")
	List<SysRole> listByUserId(String userId);
	
}
