package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.CompanySpecial;
import com.ljwm.gecko.base.serializer.PathToUrlSerializer;
import com.ljwm.gecko.base.serializer.StatusWithNameSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/5 15:13
 */
@Data
public class CompanyVo {

  @ApiModelProperty(value = "入驻企业")
  private Long id;

  @ApiModelProperty(value = "企业名称")
  private String name;

  @JSONField(serializeUsing = StatusWithNameSerializer.CompanyTypeSerializer.class)
  @ApiModelProperty(value = "企业类型")
  private Integer type;

  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @JSONField(serializeUsing = StatusWithNameSerializer.CompanyValidateSerializer.class)
  @ApiModelProperty(value = "认证状态：0：未认证，1：认证通过，2:认证失败")
  private Integer validateState;

  @ApiModelProperty(value = "是否禁用")
  private Integer disabled;

  @ApiModelProperty(value = "手机号码")
  private String phoneNum;

  @ApiModelProperty(value = "企业纳税代码")
  private String code;

  @ApiModelProperty(value = "创建人ID")
  private Long createrId;

  @ApiModelProperty(value = "证照图片路径")
  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String picPath;

  @ApiModelProperty(value = "审核人ID")
  private Long validatorId;

  @ApiModelProperty(value = "审核时间")
  private Date validateTime;

  @ApiModelProperty(value = "更新时间")
  private Date updateTime;

  @ApiModelProperty(value = "审核意见")
  private String validateText;

  @JSONField(serializeUsing = StatusWithNameSerializer.LocationSerializer.class)
  @ApiModelProperty(value = "省级代码")
  private String provCode;

  @JSONField(serializeUsing = StatusWithNameSerializer.LocationSerializer.class)
  @ApiModelProperty(value = "市级代码")
  private String cityCode;

  @JSONField(serializeUsing = StatusWithNameSerializer.LocationSerializer.class)
  @ApiModelProperty(value = "区级代码")
  private String areaCode;

  @ApiModelProperty(value = "详细地址")
  private String address;

  List<CompanySpecialVo> companySpecialList;
}
