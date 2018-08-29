package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.OtherReduce;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.OtherReduceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 其它扣除减免分类表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-08-29
 */
public interface OtherReduceMapper extends BaseMapper<OtherReduce> {

  List<OtherReduceVo> find(Page<OtherReduceVo> page, @Param("param")Map map);

}
