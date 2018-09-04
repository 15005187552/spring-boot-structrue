package com.ljwm.gecko.base.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 各城市缴纳项目扣除项表
 * </p>
 *
 * @author xixil
 * @since 2018-09-03
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_city_item`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "各城市缴纳项目扣除项表", subTypes = {CityItem.class})
public class CityItem implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "项目ID")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "项目类型")
    @TableField("`ITEM_TYPE`")
    private Long itemType;

    @ApiModelProperty(value = "地区code")
    @TableField("`REGION_CODE`")
    private Integer regionCode;

    @ApiModelProperty(value = "上限")
    @TableField("`UPPER_LIMIT`")
    private BigDecimal upperLimit;

    @ApiModelProperty(value = "下限")
    @TableField("`LOWER_LIMIT`")
    private BigDecimal lowerLimit;

    @ApiModelProperty(value = "单位比例")
    @TableField("`COMPANY_PER`")
    private BigDecimal companyPer;

    @ApiModelProperty(value = "个人比例")
    @TableField("`PERSON_PER`")
    private BigDecimal personPer;

    @ApiModelProperty(value = "比例类型 0-百分比 1-金额")
    @TableField("`PER_TYPE`")
    private Integer perType;

    @ApiModelProperty(value = "排序")
    @TableField("`SORT`")
    private Integer sort;
    @TableField("`CREATE_TIME`")
    private Date createTime;
    @TableField("`CREATOR`")
    private Long creator;
    @TableField("`UPDATE_TIME`")
    private Date updateTime;

    @ApiModelProperty(value = "更新人ID")
    @TableField("`UPDATER_ID`")
    private Long updaterId;


    public static final String ID = "`ID`";

    public static final String ITEM_TYPE = "`ITEM_TYPE`";

    public static final String REGION_CODE = "`REGION_CODE`";

    public static final String UPPER_LIMIT = "`UPPER_LIMIT`";

    public static final String LOWER_LIMIT = "`LOWER_LIMIT`";

    public static final String COMPANY_PER = "`COMPANY_PER`";

    public static final String PERSON_PER = "`PERSON_PER`";

    public static final String PER_TYPE = "`PER_TYPE`";

    public static final String SORT = "`SORT`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String CREATOR = "`CREATOR`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

    public static final String UPDATER_ID = "`UPDATER_ID`";

}
