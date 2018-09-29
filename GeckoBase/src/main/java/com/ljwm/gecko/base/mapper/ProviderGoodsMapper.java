package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.ProviderGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.ProviderGoodsVo;
import com.ljwm.gecko.base.model.vo.ProviderVo;
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
public interface ProviderGoodsMapper extends BaseMapper<ProviderGoods> {
  List<ProviderGoodsVo> findByPage(Page<ProviderGoodsVo> ret, @Param("params")Map map);

  ProviderGoodsVo findProviderGoodsVoById(@Param("id") Long id);
}
