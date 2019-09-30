package com.smallrain.wechat.common.manager.token.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.smallrain.wechat.common.BaseProperties;
import com.smallrain.wechat.common.cache.EncheManager;
import com.smallrain.wechat.common.manager.token.TokenManager;
import com.smallrain.wechat.common.model.Token;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * EhCache实现的Token<br>
 * 默认采用此实现
 * 
 */
@Primary
@Service
public class EhCacheTokenManager implements TokenManager {

  private  static final String LOGIN_USER_TOKEN_CACHE = "TokenCache";
  
  @Autowired
  private EncheManager encheManager;
  
  @Autowired
  private BaseProperties baseProperties;

	@Override
	public Token saveToken(UsernamePasswordToken usernamePasswordToken) {
		Cache cache = encheManager.getCache(LOGIN_USER_TOKEN_CACHE);
		String key = UUID.randomUUID().toString();
		Element element = new Element(key, usernamePasswordToken);
		element.setTimeToLive(baseProperties.getTokenExpire());
		cache.put(element);
		return new Token(key, DateUtils.addSeconds(new Date(), baseProperties.getTokenExpire()));
	}

	@Override
	public UsernamePasswordToken getToken(String key) {
		Cache cache = encheManager.getCache(LOGIN_USER_TOKEN_CACHE);
		Element element = cache.get(key);
		if (element != null) {
			return (UsernamePasswordToken) element.getObjectValue();
		}
		return null;
	}

	@Override
	public boolean deleteToken(String key) {
		Cache cache = encheManager.getCache(LOGIN_USER_TOKEN_CACHE);
		return cache.remove(key);
	}
}
