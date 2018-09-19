package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.CompanySpecial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-15
 */
@Repository
public interface CompanySpecialMapper extends BaseMapper<CompanySpecial> {

  @Select("SELECT a.PERSON_PER FROM t_company_special a, t_special_deduction b WHERE a.SPECIAL_ID = b.ID AND b.`NAME` LIKE \"%#{name}%\"")
  BigDecimal selectByName(String name);
}
