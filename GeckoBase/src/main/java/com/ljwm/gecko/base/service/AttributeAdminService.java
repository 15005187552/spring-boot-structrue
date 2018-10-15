package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.Attribute;
import com.ljwm.gecko.base.enums.TableNameEnum;
import com.ljwm.gecko.base.mapper.AttributeMapper;
import com.ljwm.gecko.base.model.form.AttributeForm;
import com.ljwm.gecko.base.utils.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@SuppressWarnings("all")
public class AttributeAdminService {

  @Autowired
  private AttributeMapper attributeMapper;

  @Autowired
  private CommonService commonService;

  @Transactional
  public Attribute save(Class aClass,AttributeForm form) {
    form.setTableName(getTable(aClass));
    List<Attribute> attributes = attributeMapper.selectByMap(Kv.by("TABLE_NAME",form.getTableName()).set("ITEM_ID",form.getItemId()));
    Attribute attribute = null;
    if (attributes.size() > 0)
      attribute = attributes.get(0);
    else
      attribute = new Attribute();
    BeanUtil.copyProperties(form,attribute);
    commonService.insertOrUpdate(attribute,attributeMapper);
    return attribute;
  }

  private Integer getTable(Class aClass) {
    String tableName = null;
    try {
      tableName = Class.forName(aClass.getName()).getAnnotation(TableName.class).value();
    } catch (ClassNotFoundException e) {
      throw new LogicException(ResultEnum.DATA_ERROR,"未找到名字为" + aClass.getName() + "的实体类");
    }
    return EnumUtil.getEnumByName(TableNameEnum.class,tableName.split("`")[1].split("`")[0]).getCode();
  }

  @Transactional
  public void delete(Class aClass,Long id) {
    Integer tableCode = getTable(aClass);
    if (attributeMapper.selectOne(new QueryWrapper<Attribute>().eq("TABLE_NAME",tableCode).eq("ITEM_ID",id)) == null)
      throw new LogicException(ResultEnum.DATA_ERROR,"未知道id为" + id + "的数据");
    attributeMapper.delete(new QueryWrapper<Attribute>().eq("TABLE_NAME",tableCode).eq("ITEM_ID",id));
  }
}
