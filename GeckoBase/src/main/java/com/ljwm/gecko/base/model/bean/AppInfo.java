package com.ljwm.gecko.base.model.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@Accessors(chain = true)
@ConfigurationProperties(prefix = "appInfo")
public class AppInfo {

  @ApiModelProperty("图片包")
  private String pic;

  @ApiModelProperty("文件路径")
  private String filePath;

  @ApiModelProperty("上传前缀路径")
  private String webPath;

  @ApiModelProperty("后台图片访问")
  private String adminPath;
}
