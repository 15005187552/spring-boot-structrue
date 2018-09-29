package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.serializer.IdToNameSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Janiffy
 * @date 2018/9/28 21:02
 */
@Data
public class TaxSpecialAddVo {
  @ApiModelProperty(value = "报税数据专项附加扣除")
  private Long id;

  @ApiModelProperty(value = "报税数据ID")
  private Long taxId;

  @JSONField(serializeUsing = IdToNameSerializer.SpecialAddSerializer.class)
  @ApiModelProperty(value = "专项附加扣除分类ID")
  private Long specialAddId;

  @ApiModelProperty(value = "个人缴纳金额,参数改为value")
  @JSONField(name = "value")
  private String taxMoney;

  @ApiModelProperty(value = "缴纳证明附件路径")
  private String taxDocPath;

  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "更新时间")
  private Date updateTime;

  @ApiModelProperty(value = "更新人")
  private Long updater;

}
