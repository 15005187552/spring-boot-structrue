package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.CompanyUserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 公司员工信息详情表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-27
 */
@Repository
public interface CompanyUserInfoMapper extends BaseMapper<CompanyUserInfo> {

  @Select("SELECT * \n" +
    "FROM (SELECT * FROM t_company_user WHERE MEMBER_ID =#{memberId} AND COMPANY_ID =#{companyId}) a \n" +
    "LEFT JOIN t_company_user_info c\n" +
    "ON a.id = c.COMPANY_USER_ID")
  List<CompanyUserInfo> selectCompanyUser(@Param("companyId")Long companyId, @Param("memberId") Long memberId);
}
