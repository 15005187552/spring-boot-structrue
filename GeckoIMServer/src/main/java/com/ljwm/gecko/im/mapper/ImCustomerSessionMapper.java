package com.ljwm.gecko.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.entity.CustomerSession;
import com.ljwm.gecko.im.model.vo.SessionVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客服会话表 Mapper 接口
 * </p>
 *
 * @author YunQiSong
 * @since 2018-09-24
 */
@Repository
public interface ImCustomerSessionMapper extends BaseMapper<CustomerSession> {

  @Select({
    "<script>",
    "SELECT * FROM `im_customer_session`",
    "<where>",
    "<if test=\"params.memberId != null\">",
    " AND " + CustomerSession.CUSTOMER_MEMBER_ID + " = #{params.memberId}",
    "</if>",
    "<if test=\"params.guestId != null\">",
    " AND " + CustomerSession.CUSTOMER_GUEST_ID + " = #{params.guestId}",
    "</if>",
    "</where>",
    "</script>"
  })
  @ResultMap("SessionVo")
  List<SessionVo> getSessions(@Param("params") Map map);
}
