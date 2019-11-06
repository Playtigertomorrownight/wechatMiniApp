package com.smallrain.wechat.common.manager.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 表单选择项数据源管理
 * @author wangying.dz3
 *
 */
@Service
public class SelectDataSourceManager {
  
  public List<Map<String,String>> getData(SelectDataType dataType){
    if(null == dataType) {
      return new ArrayList<>();
    }
    switch(dataType) {
      case SEX:  //性别
        return getSexDataSource();
      case DEFAULT: 
        return new ArrayList<>();
      default:
        return new ArrayList<>();
    }
  }
  
  @SuppressWarnings("serial")
  private List<Map<String,String>> getSexDataSource() {
    List<Map<String,String>> result = new ArrayList<>();
    result.add(new HashMap<String,String>() {{
      put("key","0");
      put("value","男");
    }});
    result.add(new HashMap<String,String>() {{
      put("key","1");
      put("value","女");
    }});
    return result;
  }

}
