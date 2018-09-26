package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.ProviderGoodsPath;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.ProviderGoodsPathVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务商证书表 Mapper 接口
 * </p>
 *
 * @author Livis
 * @since 2018-09-26
 */
public interface ProviderGoodsPathMapper extends BaseMapper<ProviderGoodsPath> {

  void deleteByGoodId(@Param("goodId") Long goodId);

  List<ProviderGoodsPathVo> findByGoodId(@Param("goodId") Long goodId);

}
