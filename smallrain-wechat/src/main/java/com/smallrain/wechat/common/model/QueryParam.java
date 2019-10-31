package com.smallrain.wechat.common.model;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Arrays;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smallrain.wechat.utils.BaseUtils;

import lombok.Data;

/**
 * 通用查询参数
 * @author wangying.dz3
 *
 * @param <T>
 */
@Data
public class QueryParam<T> {
  
  /**
   * 默认分页页码
   */
  private static final int DEFAULT_PAGE_NUM = 1;
  /**
   * 默认分页大小
   */
  private static final int DEFAULT_PAGE_SIZE = 12;
  
  /**
   * 默认是否查询 count
   */
  private static final boolean DEFAULT_IS_SEARCHCOUNT = true;

  /**
   * mybatis-plus分页参数
   */
  private Page<T> page;
  
  /**
   * 当前页码
   */
  private int pageNum;
  
  /**
   * 每页条数
   */
  private int pageSize;
  
  private  Class<T> tClass;
  
  /**
   * 查询条件
   */
  private List<QueryCondition> conditions = null;
  
  public QueryParam(Class<T> tClass) {
    this.tClass = tClass;
    this.pageNum = DEFAULT_PAGE_NUM;
    this.pageSize = DEFAULT_PAGE_SIZE;
    this.page = new Page<>(pageNum, pageSize);
    page.setSearchCount(DEFAULT_IS_SEARCHCOUNT);
  }
  
  /**
   * 参数必须包含 ：pageNum，pageSize，conditions，searchCount,orderItem:{column:"",asc:false}等属性
   * @param param 参数 json 字符串
   */
  public QueryParam(String param,Class<T> tClass) {
     this.tClass = tClass;
     if(StringUtils.isNotBlank(param)||!"null".equalsIgnoreCase(param)) {
       try {
         JSONObject paramData = JSONObject.parseObject(param);
         this.pageNum = paramData.getIntValue("pageNum");
         this.pageNum = 0==this.pageNum?DEFAULT_PAGE_NUM:this.pageNum;
         this.pageSize = paramData.getIntValue("pageSize");
         this.pageSize = 0==this.pageSize?DEFAULT_PAGE_SIZE:this.pageSize;
         this.page = new Page<>(pageNum, pageSize);
         if(paramData.containsKey("searchCount")) {
           page.setSearchCount(paramData.getBooleanValue("searchCount"));
         }else {
           page.setSearchCount(DEFAULT_IS_SEARCHCOUNT);
         }
         if(paramData.containsKey("orderItem")) {
           page.addOrder(paramData.getJSONObject("orderItem").toJavaObject(OrderItem.class));
         }
         if(paramData.containsKey("conditions")) {
           this.conditions = paramData.getJSONArray("conditions").toJavaList(QueryCondition.class);
         }
       }catch(Exception e) {
         throw new RuntimeException("查询参数转换失败");
       }
     }else {
       this.pageNum = DEFAULT_PAGE_NUM;
       this.pageSize = DEFAULT_PAGE_SIZE;
       this.page = new Page<>(pageNum, pageSize);
       page.setSearchCount(DEFAULT_IS_SEARCHCOUNT);
     }
  }
  
  /**
   * 参数必须包含 ：pageNum，pageSize，entity，等属性
   * @param param 参数 Map<String,Object>
   */
  public QueryParam(Map<String,Object> param,Class<T> tClass) {
    this(JSON.toJSONString(param),tClass);
  }
  
  public QueryWrapper<T> getQueryWrapper() {
    QueryWrapper<T> wrapper = new QueryWrapper<T>();
    dealCondition(this.conditions,wrapper);
    return wrapper;
  }
  
