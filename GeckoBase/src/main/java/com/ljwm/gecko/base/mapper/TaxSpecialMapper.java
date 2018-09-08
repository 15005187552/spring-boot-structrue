package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.entity.TaxSpecial;
import com.ljwm.gecko.base.model.vo.TaxSpecialVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 报税数据专项扣除表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-06
 */
@Repository
public interface TaxSpecialMapper extends BaseMapper<TaxSpecial> {

  @Select("SELECT c.ID, c.`NAME`, b.PERSONAL_MONEY, b.PERSONAL_PERCENT, b.COMPANY_MONEY, b.COMPANY_PERCENT\n" +
    "FROM t_tax a, t_tax_special b, t_special_deduction c\n" +
    "WHERE a.DECLARE_TIME = #{declareTime} AND MEMBER_ID = #{memberId} AND DECLARE_TYPE = #{declareType}\n" +
    "AND a.ID = b.TAX_ID  \n" +
    "AND b.SPECIAL_DEDU_ID = c.ID\n" +
    "ORDER BY c.SORT;")
  @ResultMap("BaseMap")
  List<TaxSpecialVo> selectSpecial(@Param("memberId") Long memberId, @Param("declareTime")String declareTime, @Param("declareType")Integer declareType);
}
