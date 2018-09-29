package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ljwm.gecko.base.serializer.IdToNameSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Janiffy
 * @date 2018/9/28 20:58
 */
@Data
public class TaxIncomeVo {
  @ApiModelProperty(value = "报税数据收入ID")
  private Long incomeId;

  @ApiModelProperty(value = "报税数据ID")
  private Long taxId;

  @JSONField(serializeUsing = IdToNameSerializer.IncomeTypeSerializer.class)
  @ApiModelProperty(value = "收入分类ID")
  private Long incomeTypeId;

  @ApiModelProperty(value = "分类名称")
  private String name;

  @ApiModelProperty(value = "排序")
  private String sort;

  @ApiModelProperty(value = "是否前台输入 0-不需要 1-需要")
  private Integer isNeedEnter;

  @ApiModelProperty(value = "父分类ID")
  private Long pId;

  @ApiModelProperty(value = "级别 0-一级 1-二级")
  @TableField("`LEVEL`")
  private Integer level;

  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "更新时间")
  private Date updateTime;

  @ApiModelProperty(value = "更新人")
  private Long updater;

  @ApiModelProperty(value = "收入金额, 参数改为value")
  @JSONField(name = "value")
  private String income;
}
