package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.serializer.IdToNameSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Janiffy
 * @date 2018/9/28 21:01
 */
@Data
public class TaxSpecialVo {

  @ApiModelProperty(value = "报税数据专项扣除表")
  private Long id;

  @ApiModelProperty(value = "报税数据ID")
  private Long taxId;

  @JSONField(serializeUsing = IdToNameSerializer.SpecialDeductionSerializer.class)
  @ApiModelProperty(value = "专项扣除分类ID")
  private Long specialDeduId;

  @ApiModelProperty(value = "个人缴纳金额")
  private String personalMoney;

  @ApiModelProperty(value = "单位纳税金额")
  private String companyMoney;

  @ApiModelProperty(value = "个人缴纳比例")
  private String personalPercent;

  @ApiModelProperty(value = "单位缴纳比例")
  private String companyPercent;

  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "更新时间")
  private Date updateTime;

  @ApiModelProperty(value = "更新人")
  private Long updater;

}
