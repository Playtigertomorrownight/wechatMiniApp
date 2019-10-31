package com.smallrain.wechat.common.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

/**
 * 查询条件
 * 
 * @author wangying.dz3
 *
 */
@Data
public class QueryCondition {

  /**
   * 字段名
   */
  private String colum;

  /**
   * 字段值
   */
  private String value;

  /**
   * 操作
   */
  private String operate;

  /**
   * 该条件后要跟的Or条件
   */
  private List<QueryCondition> or;
  
  public boolean check() {
    return StringUtils.isNotBlank(colum) && StringUtils.isNotBlank(operate);
  }

}
