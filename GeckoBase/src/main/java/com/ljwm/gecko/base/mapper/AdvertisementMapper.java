package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.Advertisement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.AdvertisementVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 广告数据表 Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-08-30
 */
public interface AdvertisementMapper extends BaseMapper<Advertisement> {

  List<AdvertisementVo> find(Page<AdvertisementVo> page,@Param("params") Map map);

  List<AdvertisementVo> find(@Param("params") Map map);

}
