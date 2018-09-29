package com.ljwm.gecko.client.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
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
  @ApiModelProperty(value = "报税数据收入")
  private Long id;

  @ApiModelProperty(value = "报税数据ID")
  private Long taxId;

  @JSONField(serializeUsing = IdToNameSerializer.IncomeTypeSerializer.class)
  @ApiModelProperty(value = "收入分类ID")
  private Long incomeTypeId;

  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "更新时间")
  private Date updateTime;

  @ApiModelProperty(value = "更新人")
  private Long updater;

  @ApiModelProperty(value = "收入金额")
  private String income;
}
