package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.entity.TaxSpecialAdd;
import com.ljwm.gecko.base.model.vo.TaxSpecialAddVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 报税数据专项附加扣除表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-06
 */
@Repository
public interface TaxSpecialAddMapper extends BaseMapper<TaxSpecialAdd> {

  @Select("SELECT c.ID, c.`NAME`, b.TAX_MONEY, b.TAX_DOC_PATH\n" +
    "FROM t_tax a, t_tax_special_add b, t_add_special c\n" +
    "WHERE a.DECLARE_TIME = #{declareTime} AND MEMBER_ID = #{memberId} AND DECLARE_TYPE = #{declareType}\n" +
    "AND a.ID = b.TAX_ID  \n" +
    "AND b.SPECIAL_ADD_ID = c.ID\n" +
    "ORDER BY c.SORT;")
  @ResultMap("BaseMap")
  List<TaxSpecialAddVo> selectSpecialAdd(@Param("memberId") Long memberId, @Param("declareTime")String declareTime, @Param("declareType")Integer declareType);
}
