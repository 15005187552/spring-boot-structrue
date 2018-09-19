package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.Provider;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.MemberVo;
import com.ljwm.gecko.base.model.vo.ProviderSimpleVo;
import com.ljwm.gecko.base.model.vo.ProviderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务商表 Mapper 接口
 * </p>
 *
 * @author yuzhou
 * @since 2018-08-24
 */
public interface ProviderMapper extends BaseMapper<Provider> {


  ProviderVo findById(@Param("id") Long id);

  List<ProviderVo> findByPage(Page<ProviderVo> ret, @Param("params")Map map);

  ProviderVo findProviderByMemberId(@Param("memberId") Long memberId);

  ProviderVo findProviderSimpleVoByMemberId(@Param("memberId") Long memberId);

  List<ProviderSimpleVo> findClientByPage(Page<ProviderVo> ret, @Param("params")Map map);
}
