package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.entity.Service;
import com.ljwm.gecko.base.model.vo.ServiceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务分类表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-08
 */
public interface ServiceMapper extends BaseMapper<Service> {
  List<ServiceVo> find();

  List<ServiceVo> children(@Param("pId") Integer pId);
}
