package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 企业员工表
 * </p>
 *
 * @author Levis
 * @since 2018-09-11
 */
@Data
@SuppressWarnings("ALL")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("`t_company_user`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "企业员工表", subTypes = {CompanyUser.class})
public class CompanyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属企业ID")
    @TableField("`COMPANY_ID`")
    private Long companyId;

    @ApiModelProperty(value = "会员ID")
    @TableField("`MEMBER_ID`")
    private Long memberId;

    @ApiModelProperty(value = "角色代码")
    @TableField("`ROLES_CODE`")
    private String rolesCode;

    @ApiModelProperty(value = "加入时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`UPDATE_TIME`")
    private Date updateTime;

    @ApiModelProperty(value = "是否离职")
    @TableField("`DISABLED`")
    private Integer disabled;

    @ApiModelProperty(value = "是否激活")
    @TableField("`ACTIVATED`")
    private Integer activated;


    public static final String ID = "`ID`";

    public static final String COMPANY_ID = "`COMPANY_ID`";

    public static final String MEMBER_ID = "`MEMBER_ID`";

    public static final String ROLES_CODE = "`ROLES_CODE`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

    public static final String DISABLED = "`DISABLED`";

    public static final String ACTIVATED = "`ACTIVATED`";

}
