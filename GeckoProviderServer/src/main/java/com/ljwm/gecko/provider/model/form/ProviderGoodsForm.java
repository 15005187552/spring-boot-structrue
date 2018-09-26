package com.ljwm.gecko.provider.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProviderGoodsForm {

  @ApiModelProperty(value = "服务商品id")
  private Long id;

  @ApiModelProperty(value = "服务id,对应t_service表的主键")
  private Integer serviceId;

  @ApiModelProperty(value = "服务商id")
  private Long providerId;

  @ApiModelProperty(value = "商品名称")
  private String name;

  @ApiModelProperty(value = "商品副标题")
  private String subtitle;

  @ApiModelProperty(value = "产品主图,url相对地址")
  private String mainImage;

  @ApiModelProperty(value = "图片地址,json格式,扩展用")
  private List<String> subImagesList;

  @ApiModelProperty(value = "商品详情")
  private String detail;

  @ApiModelProperty(value = "价格,单位-元保留两位小数")
  private BigDecimal price;

  @ApiModelProperty(value = "是否需要定价   0 不需要   1  需要")
  private Integer isConfirmPrice;

}
