package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.AttendanceAttribute;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.admin.AttendanceAtrVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考勤字段表 Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-09-27
 */
public interface AttendanceAttributeMapper extends BaseMapper<AttendanceAttribute> {

  List<AttendanceAtrVo> find(Page<AttendanceAtrVo> page, @Param("params") Map map);
}