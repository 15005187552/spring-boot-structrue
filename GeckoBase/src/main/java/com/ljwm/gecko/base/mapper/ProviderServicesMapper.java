package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.ProviderServices;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.ProviderServicesSimpleVo;
import com.ljwm.gecko.base.model.vo.ProviderServicesVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 服务商关联服务分类表 Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-09-13
 */
@Repository
public interface ProviderServicesMapper extends BaseMapper<ProviderServices> {
  List<ProviderServicesVo> findProviderServicesVoListByProviderId(@Param("providerId") Long providerId);

  List<ProviderServicesSimpleVo> findClienProviderServicesListByProviderId(@Param("providerId") Long providerId);

  List<ProviderServicesVo> findProviderServicesDetailVoListByProviderId(@Param("providerId") Long providerId);

  @Update("UPDATE t_provider_services SET DISABLED=#{status} WHERE ID=#{id}")
  int disabled(@Param("id") Long id,@Param("status") Integer status);
}
