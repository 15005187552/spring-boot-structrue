package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.AddSpecial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.AddSpecialVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 专项附加扣除分类表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-08-29
 */
public interface AddSpecialMapper extends BaseMapper<AddSpecial> {

  List<AddSpecialVo> find();

  Boolean deleteAble(@Param("id") Long id);

  List<AddSpecialVo> findPage(Page<AddSpecialVo> ret, @Param("params") Kv params);

}
