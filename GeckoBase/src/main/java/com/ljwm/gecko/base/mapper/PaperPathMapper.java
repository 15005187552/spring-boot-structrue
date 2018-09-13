package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.PaperPath;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.PaperPathVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务商证书表 Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-09-13
 */
public interface PaperPathMapper extends BaseMapper<PaperPath> {
  void delete(Long memberPaperId);

  List<PaperPathVo> findByMemberPaperId(@Param("memberPaperId") Long memberPaperId);
}
