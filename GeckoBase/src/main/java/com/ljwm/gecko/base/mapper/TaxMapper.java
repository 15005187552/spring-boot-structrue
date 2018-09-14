package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.Tax;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 报税数据主表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-06
 */
@Component
public interface TaxMapper extends BaseMapper<Tax> {

  @Select("SELECT * FROM `t_tax` WHERE MEMBER_ID = #{map.memberId} AND DECLARE_TYPE= #{map.declareType}")
  List<Tax> selectByPage(Page<Tax> taxPage, @Param("map") Map map);
}
