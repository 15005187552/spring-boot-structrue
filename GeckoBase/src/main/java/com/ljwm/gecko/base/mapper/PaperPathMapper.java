package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.PaperPath;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
}
