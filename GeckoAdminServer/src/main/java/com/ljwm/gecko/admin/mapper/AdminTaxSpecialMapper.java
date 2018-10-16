package com.ljwm.gecko.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.admin.model.vo.TaxSpecialAddVo;
import com.ljwm.gecko.admin.model.vo.TaxSpecialVo;
import com.ljwm.gecko.base.entity.TaxSpecial;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 报税数据专项扣除表 Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-10-16
 */
public interface AdminTaxSpecialMapper extends BaseMapper<TaxSpecial> {

  @Select("SELECT * FROM `t_tax_special` WHERE `TAX_ID` = #{taxId}")
  @ResultMap("TaxSpecialVo")
  List<TaxSpecialVo> findByTaxId(@Param("taxId") Long taxId);
}
