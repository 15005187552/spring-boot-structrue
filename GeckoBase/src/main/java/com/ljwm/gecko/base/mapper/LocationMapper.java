package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.Location;
import com.ljwm.gecko.base.model.vo.LocationRateVo;
import com.ljwm.gecko.base.model.vo.SimpleLocation;
import com.ljwm.gecko.base.model.vo.SimpleProv;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 中国行政区划表 Mapper 接口
 * </p>
 *
 * @author yuzhou
 * @since 2018-08-24
 */
@Repository
public interface LocationMapper extends BaseMapper<Location> {

  @Select("SELECT * FROM `t_location` WHERE `CODE`=#{code}")
  @ResultMap("BaseResultMap")
  Location findByCode(Integer code);

  @Select("SELECT * FROM t_location WHERE LEVEL = \"0\"")
  List<Location> selectProvinceByLevel();

  @Select("SELECT * FROM t_location WHERE PCODE =#{code}")
  List<Location> selectByCode(Integer code);

  Location findCodeByName(@Param("params") Kv kv);

  @Select("SELECT cl.*,pl.`CODE` PARENT_CODE,pl.`NAME` PARENT_NAME FROM `t_location` cl LEFT JOIN `t_location` pl ON pl.`CODE` LIKE CONCAT(LEFT(cl.`CODE`,3),'%') WHERE \n" +
    " cl.`CODE` LIKE CONCAT('%','00')  AND pl.`LEVEL` = 0  ")
  @ResultMap("SimpleLocation")
  List<SimpleLocation> findProvAndCity();

  List<LocationRateVo> find(Page<LocationRateVo> page, @Param("params")Map map);
}
