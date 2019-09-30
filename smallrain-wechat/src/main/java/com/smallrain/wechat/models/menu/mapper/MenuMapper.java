package com.smallrain.wechat.models.menu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smallrain.wechat.models.menu.entity.Menu;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangying
 * @since 2019-09-29
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
  
  @Select("select distinct m.* from menu m inner join role_menu rm on m.id = rm.menu_id inner join role_user ru on ru.role_id = rm.role_id where ru.user_id = #{userId} order by m.sort")
  List<Menu> listByUserId(String userId);

}
