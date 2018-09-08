package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.MemberPaper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.MemberPaperVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 服务商证书表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-05
 */
@Component
public interface MemberPaperMapper extends BaseMapper<MemberPaper> {
  List<MemberPaperVo> findMemberPaperVoListByMemberId(Long memberId);
}
