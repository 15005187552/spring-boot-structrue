package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.SpecServicesPrice;
import com.ljwm.gecko.base.model.vo.SpecServicesPriceSimpleVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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

  SpecServicesPriceSimpleVo findById(@Param("id") Long id);

  List<SpecServicesPriceSimpleVo> findByServiceIdAndProviderId(@Param("params") Kv kv);

  @Update("UPDATE t_spec_services_price SET DISABLED=#{status} WHERE SERVICE_ID=#{serviceId} and PROVIDER_ID=#{providerId}")
  int disabled(@Param("serviceId") Integer serviceId,@Param("status") Integer status,@Param("providerId") Long providerId);
}
