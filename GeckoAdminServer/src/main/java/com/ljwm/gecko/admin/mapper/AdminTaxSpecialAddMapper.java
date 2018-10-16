package com.ljwm.gecko.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.admin.model.vo.TaxOtherVo;
import com.ljwm.gecko.admin.model.vo.TaxSpecialAddVo;
import com.ljwm.gecko.base.entity.TaxSpecialAdd;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 报税数据专项附加扣除表 Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-10-16
 */
public interface AdminTaxSpecialAddMapper extends BaseMapper<TaxSpecialAdd> {

  @Select("SELECT * FROM `t_tax_special_add` WHERE `TAX_ID` = #{taxId}")
  @ResultMap("TaxSpecialAddVo")
  List<TaxSpecialAddVo> findByTaxId(@Param("taxId") Long taxId);
}
