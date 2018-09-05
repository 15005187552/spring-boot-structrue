package com.ljwm.gecko.client.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Janiffy
 * @date 2018/9/4 16:25
 */
@Data
@Component
@Accessors(chain = true)
@ConfigurationProperties(prefix = "appInfo")
public class ApplicationInfo {

  @ApiModelProperty("指向的本地路径")
  private String webRoot;

  @ApiModelProperty("访问的服务器ip端口号或者域名")
  private String webPath;

  @ApiModelProperty("文件路径")
  private String filePath;

  @ApiModelProperty("公司文件路径")
  private String companyFile;

  @ApiModelProperty("图片缓存路径前缀")
  private String cachePath;

  @ApiModelProperty("图片缓存路径前缀")
  private String personFile;
}
