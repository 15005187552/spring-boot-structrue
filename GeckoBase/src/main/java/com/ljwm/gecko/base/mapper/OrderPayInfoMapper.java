package com.ljwm.gecko.base.mapper;

import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.OrderPayInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-10-08
 */
public interface OrderPayInfoMapper extends BaseMapper<OrderPayInfo> {

  OrderPayInfo findOrderPayByOrderNo(@Param("params") Kv kv);

  OrderPayInfo findOrderPayByWxNum(@Param("wxNum") String wxNum);
}
