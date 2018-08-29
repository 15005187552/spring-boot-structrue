package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.entity.Location;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
