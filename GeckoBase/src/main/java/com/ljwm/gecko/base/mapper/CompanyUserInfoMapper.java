package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.CompanyUserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 公司员工信息详情表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-27
 */
public interface CompanyUserInfoMapper extends BaseMapper<CompanyUserInfo> {

  @Select("SELECT * FROM t_company_user a, t_company_user_info b WHERE a.MEMBER_ID =#{memberId} AND a.COMPANY_ID =#{companyId} AND a.ID = b.COMPANY_USER_ID")
  List<CompanyUserInfo> selectCompanyUser(@Param("companyId")Long companyId, @Param("memberId") Long memberId);
}
