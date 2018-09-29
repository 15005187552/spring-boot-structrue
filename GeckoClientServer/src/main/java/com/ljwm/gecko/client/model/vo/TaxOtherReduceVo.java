package com.ljwm.gecko.client.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.serializer.IdToNameSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Janiffy
 * @date 2018/9/28 20:59
 */
@Data
public class TaxOtherReduceVo {

  @ApiModelProperty(value = "报税数据其它扣除减免")
  private Long id;

  @ApiModelProperty(value = "报税数据ID")
  private Long taxId;

  @JSONField(serializeUsing = IdToNameSerializer.OtherReduceSerializer.class)
  @ApiModelProperty(value = "其它扣除减免分类ID")
  private Long otherReduceId;

  @ApiModelProperty(value = "个人缴纳金额,参数改为value")
  @JSONField(name = "value")
  private String taxMoney;

  @ApiModelProperty(value = "缴纳证明路径")
  private String taxDocPath;

  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "更新时间")
  private Date updateTime;

  @ApiModelProperty(value = "更新人")
  private Long updater;
}
