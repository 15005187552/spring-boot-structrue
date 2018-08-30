package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.SpecialDeduction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.SpecialDeductionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 专项扣除分类表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-08-29
 */
public interface SpecialDeductionMapper extends BaseMapper<SpecialDeduction> {
    List<SpecialDeductionVo> find();

    Boolean deleteAble(@Param("id") Long id);

    List<SpecialDeductionVo> findPage(Page<SpecialDeductionVo> ret, @Param("params") Kv params);
}
