package com.smallrain.wechat.models.dto;

import com.smallrain.wechat.common.annotations.InputType;
import com.smallrain.wechat.common.annotations.ModelEditField;

import lombok.Data;

@Data
public class MenuDto {

	@ModelEditField(name="名称",show=true,validators= {"required:true,message:菜单名称不能为空,tigger:blur"})
	private String name;
	
	@ModelEditField(name="显示文本",show=true,validators= {"required:true,message:菜单显示文本不能为空,tigger:blur"})
	private String text;
	
	@ModelEditField(name="类型",edit=false,show=true,validators= {"required:true,message:菜单类型不能为空,tigger:blur"})
    private Integer type;

	@ModelEditField(name="地址",show=true)
    private String href;

	@ModelEditField(name="图标",type=InputType.IMAGE)
    private String icon;
	
	@ModelEditField(name="排序",type=InputType.NUMBER)
    private Integer sort;

	@ModelEditField(name="权限",show=true)
    private String permission;

	@ModelEditField(name="上级菜单",show=true,type=InputType.CHECKBOX)
	private String parentId;
	
	@ModelEditField(name="样式")
    private String style;
	
}
