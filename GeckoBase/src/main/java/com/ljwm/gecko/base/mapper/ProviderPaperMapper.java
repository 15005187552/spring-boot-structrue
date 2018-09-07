package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.ProviderPaper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.MemberPaperVo;
import com.ljwm.gecko.base.model.vo.ProviderPaperVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务商证书表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-06
 */
public interface ProviderPaperMapper extends BaseMapper<ProviderPaper> {
  List<ProviderPaperVo> findProviderPaperVoListByProviderId(@Param("providerId") Long providerId);
}
