package com.ljwm.gecko.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.admin.model.vo.TaxIncomeVo;
import com.ljwm.gecko.admin.model.vo.TaxOtherVo;
import com.ljwm.gecko.base.entity.TaxOtherReduce;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 报税数据其它扣除减免表 Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-10-16
 */
public interface AdminTaxOtherReduceMapper extends BaseMapper<TaxOtherReduce> {

  @Select("SELECT * FROM `t_tax_other_reduce` WHERE `TAX_ID` = #{taxId}")
  @ResultMap("TaxOtherVo")
  List<TaxOtherVo> findByTaxId(@Param("taxId") Long taxId);
}
