package com.ljwm.gecko.client.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.serializer.StatusWithNameSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Janiffy
 * @date 2018/9/5 15:13
 */
@Data
public class CompanyVo {

  @ApiModelProperty(value = "企业ID")
  private Long id;

  @ApiModelProperty(value = "企业名称")
  private String name;

  @ApiModelProperty(value = "企业类型")
  @JSONField(serializeUsing = StatusWithNameSerializer.CompanyTypeSerializer.class)
  private Integer type;

  @ApiModelProperty(value = "企业纳税代码")
  private String code;

  @ApiModelProperty(value = "手机号码")
  @JSONField(name = "regMobile")
  private String phoneNum;

  @ApiModelProperty(value = "认证状态：0：未认证，1：认证通过，2:认证失败")
  @JSONField(serializeUsing = StatusWithNameSerializer.CompanyValidateSerializer.class)
  private Integer validateState;

  @ApiModelProperty(value = "创建人ID")
  private Long createrId;

  @ApiModelProperty(value = "证照图片文件")
  private String filePath;

  @JSONField(serializeUsing = StatusWithNameSerializer.LocationSerializer.class)
  @ApiModelProperty(value = "省")
  private String provCode;

  @JSONField(serializeUsing = StatusWithNameSerializer.LocationSerializer.class)
  @ApiModelProperty(value = "市")
  private String cityCode;

  @JSONField(serializeUsing = StatusWithNameSerializer.LocationSerializer.class)
  @ApiModelProperty(value = "区")
  private String areaCode;

  @ApiModelProperty(value = "详细地址")
  private String address;

  @ApiModelProperty(value = "公司养老保险比例")
  private BigDecimal comPension;

  @ApiModelProperty(value = "公司医疗保险比例")
  private BigDecimal comMedical;

  @ApiModelProperty(value = "公司失业保险比例")
  private BigDecimal comUnemploy;

  @ApiModelProperty(value = "公司工伤保险比例")
  private BigDecimal comInjury;

  @ApiModelProperty(value = "公司生育保险比例")
  private BigDecimal comBirth;

  @ApiModelProperty(value = "个人养老保险比例")
  private BigDecimal personPension;

  @ApiModelProperty(value = "个人医疗保险比例")
  private BigDecimal personMedical;

  @ApiModelProperty(value = "个人失业保险比例")
  private BigDecimal personUnemploy;

  @ApiModelProperty(value = "个人工伤比例")
  private BigDecimal peesonInjury;

  @ApiModelProperty(value = "个人生育比例")
  private BigDecimal personBirth;

  @ApiModelProperty(value = "公司公积金比例")
  private BigDecimal fundCom;

  @ApiModelProperty(value = "个人公积金比例")
  private BigDecimal fundPerson;
}
