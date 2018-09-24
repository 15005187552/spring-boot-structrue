package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 套接字连接信息表
 * </p>
 *
 * @author YunQiSong
 * @since 2018-09-24
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`im_socket_info`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "套接字连接信息表", subTypes = {SocketInfo.class})
public class SocketInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "对应表记录ID")
    @TableField("`TARGET_ID`")
    private Long targetId;

    @ApiModelProperty(value = "对应的记录表")
    @TableField("`TARGET_TABLE`")
    private Long targetTable;

    @ApiModelProperty(value = "Socket状态 （在线，离线）枚举")
    @TableField("`STATUS`")
    private Integer status;

    @ApiModelProperty(value = "渠道（PC,MEMBER,ADMIN,PRIVADER）枚举")
    @TableField("`CHANNEL`")
    private Integer channel;

    @ApiModelProperty(value = "当前IP")
    @TableField("`IP`")
    private String ip;

    @ApiModelProperty(value = "上次IP")
    @TableField("`LAST_IP`")
    private String lastIp;

    @ApiModelProperty(value = "连接时间")
    @TableField("`CONNECT_TIME`")
    private Date connectTime;

    @ApiModelProperty(value = "连接上下文对应的ID")
    @TableField("`CONNECT_CONTENXT_ID`")
    private String connectContenxtId;


    public static final String ID = "`ID`";

    public static final String TARGET_ID = "`TARGET_ID`";

    public static final String TARGET_TABLE = "`TARGET_TABLE`";

    public static final String STATUS = "`STATUS`";

    public static final String CHANNEL = "`CHANNEL`";

    public static final String IP = "`IP`";

    public static final String LAST_IP = "`LAST_IP`";

    public static final String CONNECT_TIME = "`CONNECT_TIME`";

    public static final String CONNECT_CONTENXT_ID = "`CONNECT_CONTENXT_ID`";

}
