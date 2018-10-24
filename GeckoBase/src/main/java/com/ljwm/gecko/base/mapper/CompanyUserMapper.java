package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.CompanyUser;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.model.dto.EmployeeDto;
import com.ljwm.gecko.base.model.vo.EmployeeVo;
import com.ljwm.gecko.base.model.vo.admin.CUserQueryVO;
import com.ljwm.gecko.base.model.vo.admin.CompanyUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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


  @Select("SELECT tc.*,tm.`MEMBER_IDCARD`,tm.`NICK_NAME`,tm.`AVATAR_PATH`,tm.`REG_MOBILE`,tm.`DISABLED` as memberDisabled FROM `t_company_user` tc \n" +
    "LEFT JOIN `t_member` tm ON tc.`MEMBER_ID` = tm.`ID` WHERE `COMPANY_ID` = #{companyId}")
  @ResultMap("CompanyUserVo")
  List<CompanyUserVo> findCompanyUser(Long companyId);


  @Select("SELECT a.MEMBER_ID, c.COMPANY_USER_ID, c.JOB_NUM, a.CERTIFICATE, a.CERT_NUM, a.`NAME`\n" +
    "FROM t_natural_person a, t_company_user b, t_company_user_info c\n" +
    "WHERE a.MEMBER_ID = b.MEMBER_ID\n" +
    "AND b.ID = c.COMPANY_USER_ID\n" +
    "AND b.DISABLED = #{disableCode}\n" +
    "AND b.ACTIVATED = #{activateCode}\n" +
    "AND b.COMPANY_ID = #{companyId}")
  @ResultMap("EmployeeVo")
  List<EmployeeVo> selectEmployee(@Param("companyId") Long companyId,@Param("disableCode") Integer disableCode,@Param("activateCode") Integer activateCode);


  @Select("SELECT * FROM t_company_user a RIGHT JOIN t_company_user_info b\n" +
    "ON a.COMPANY_ID =#{companyId} AND MEMBER_ID=#{memberId} AND a.ID = b.COMPANY_USER_ID")
  @ResultMap("EmployeeInfo")
  List<EmployeeDto> selectEmployeeList(@Param("companyId") Long companyId,@Param("memberId") Long memberId);

  @Select({
    "<script>",
    "SELECT * FROM `t_company_user` t ",
    " LEFT JOIN `t_member` m ",
    " ON t.`MEMBER_ID` = m.`ID` ",
    "<where>",
    " t." + CompanyUser.COMPANY_ID + " = #{params.companyId}",
    "<if test=\"params.text != null and params.text != ''\">",
    " AND(",
    " m." + Member.NICK_NAME + " LIKE  CONCAT('%',#{params.text},'%') ",
    " OR m." + Member.NAME + " LIKE  CONCAT('%',#{params.text},'%') ",
    " )",
    "</if>",
    "<if test=\"params.disabled != null and params.disabled != ''\">",
    " AND t." + CompanyUser.DISABLED + " = #{params.disabled}",
    "</if>",
    "</where>",
    "</script>"
  })
  @ResultMap("CUserQueryVO")
  List<CUserQueryVO> findPage(Page<CUserQueryVO> page,@Param("params") Map map);
}
