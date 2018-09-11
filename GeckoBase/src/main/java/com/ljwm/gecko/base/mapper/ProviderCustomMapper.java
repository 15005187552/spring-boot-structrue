package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.ProviderCustom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.ProviderCustomVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-10
 */
public interface ProviderCustomMapper extends BaseMapper<ProviderCustom> {
    List<ProviderCustomVo> findByPage(Page<ProviderCustomVo> ret, @Param("params") Kv params);
}
