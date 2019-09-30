package com.smallrain.wechat.common.cache;

import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * enche 管理类
 * 
 * @author wangying.dz3
 *
 */
@Slf4j
@Component
public class EncheManager {

  private static CacheManager CACHE_MANAGER = null;

  static {
    log.info("初始化  EncheManager 。。");
    ClassPathResource encheConfig = new ClassPathResource("cache/ehcache.xml");
    try (InputStream ec = encheConfig.getInputStream()) {
      CACHE_MANAGER = CacheManager.create(ec);
      log.info("初始化  EncheManager 结束");
    } catch (Exception e) {
      log.error("初始化  EncheManager失败：{}", e.getMessage());
    }
  }

  public Cache getCache(String cacheName) {
    try {
      if (CACHE_MANAGER == null) {
        log.error("EncheManager is NULL");
      } else {
        Cache  cache = CACHE_MANAGER.getCache(cacheName);
        return cache;
      }
      return null;
    } catch (net.sf.ehcache.CacheException e) {
      log.error("get cache error:{}",e.getMessage());
      return null;
    }
    
  }
  
  public void destroy() {
        try {
            CACHE_MANAGER.shutdown();
        } catch (Throwable t) {
          log.warn("Unable to cleanly shutdown implicitly created CacheManager instance.  Ignoring (shutting down)...", t);
        } finally {
          CACHE_MANAGER = null;
        }
    }

}
