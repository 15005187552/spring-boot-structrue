package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.entity.CalTax;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 个税计算表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-10
 */
@Repository
public interface CalTaxMapper extends BaseMapper<CalTax> {

  @Select("SELECT COUNT(*) FROM t_cal_tax WHERE OLD_TAX> NEW_TAX")
  Long redPackage();
}
