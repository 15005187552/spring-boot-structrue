package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.entity.TaxIncome;
import com.ljwm.gecko.base.model.vo.TaxIncomeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 报税数据收入表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-05
 */
@Repository
public interface TaxIncomeMapper extends BaseMapper<TaxIncome> {
  @Select("SELECT b.ID as INCOME_ID, " +
    "a.ID as TAX_ID" +
    "c.ID as INCOME_TYPE_ID" +
    "c.`NAME`, " +
    "c.SORT, " +
    "c.IS_NEED_ENTER, " +
    "c.P_ID, " +
    "c.LEVEL, " +
    "b.CREATE_TIME, " +
    "b.UPDATE_TIME, " +
    "b.UPDATER, " +
    "b.INCOME\n" +
    "FROM t_tax a," +
    " t_tax_income b," +
    " t_income_type c\n" +
    "WHERE a.DECLARE_TIME = #{declareTime} AND MEMBER_ID = #{memberId} AND DECLARE_TYPE = #{declareType}\n" +
    "AND a.ID = b.TAX_ID  \n" +
    "AND c.ID = b.INCOME_TYPE_ID")
  @ResultMap("BaseMap")
  List<TaxIncomeVo> selectIncome(@Param("memberId") Long memberId, @Param("declareTime")String declareTime, @Param("declareType")Integer declareType);
}
