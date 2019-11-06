package com.smallrain.wechat.models.dto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smallrain.wechat.common.annotations.ModelEditField;
import com.smallrain.wechat.common.manager.datasource.SelectDataSourceManager;
import com.smallrain.wechat.common.manager.datasource.SelectDataType;
import com.smallrain.wechat.utils.BaseUtils;
import com.smallrain.wechat.utils.SpringUtil;

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
      obj.put("beanInfo", resolveEntity(key));
      MODELS.put(key.getSimpleName().toUpperCase(), obj);
    }
  }

  public static JSONObject getModelInfo(String modelName) {
    if(StringUtils.isBlank(modelName)) return null;
    return MODELS.getJSONObject((modelName+"DTO").toUpperCase());
  }
  
  
  private static JSONObject resolveEntity(Class<?> clazz) {
    JSONObject result = new JSONObject();
    if (null == clazz)
      return result;
    List<Field> fieldsList = new ArrayList<>();
    Field[] fields = clazz.getDeclaredFields();
    fieldsList.addAll(Arrays.asList(fields));
    Class<?> superClazz = clazz.getSuperclass();
    if (superClazz != null) {
      Field[] superFields = superClazz.getDeclaredFields();
      fieldsList.addAll(Arrays.asList(superFields));
    }
    List<String> names = new ArrayList<>();
    for (Field field : fieldsList) {
      // 设置访问对象权限，保证对私有属性的访问
      field.setAccessible(true);
      ModelEditField mef = field.getAnnotation(ModelEditField.class);
      if (null == mef)
        continue; // 没有该注解，跳过
      String name = field.getName();
      names.add(field.getName());
      JSONObject mefJson = JSONObject.parseObject(JSON.toJSONString(mef));
      //处理校验规则
      if(StringUtils.isNoneBlank(mef.validators())) {
        JSONArray validatorsJson = new JSONArray();
        String [] validators = mef.validators();
        for(String validator:validators) {
          JSONObject json = new JSONObject();
          if(StringUtils.isBlank(validator)) continue;
          String [] validatorItems = validator.split(",");
          if(null!=validatorItems) {
            for(String validatorItem:validatorItems) {
              if(!validatorItem.contains(":")) continue;
              String [] items = validatorItem.split(":");
              if(null==items || items.length == 0) continue;
              if(items.length == 1) {
                json.put(validatorItem.split(":")[0], "");
              }else {
                json.put(validatorItem.split(":")[0], BaseUtils.transBoolean(validatorItem.split(":")[1])?true:validatorItem.split(":")[1]);
              }
            }
          }
          validatorsJson.add(json);
        }
        mefJson.put("validators", validatorsJson);
      }
      //处理选择数据
      if(SelectDataType.DEFAULT != mef.source()) {
        SelectDataSourceManager selectDataSourceManager = SpringUtil.getBean(SelectDataSourceManager.class);
        mefJson.put("dataList", selectDataSourceManager.getData(mef.source()));
      }
      result.put(name, mefJson);
    }
    result.put("FIELD_ITEM_LIST", names);
    return result;
  }

  
}
