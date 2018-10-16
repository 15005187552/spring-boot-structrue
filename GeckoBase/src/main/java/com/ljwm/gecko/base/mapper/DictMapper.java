package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.DictVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-10-16
 */
public interface DictMapper extends BaseMapper<Dict> {
  List<DictVo> findByPage(Page<DictVo> page, @Param("params")Map map);

  String findValueByKey(@Param("key") String key);
}
