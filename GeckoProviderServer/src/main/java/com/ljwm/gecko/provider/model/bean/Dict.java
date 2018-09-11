package com.ljwm.gecko.provider.model.bean;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * Wolverine Created by yunqisong on 2018-4-7.
 * FOR: Dict
 */
@Data
@Component
@Accessors(chain = true)
@ConfigurationProperties(prefix = "dict")
public class Dict {

  private String sysAdmin;

  private String initRole;

  private String[] builtInMenu;

  private String initPassword;
}
