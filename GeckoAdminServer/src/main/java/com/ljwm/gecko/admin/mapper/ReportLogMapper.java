package com.ljwm.gecko.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.admin.model.vo.ReportLogVo;
import com.ljwm.gecko.base.entity.NaturalPersonBackup;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 自然人纳税基本信息表 Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-10-16
 */
public interface ReportLogMapper extends BaseMapper<NaturalPersonBackup> {

  @Select("SELECT * FROM `t_natural_person_backup`")
  @ResultMap("ReportLogVo")
  List<ReportLogVo> findReportLog();
}
