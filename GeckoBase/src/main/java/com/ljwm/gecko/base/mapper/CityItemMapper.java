package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.entity.CityItem;
import com.ljwm.gecko.base.model.vo.RateVo;
import com.ljwm.gecko.base.model.vo.SpecialPercentVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 各城市缴纳项目扣除项表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-08-31
 */
@Repository
public interface CityItemMapper extends BaseMapper<CityItem> {

  @Select("SELECT ci.*,sd.`NAME`  FROM `t_city_item` ci LEFT JOIN `t_special_deduction` sd ON ci.`ITEM_TYPE` = sd.`ID` WHERE ci.`REGION_CODE` = #{code}")
  @ResultMap("RateVo")
  List<RateVo> findByLoCode(Integer code);


  @Select("SELECT  b.`NAME`,a.COMPANY_PER, a.PERSON_PER FROM t_city_item a INNER JOIN t_special_deduction b ON a.ITEM_TYPE = b.ID\n" +
    "AND a.REGION_CODE = #{code}\n" +
    "AND a.DISABLED =#{disableCode}")
  @ResultMap("SpecialPercentVo")
  List<SpecialPercentVo> getSpecial(@Param("code") String code, @Param("disableCode") Integer disableCode);
}
