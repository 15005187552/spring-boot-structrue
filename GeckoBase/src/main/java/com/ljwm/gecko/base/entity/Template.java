package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 模板表
 * </p>
 *
 * @author Levis
 * @since 2018-09-25
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_template`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "模板表", subTypes = {Template.class})
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "自增ID")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "公司ID")
    @TableField("`COMPANY_ID`")
    private Long companyId;

    @ApiModelProperty(value = "模板表头ID")
    @TableField("`ATTRIBUTE_ID`")
    private Long attributeId;

    @ApiModelProperty(value = "顺序")
    @TableField("`SORT`")
    private Integer sort;


    public static final String ID = "`ID`";

    public static final String COMPANY_ID = "`COMPANY_ID`";

    public static final String ATTRIBUTE_ID = "`ATTRIBUTE_ID`";

    public static final String SORT = "`SORT`";

}
