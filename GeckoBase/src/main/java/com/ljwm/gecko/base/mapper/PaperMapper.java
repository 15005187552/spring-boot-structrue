package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.Paper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.PaperVo;
import org.apache.ibatis.annotations.Param;

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
}
