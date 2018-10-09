package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.AttendanceAttribute;
import com.ljwm.gecko.base.model.vo.admin.AttendanceAtrVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
@Repository
public interface AttendanceAttributeMapper extends BaseMapper<AttendanceAttribute> {

  List<AttendanceAtrVo> find(Page<AttendanceAtrVo> page, @Param("params") Map map);

  Boolean deleteAble(Long id);
}
