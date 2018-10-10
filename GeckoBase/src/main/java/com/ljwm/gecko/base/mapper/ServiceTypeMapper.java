package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.ServiceType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.ServeSimpleVo;
import com.ljwm.gecko.base.model.vo.ServiceVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务分类表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-10
 */
public interface ServiceTypeMapper extends BaseMapper<ServiceType> {

  List<ServiceVo> findTree();

  List<ServiceVo> find(Page<ServiceVo> page, @Param("params")Map map);

  List<ServiceVo> children(@Param("pId") Integer pId);

  ServeSimpleVo findById(@Param("id") Integer id);

  String findNameByPid(@Param("pId") Integer pId);

  @Select("SELECT st.* FROM `t_service_type` st LEFT JOIN `t_provider_services` ps ON st.`ID` = ps.`SERVICE_ID` \n" +
    "WHERE ps.`PROVIDER_ID` = #{id} ORDER BY st.`LEVEL`")
  List<ServiceType> findByProviderId(Integer id);

  List<ServeSimpleVo> findTopList();
}
