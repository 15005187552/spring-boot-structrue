package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.form.CompanyCheckForm;
import com.ljwm.gecko.admin.model.form.CompanyQuery;
import com.ljwm.gecko.admin.model.vo.SimpleCompany;
import com.ljwm.gecko.base.entity.Company;
import com.ljwm.gecko.base.entity.CompanyUser;
import com.ljwm.gecko.base.enums.CompanyValidateEnum;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.enums.MPTemplateEnum;
import com.ljwm.gecko.base.enums.ValidateStatEnum;
import com.ljwm.gecko.base.mapper.CompanyMapper;
import com.ljwm.gecko.base.mapper.CompanyUserMapper;
import com.ljwm.gecko.base.model.dto.AdminCompanyDto;
import com.ljwm.gecko.base.model.dto.im.MessageDto;
import com.ljwm.gecko.base.model.vo.UnValidateCompanyVo;
import com.ljwm.gecko.base.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@SuppressWarnings("all")
public class CompanyService {

  @Autowired
  private CompanyMapper companyMapper;

  @Autowired
  private CompanyUserMapper companyUserMapper;

  @Autowired
  private CompanyService companyService;

  @Autowired
  private CommonService commonService;

  @Autowired
  private MessageService messageService;

  public Page<UnValidateCompanyVo> findUnValidate(CompanyQuery query) {
    return commonService.find(query,(p,q) -> companyMapper.find(p,BeanUtil.beanToMap(query)));
  }

  public Page<AdminCompanyDto> find(CompanyQuery query) {
    return commonService.find(query,(p,q) -> companyMapper.find(p,BeanUtil.beanToMap(query)));
  }


  @Transactional
  public Company checkCompany(CompanyCheckForm form) {
    if (Objects.isNull(form.getId())) throw new LogicException(ResultEnum.DATA_ERROR,"企业ID不能未空");
    Company company = companyMapper.selectById(form.getId());
    if (Objects.isNull(company)) throw new LogicException(ResultEnum.DATA_ERROR,"未找到ID为" + form.getId() + "的企业");
    company.setValidateState(form.getIsPass()).setValidateTime(DateTime.now())
      .setValidatorId(SecurityKit.currentId()).setValidateText(form.getText());
    companyMapper.updateById(company);
    pushMessage(company);
    return company;
  }

  public List<SimpleCompany> getCompany() {
    return companyMapper.selectList(null).stream().map(SimpleCompany::new).collect(Collectors.toList());
  }

  public List<SimpleCompany> getOnlineCompany(){
    return companyMapper.selectList(new QueryWrapper<Company>().eq("VALIDATE_STATE",CompanyValidateEnum.PASS_VALIDATE.getCode()))
      .stream().map(SimpleCompany::new).collect(Collectors.toList());
  }

  public void pushMessage(Company company) {
    MessageDto messageDto = new MessageDto();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("templateString",MPTemplateEnum.AUDIT.getTemplateId());
    jsonObject.put("wxParams",
      Kv.by("keyword1",company.getName()).set("keyword2",CompanyValidateEnum.getName(company.getValidateState()))
        .set("keyword3",company.getValidateText())
    );
    messageDto.setLoginType(Collections.singletonList(LoginType.WX_APP))
      .setReceiverId(company.getCreaterId())
      .setMessage(jsonObject.toJSONString())
    ;
    messageService.pushMessage(messageDto);
  }

}
