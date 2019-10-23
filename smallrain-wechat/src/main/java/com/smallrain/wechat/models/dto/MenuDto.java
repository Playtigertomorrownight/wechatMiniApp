package com.smallrain.wechat.models.dto;

import com.smallrain.wechat.common.annotations.InputType;
import com.smallrain.wechat.common.annotations.ModelEditField;

import lombok.Data;

@Data
public class MenuDto {

	@ModelEditField(name="名称",required=true,show=true)
	private String name;
	
	@ModelEditField(name="内容",required=true,show=true)
	private String text;
	
	@ModelEditField(name="类型",required=true,edit=false,show=true)
    private Integer type;

	@ModelEditField(name="地址",show=true)
    private String href;

	@ModelEditField(name="图标",type=InputType.IMAGE)
    private String icon;
	
	@ModelEditField(name="排序",type=InputType.NUMBER)
    private Integer sort;

	@ModelEditField(name="权限",show=true)
    private String permission;

	@ModelEditField(name="上级菜单ID",show=true,type=InputType.CHECKBOX)
	private String parentId;
	
	@ModelEditField(name="样式")
    private String style;
	
}
