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
 * 手机验证码表
 * </p>
 *
 * @author Levis
 * @since 2018-10-18
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_mobile_code`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "手机验证码表", subTypes = {MobileCode.class})
public class MobileCode implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "ID")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "验证码值")
    @TableField("`CODE`")
    private String code;

    @ApiModelProperty(value = "短信模板类型")
    @TableField("`TYPE`")
    private Integer type;

    @ApiModelProperty(value = "手机号")
    @TableField("`MOBILE`")
    private String mobile;

    @ApiModelProperty(value = "来源IP")
    @TableField("`FROM_IP`")
    private String fromIp;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Long createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`UPDATE_TIME`")
    private Long updateTime;

    @ApiModelProperty(value = "当日重试次数")
    @TableField("`DAY_INDEX`")
    private Integer dayIndex;


    public static final String ID = "`ID`";

    public static final String CODE = "`CODE`";

    public static final String TYPE = "`TYPE`";

    public static final String MOBILE = "`MOBILE`";

    public static final String FROM_IP = "`FROM_IP`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

    public static final String DAY_INDEX = "`DAY_INDEX`";

}
