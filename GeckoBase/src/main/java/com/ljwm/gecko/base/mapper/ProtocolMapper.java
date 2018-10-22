package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.Notice;
import com.ljwm.gecko.base.entity.Protocol;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.admin.ProtocolVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统自定义协议表 Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-10-18
 */
@Repository
public interface ProtocolMapper extends BaseMapper<Protocol> {

  @Select({
    "<script>",
    "SELECT * FROM `t_protocol`",
    "<where>",
    "1=1",
    "<if test=\"params.text != null and params.text != ''\">",
    " AND(",
    Protocol.CODE + " LIKE  CONCAT('%',#{params.text},'%') ",
    " OR " + Protocol.NAME + " LIKE CONCAT('%',#{params.text},'%') ",
    " )",
    "</if>",
    "<if test=\"params.disabled != null and params.disabled != ''\">",
    " AND " + Protocol.DISABLED + " = #{params.disabled}",
    "</if>",
    "</where>",
    "</script>"
  })
  @ResultMap("ProtocolVO")
  List<ProtocolVO> find(Page<ProtocolVO> page,@Param("params") Map map);
}
