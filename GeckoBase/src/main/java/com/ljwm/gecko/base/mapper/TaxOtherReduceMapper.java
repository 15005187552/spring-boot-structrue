package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.entity.TaxOtherReduce;
import com.ljwm.gecko.base.model.vo.TaxOtherReduceVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 报税数据其它扣除减免表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-06
 */
@Repository
public interface TaxOtherReduceMapper extends BaseMapper<TaxOtherReduce> {

  @Select("SELECT c.ID, " +
    "c.`NAME`, " +
    "b.TAX_MONEY, " +
    "b.TAX_DOC_PATH\n"+
           "FROM t_tax a, t_tax_other_reduce b, t_other_reduce c\n"+
           "WHERE a.DECLARE_TIME = #{declareTime} AND MEMBER_ID = #{memberId} AND DECLARE_TYPE = #{declareType}\n"+
           "AND a.ID = b.TAX_ID  \n"+
           "AND b.OTHER_REDUCE_ID = c.ID ORDER BY c.SORT")
  @ResultMap("BaseMap")
  List<TaxOtherReduceVo> selectOther(@Param("memberId") Long memberId, @Param("declareTime")String declareTime, @Param("declareType")Integer declareType);


  @Select("SELECT TAX_MONEY FROM t_tax_other_reduce WHERE TAX_ID = (SELECT ID FROM t_tax WHERE MEMBER_ID = #{memberId} AND DECLARE_TIME = #{declareTime}) \n" +
    "AND OTHER_REDUCE_ID = (SELECT ID FROM t_other_reduce WHERE `NAME` LIKE \"%${name}%\"\t)")
  BigDecimal selectTaxMoney(@Param("memberId") Long memberId, @Param("name") String name, @Param("declareTime") String declareTime);
}
