package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.IncomeType;
import com.ljwm.gecko.base.model.vo.IncomeTypeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 收入类型表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-08-29
 */
public interface IncomeTypeMapper extends BaseMapper<IncomeType> {

  List<IncomeTypeVo> find();

  List<IncomeTypeVo> children(@Param("pId") Long pId);

  Boolean deleteAble(@Param("id") Long id);

  List<IncomeTypeVo> findPage(Page<IncomeTypeVo> ret, @Param("params") Kv params);
}