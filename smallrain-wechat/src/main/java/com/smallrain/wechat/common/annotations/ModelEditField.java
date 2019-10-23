package com.smallrain.wechat.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记需要编辑的实体属性
 * @author wangying.dz3
 *
 */
//ElementType.FIELD：注解放在属性上
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)// 注解在运行时有效
public @interface ModelEditField {
  
  /**
   * 属性名称
   * @return
   */
  String name() default "实体属性";
  
  /**
   * 是否在添加时需要
   * @return
   */
  boolean add() default true;
  
  /**
   * 是否在修改时需要
   * @return
   */
  boolean edit() default true;
  
  /**
   * 是否必需
   * @return
   */
  boolean required() default false;
  
  /**
   * 长度限制
   * @return
   */
  int size() default 64;
  
  /**
   * 正则表达式
   * @return
   */
  String pattern() default "";
  
  /**
   * 输入格式
   * @return
   */
  InputType type() default InputType.TEXT;
  
}
