package com.ljwm.gecko.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.admin.model.vo.TaxIncomeVo;
import com.ljwm.gecko.base.entity.TaxIncome;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 报税数据收入表 Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-10-16
 */
public interface AdminTaxIncomeMapper extends BaseMapper<TaxIncome> {

  @Select("SELECT * FROM `t_tax_income` WHERE `TAX_ID` = #{taxId}")
  @ResultMap("TaxIncomeVo")
  List<TaxIncomeVo> findByTaxId(@Param("taxId") Long taxId);
}
