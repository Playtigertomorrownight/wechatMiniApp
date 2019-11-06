package com.smallrain.wechat.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.smallrain.wechat.common.manager.datasource.SelectDataType;

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
   * 是否在列表显示
   * @return
   */
  boolean show() default false;
  
  /**
   * 列表显示时的宽度
	 * @return
	 */
  int width() default 100;
  
  /**
   * 输入格式
   * @return
   */
  InputType type() default InputType.TEXT;
  
  /**
   * 校验规则 ，形如：type:string;message:必须为字符串,tigger:blur 参考:async-validator
   * @return
   */
  String [] validators() default {};
  
  /**
   * 选择框所需的选择项来源
   * @return
   */
  SelectDataType source() default SelectDataType.DEFAULT;
  
}
