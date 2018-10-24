package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.admin.model.vo.FileTemplateVo;
import com.ljwm.gecko.base.entity.FileTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.entity.Function;
import com.ljwm.gecko.base.model.dto.FileTemplateDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

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

public interface FileTemplateMapper extends BaseMapper<FileTemplate> {
  List<FileTemplateVo> find(Page<FileTemplateVo> page, Map map);
}
