package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.SpecItem;
import com.ljwm.gecko.base.model.vo.SpecItemSimpleVo;
import com.ljwm.gecko.base.model.vo.SpecItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Livis
 * @since 2018-09-26
 */
public interface SpecItemMapper extends BaseMapper<SpecItem> {
  List<SpecItemVo> findByPage(Page<SpecItemVo> ret, @Param("params")Map map);

  List<SpecItemSimpleVo> find(@Param("params")Map map);

  SpecItemSimpleVo findByMap(@Param("params")Map map);
}
