package com.smallrain.wechat.models.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.smallrain.wechat.utils.BaseUtils;

/**
 * 数据转对象工具类，用于生成实体类信息
 * @author wangying.dz3
 *
 */
public class DtoUtils {
  
  private static Map<Class<?>,String> INIT_MODELS = new HashMap<>();
  
  private static JSONObject MODELS = new JSONObject();
  
  static {
    INIT_MODELS.put(UserDto.class, "用户");
    INIT_MODELS.put(MenuDto.class, "菜单");
    
    //遍历处理
    Set<Class<?>> keys = INIT_MODELS.keySet();
    for(Class<?> key:keys) {
      if(null==key) continue;
      JSONObject obj = new JSONObject();
      String modelName = INIT_MODELS.get(key);
      obj.put("modelName", modelName);
      obj.put("beanInfo", BaseUtils.resolveEntity(key));
      MODELS.put(key.getSimpleName().toUpperCase(), obj);
    }
  }

  public static JSONObject getModelInfo(String modelName) {
    if(StringUtils.isBlank(modelName)) return null;
    return MODELS.getJSONObject((modelName+"DTO").toUpperCase());
  }
}
