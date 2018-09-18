package com.ljwm.gecko.client.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by yunqisong on 2018/3/21/021.
 */
@Data
@Accessors(chain = true)
public class PaymentBaseVo {

  @ApiModelProperty("时间戳")
  private String timeStamp;

  @ApiModelProperty("参数字符串")
  private String nonceStr;

  @ApiModelProperty("package")
  @JSONField(name = "package") // package是JAVA关键字不可作为变量，只能转
  private String packageName;

  @ApiModelProperty("签名类型")
  private String signType;

  @ApiModelProperty("签名值")
  private String paySign;

}
