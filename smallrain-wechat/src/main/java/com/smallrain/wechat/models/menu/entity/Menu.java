package com.smallrain.wechat.models.menu.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangying
 * @since 2019-09-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Menu对象", description="")
public class Menu extends Model<Menu> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String parentId;

    private String name;

    private String style;

    private String href;

    private String icon;

    private String text;

    private Integer type;

    private String permission;

    private Integer sort;

    private Integer status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
