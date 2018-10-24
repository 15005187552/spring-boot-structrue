package com.ljwm.gecko.base.model.vo.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ljwm.gecko.base.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Author: xixil
 * Date: 2018/10/23 16:42
 * RUA
 */

@Data
public class AdminMemberVO {

  private Long id;

  private String regMobile;

  private String nickName;

  private String name;

  private String avatarPath;

  private String memberIdcard;

  private Date createTime;

  private Boolean disabled;

  private String picFront;

  private String picBack;

  private String picPassport;
}
