package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.dto.AdminCompanyDto;
import com.ljwm.gecko.base.model.vo.UnValidateCompanyVo;
import org.apache.ibatis.annotations.Param;
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
}
