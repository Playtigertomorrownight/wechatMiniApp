package com.smallrain.wechat.models.menu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smallrain.wechat.models.menu.entity.SysMenu;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangying
 * @since 2019-10-30
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

	@Select("select distinct m.* from sys_menu m inner join sys_role_menu rm on m.id = rm.menu_id inner join sys_role_user ru on ru.role_id = rm.role_id where ru.user_id = #{userId} order by m.sort")
	List<SysMenu> listByUserId(String userId);
	
}
