package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.admin.NoticeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公告表 Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-10-10
 */
public interface NoticeMapper extends BaseMapper<Notice> {


  @Select({
    "<script>",
    "SELECT * FROM `t_notice`",
    "<where>",
    "1=1",
    "<if test=\"params.text != null and params.text != ''\">",
    " AND(",
    Notice.TITLE + " LIKE  CONCAT('%',#{params.text},'%') ",
    " OR " + Notice.CONTENT + " LIKE CONCAT('%',#{params.text},'%') ",
    " )",
    "</if>",
    "<if test=\"params.disabled != null and params.disabled != ''\">",
    " AND " + Notice.DISABLED + " = #{params.disabled}",
    "</if>",
    "</where>",
    "</script>"
  })
  @ResultMap("NoticeVo")
  List<NoticeVo> find(Page<NoticeVo> page,@Param("params") Map map);
}
