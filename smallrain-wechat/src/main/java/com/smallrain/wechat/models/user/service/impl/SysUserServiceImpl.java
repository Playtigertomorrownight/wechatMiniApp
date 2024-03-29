package com.smallrain.wechat.models.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallrain.wechat.common.exception.SmallrainException;
import com.smallrain.wechat.common.model.QueryParam;
import com.smallrain.wechat.models.user.entity.SysUser;
import com.smallrain.wechat.models.user.mapper.SysUserMapper;
import com.smallrain.wechat.models.user.service.SysUserService;
import com.smallrain.wechat.utils.AuthUtil;
import com.smallrain.wechat.utils.BaseUtils;
import com.smallrain.wechat.utils.EntityCheckUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangying
 * @since 2019-10-30
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

  
	@Override
	public IPage<SysUser> getList(QueryParam<SysUser> param) throws SmallrainException {
		// TODO Auto-generated method stub
	  return this.page(param.getPage(), param.getQueryWrapper());
	}

	@Override
	public SysUser getOne(String id) throws SmallrainException {
		// TODO Auto-generated method stub
		return this.getById(id);
	}

	@Override
	public SysUser add(SysUser entity) throws SmallrainException {
		// TODO Auto-generated method stub
		log.info("添加用户：{}", entity);
		if (StringUtils.isNotBlank(entity.getId())) {
			update(entity);
		}
		entity.setId(BaseUtils.createUuid("user"));
		entity.setRegisterTime(new Date());
		entity.setStatus(0);
		EntityCheckUtil.userFieldCheck(entity);
		// 密码加密
		AuthUtil.encryptPassword(entity, true);
		this.save(entity);
		return entity;
	}

	@Override
	public SysUser update(SysUser entity) throws SmallrainException {
		// TODO Auto-generated method stub
		log.info("更新用户：{}", entity);
		if (StringUtils.isBlank(entity.getId()) || !entity.getId().startsWith("user")) {
			entity.setId(null);
			add(entity);
		}
		SysUser oldUser = getOne(entity.getId());
		if (null == oldUser) {
			log.info("不存在 id 为：{} 的用户", entity.getId());
			add(entity);
		}
		BaseUtils.copyNotNullProperties(entity, oldUser, "id", "registerTime", "bandIp");
		EntityCheckUtil.userFieldCheck(entity);
		log.info("更新用户。。");
		this.updateById(entity);
		return entity;
	}

	@Override
	public boolean delete(String... ids) throws SmallrainException {
		// TODO Auto-generated method stub
		log.info("根据Id：{} 删除用户。", String.join(",", ids));
		if (null == ids || ids.length == 0) {
			throw new SmallrainException(601, "删除用户失败，传入的用户id 为空!");
		}
		boolean result = true;
		if (ids.length == 1) {
			result = this.removeById(ids[0]);
		} else {
			List<String> idsList = new ArrayList<String>();
			for (String id : ids) {
				idsList.add(id);
			}
			result = this.removeByIds(idsList);
		}
		return result;
	}

	@Override
	public SysUser getUserByUserName(String useName) throws SmallrainException {
		// TODO Auto-generated method stub
		log.info("根据用户名：{} 获取用户信息", useName);
		if (StringUtils.isBlank(useName)) {
			throw new SmallrainException(601, "获取用户失败，用户名为空");
		}
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("account", useName);
		return this.getOne(queryWrapper);
	}

}
