package com.ljwm.gecko.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.admin.model.vo.TaxVo;
import com.ljwm.gecko.base.entity.Tax;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 考勤报税数据主表 Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-10-16
 */
public interface AdminTaxMapper extends BaseMapper<Tax> {

  @Select("SELECT * FROM `t_tax` WHERE `ID` = #{id}")
  @ResultMap("TaxVo")
  TaxVo getTaxById(@Param("id") Long id);
}
