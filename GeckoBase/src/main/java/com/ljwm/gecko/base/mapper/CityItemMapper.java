package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.CityItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.RateVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 各城市缴纳项目扣除项表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-08-31
 */
public interface CityItemMapper extends BaseMapper<CityItem> {

  @Select("SELECT ci.*,sd.`NAME`  FROM `t_city_item` ci LEFT JOIN `t_special_deduction` sd ON ci.`ITEM_TYPE` = sd.`ID` WHERE ci.`REGION_CODE` = #{code}")
  @ResultMap("RateVo")
  List<RateVo> findByLoCode(Integer code);
}
