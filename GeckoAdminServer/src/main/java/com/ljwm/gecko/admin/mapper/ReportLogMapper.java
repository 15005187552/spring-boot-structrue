package com.ljwm.gecko.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.admin.model.vo.ReportLogVo;
import com.ljwm.gecko.base.entity.NaturalPersonBackup;
import com.ljwm.gecko.base.entity.Notice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 自然人纳税基本信息表 Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-10-16
 */
public interface ReportLogMapper extends BaseMapper<NaturalPersonBackup> {

  @Select({
    "<script>",
    "SELECT * FROM `t_natural_person_backup`",
    "<where>",
    " 1=1 ",
    "<if test=\"params.text != null and params.text != ''\">",
    " AND( ",
    NaturalPersonBackup.NAME + " LIKE  CONCAT('%',#{params.text},'%') ",
    " OR " + NaturalPersonBackup.CERTIFICATE + " LIKE CONCAT('%',#{params.text},'%') ",
    " ) ",
    "</if>",
    "<if test=\"params.declareTime != null and params.declareTime != ''\">",
    " AND " + NaturalPersonBackup.TAX_ID + " IN (SELECT `ID` FROM `t_tax` WHERE `DECLARE_TIME` = #{params.declareTime} )",
    "</if>",
    "<if test=\"params.declareType != null\">",
    " AND " + NaturalPersonBackup.TAX_ID + " IN (SELECT `ID` FROM `t_tax` WHERE `DECLARE_TYPE` = #{params.declareType} )",
    "</if>",
    "<if test=\"params.companyId != null\">",
    " AND " + NaturalPersonBackup.COMPANY_ID + " = #{params.companyId} ",
    "</if>",
    "</where>",
    "</script>"
  })
  @ResultMap("ReportLogVo")
  List<ReportLogVo> findReportLog(Page<ReportLogVo> page,@Param("params") Map map);
}
