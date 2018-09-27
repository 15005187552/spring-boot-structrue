package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.ProviderUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 服务商员工表 Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-09-14
 */
public interface ProviderUserMapper extends BaseMapper<ProviderUser> {
    void delete(@Param("providerId") Long providerId);

    ProviderUser findByMap(@Param("params") Map map);
}
