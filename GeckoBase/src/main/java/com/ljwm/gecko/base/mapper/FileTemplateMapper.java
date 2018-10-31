package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.FileTemplate;
import com.ljwm.gecko.base.model.dto.FileTemplateDto;
import com.ljwm.gecko.base.model.vo.FileTemplateVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kevin
 * @since 2018-10-24
 */

@Repository
public interface FileTemplateMapper extends BaseMapper<FileTemplate> {
  List<FileTemplateVo> findByPage(Page<FileTemplateDto> page, @Param("params") Map map);
}
