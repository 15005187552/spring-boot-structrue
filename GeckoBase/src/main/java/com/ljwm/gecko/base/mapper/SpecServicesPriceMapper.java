package com.ljwm.gecko.base.mapper;

import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.SpecServicesPrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.SpecServicesPriceSimpleVo;
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
public interface SpecServicesPriceMapper extends BaseMapper<SpecServicesPrice> {

  SpecServicesPriceSimpleVo findByMap(@Param("params") Map map);

  List<SpecServicesPriceSimpleVo> find(@Param("params") Map map);
}
