package com.ljwm.gecko.admin.model.form;

import cn.hutool.core.date.DateTime;
import com.ljwm.bootbase.dto.CommonQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Author: xixil
 * Date: 2018/10/16 15:17
 * RUA
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ReportLogQuery  extends CommonQuery{

    private Long companyId;

    private Integer declareType;

    private Date declareTime;
}
