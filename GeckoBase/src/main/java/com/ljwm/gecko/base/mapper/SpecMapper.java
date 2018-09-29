package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.Spec;
import com.ljwm.gecko.base.model.vo.SpecSimpleVo;
import com.ljwm.gecko.base.model.vo.SpecVo;
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
public interface SpecMapper extends BaseMapper<Spec> {
  List<SpecVo> findByPage(Page<SpecVo> ret, @Param("params")Map map);

  List<SpecSimpleVo> find(@Param("params")Map map);

  SpecSimpleVo findByMap(@Param("params")Map map);

  SpecSimpleVo  findSpecSimpleVoById(@Param("id") Integer id);

  List<SpecVo> findByServiceId(@Param("serviceId") Integer serviceId);
}