  private void dealCondition(List<QueryCondition> conditionList,QueryWrapper<T> wrapper) {
    if(null!=conditionList && !conditionList.isEmpty()) {
      for(QueryCondition condition:conditions) {
        if(null!=condition && condition.check() && BaseUtils.checkBeanHasField(tClass, condition.getColum())) {
          String field = BaseUtils.humpToLine(condition.getColum());
          String operate = condition.getOperate();
          //等于
          if("eq".equalsIgnoreCase(operate) || "=".equalsIgnoreCase(operate) || "==".equalsIgnoreCase(operate)) {
            wrapper.eq(true,field,condition.getValue());
          }
          //不等于
          if("ne".equalsIgnoreCase(operate) || "!=".equalsIgnoreCase(operate) || "<>".equalsIgnoreCase(operate)) {
            wrapper.ne(true,field,condition.getValue());
          }
          //大于
          if("gt".equalsIgnoreCase(operate) || ">".equalsIgnoreCase(operate)) {
            wrapper.gt(true,field,condition.getValue());
          }
          //大于等于
          if("ge".equalsIgnoreCase(operate) || ">=".equalsIgnoreCase(operate)) {
            wrapper.ge(true,field,condition.getValue());
          }
          //小于
          if("lt".equalsIgnoreCase(operate) || "<".equalsIgnoreCase(operate)) {
            wrapper.lt(true,field,condition.getValue());
          }
          //小于等于
          if("le".equalsIgnoreCase(operate) || "<=".equalsIgnoreCase(operate)) {
            wrapper.le(true,field,condition.getValue());
          }
          //模糊查询
          if("like".equalsIgnoreCase(operate)) {
            wrapper.like(true,field,condition.getValue());
          }
          //模糊查询
          if("notLike".equalsIgnoreCase(operate)) {
            wrapper.notLike(true,field,condition.getValue());
          }
          //模糊查询
          if("likeLeft".equalsIgnoreCase(operate)) {
            wrapper.likeLeft(true,field,condition.getValue());
          }
          //模糊查询
          if("likeRight".equalsIgnoreCase(operate)) {
            wrapper.likeRight(true,field,condition.getValue());
          }
          //空判断
          if("isNull".equalsIgnoreCase(operate) || "Null".equalsIgnoreCase(operate)) {
            wrapper.isNull(true,field);
          }
          //空判断
          if("isNotNull".equalsIgnoreCase(operate) || "NotNull".equalsIgnoreCase(operate)) {
            wrapper.isNotNull(true,field);
          }
          //in 判断,之间用 -;,隔开
          if("in".equalsIgnoreCase(operate)) {
            String value = condition.getValue();
            if(StringUtils.isNotBlank(value)) {
              String [] values = value.split("[,|;|-]");
              if(null!=values&&values.length>1) {
                wrapper.in(true,field,Arrays.asList(values));
              }
            }
          }
          //notIn 判断,之间用 -;,隔开
          if("notIn".equalsIgnoreCase(operate)) {
            String value = condition.getValue();
            if(StringUtils.isNotBlank(value)) {
              String [] values = value.split("[,|;|-]");
              if(null!=values&&values.length>1) {
                wrapper.notIn(true,field,Arrays.asList(values));
              }
            }
          }
          //inSql 判断
          if("inSql".equalsIgnoreCase(operate)) {
            String value = condition.getValue();
            if(StringUtils.isNotBlank(value)) {
              wrapper.inSql(true,field,value);
            }
          }
          //groupBy 判断
          if("groupBy".equalsIgnoreCase(operate)) {
             wrapper.groupBy(true,field);
          }
          //在-- 到-- 之间用 -;,隔开
          if("between".equalsIgnoreCase(operate)) {
            String value = condition.getValue();
            if(StringUtils.isNotBlank(value)) {
              String [] values = value.split("[,|;|-]");
              if(null!=values&&values.length>1) {
                wrapper.between(true,field,values[0],values[1]);
              }
            }
          }
          //不在在-- 到-- 之间用 -;,隔开
          if("notBetween".equalsIgnoreCase(operate)) {
            String value = condition.getValue();
            if(StringUtils.isNotBlank(value)) {
              String [] values = value.split("[,|;|-]");
              if(null!=values&&values.length>1) {
                wrapper.notBetween(true,field,values[0],values[1]);
              }
            }
          }
          //deal or
          if(null!=condition.getOr() && !condition.getOr().isEmpty()) {
            wrapper.or(true, qw -> {
              dealCondition(condition.getOr(),qw);
              return qw;
             });
          }
        }
      }
    }
    
  }
  
  
}
