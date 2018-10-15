package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.OrderComments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单评价表 Mapper 接口
 * </p>
 *
 * @author Livis
 * @since 2018-09-18
 */
public interface OrderCommentsMapper extends BaseMapper<OrderComments> {
    Integer starCount(@Param("providerId") Long providerId);
}
