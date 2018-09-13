package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.Paper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.PaperVo;
import com.ljwm.gecko.base.model.vo.admin.PaperAdminVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务商证件类型表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-06
 */
public interface PaperMapper extends BaseMapper<Paper> {
  PaperVo findPaperVoByPaperId(@Param("paperId") Integer paperId);

  List<PaperAdminVo> find(Page<PaperAdminVo> page, @Param("params")Map map);

  Boolean deleteAble(Integer id);
}
