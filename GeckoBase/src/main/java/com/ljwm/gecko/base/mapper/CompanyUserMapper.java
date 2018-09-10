package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.entity.Company;
import com.ljwm.gecko.base.entity.CompanyUser;
import com.ljwm.gecko.base.model.vo.admin.CompanyUserVo;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.List;

/**
 * <p>
 * 企业员工表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-03
 */
@Repository
public interface CompanyUserMapper extends BaseMapper<CompanyUser> {

  @Select("SELECT a.*\n" +
    "FROM t_company a, t_company_user b\n" +
    "WHERE a.ID = b.COMPANY_ID\n" +
    "AND b.MEMBER_ID = #{memberId}")
  List<Company> findCompany(Long memberId);

  @Select("SELECT tc.*,tm.`MEMBER_IDCARD`,tm.`NICK_NAME`,tm.`AVATAR_PATH`,tm.`REG_MOBILE`,tm.`DISABLED` as memberDisabled FROM `t_company_user` tc \n" +
    "LEFT JOIN `t_member` tm ON tc.`MEMBER_ID` = tm.`ID` WHERE `COMPANY_ID` = #{companyId}")
  @ResultMap("CompanyUserVo")
  List<CompanyUserVo> findCompanyUser(Long companyId);
}
