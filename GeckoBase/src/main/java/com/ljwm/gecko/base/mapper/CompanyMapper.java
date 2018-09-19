package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.Company;
import com.ljwm.gecko.base.model.dto.AdminCompanyDto;
import com.ljwm.gecko.base.model.vo.CompanyVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 入驻企业表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-03
 */
@Repository
public interface CompanyMapper extends BaseMapper<Company> {


  List<AdminCompanyDto> find(Page<AdminCompanyDto> page, @Param("params")Map map);

  @Select({"SELECT a.* FROM t_company a, t_company_user b\n" ,
    "WHERE a.ID = b.COMPANY_ID\n" ,
    "AND b.MEMBER_ID = #{memberId}"})
  @ResultMap("BaseResultMap")
  List<Company> findCompany(Long memberId);

  @Select("SELECT *, b.ID as S_ID FROM t_company a, t_company_special b WHERE a.ID = b.COMPANY_ID AND a.ID=#{companyId}")
  @ResultMap("CompanyVo")
  List<CompanyVo> findCompanyById(Long companyId);

  @Select("SELECT *, b.ID as S_ID FROM t_company a, t_company_special b WHERE a.ID = b.COMPANY_ID AND a.NAME=#{name} AND a.VALIDATE_STATE = #{validateState}")
  @ResultMap("CompanyVo")
  List<CompanyVo> findCompanyByName(@Param("name")String name, @Param("validateState")Integer validateState);
}
