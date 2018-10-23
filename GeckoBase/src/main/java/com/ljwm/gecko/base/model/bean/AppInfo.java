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

  @ApiModelProperty("文件上传磁盘路径")
  private String filePath;

  @ApiModelProperty("上传文件访问URL前缀")
  private String webPath;
}
