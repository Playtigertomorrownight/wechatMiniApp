package com.smallrain.wechat.models.role.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangying
 * @since 2019-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysRole对象", description="")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

    private Integer deleteFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
