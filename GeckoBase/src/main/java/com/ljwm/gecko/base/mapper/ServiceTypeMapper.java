package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.ServiceType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.ServiceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务分类表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-10
 */
public interface ServiceTypeMapper extends BaseMapper<ServiceType> {
  List<ServiceVo> find();

  List<ServiceVo> children(@Param("pId") Integer pId);
}
