package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.dto.SqlFactory;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.mapper.CommonMapper;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.enums.DeleteEnum;
import com.ljwm.gecko.admin.model.bean.Dict;
import com.ljwm.gecko.admin.model.form.FunctionSaveForm;
import com.ljwm.gecko.base.entity.Function;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.mapper.FunctionMapper;
import com.ljwm.gecko.base.model.dto.FunctionDto;
import com.ljwm.gecko.base.model.vo.FunctionTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 菜单 服务  现在只适用于二级菜单
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class FunctionService {

  @Autowired
  private Dict dict;

  @Autowired
  private CommonMapper commonMapper;

  @Autowired
  private CommonService commonService;

  @Autowired
  private FunctionMapper functionMapper;

  @Autowired
  private FunctionService functionService;

  @Transactional
  public void init() {
    for (String menu : dict.getBuiltInMenu()) {
      String[] menuInfos = menu.split(":");
      Function function = new Function()
        .setParentId(menuInfos[6])
        .setId(menuInfos[0])
        .setName(menuInfos[1])
        .setTitle(menuInfos[2])
        .setDescription(menuInfos[3])
        .setUrl(menuInfos[4])
        .setIcon(menuInfos[5]);
//      functionMapper.insert(function);
      commonService.insertOrUpdate(function, functionMapper);
    }
  }


  public List<FunctionTree> tree(String text) {
    return functionMapper.tree(text);
  }


  @Transactional
  public Function saveFunction(FunctionSaveForm form) {
    Function function = null;
    if (StrUtil.isBlank(form.getId()))
      function = functionMapper.selectById(form.getId());
    if (function == null)
      function = new Function();

    BeanUtil.copyProperties(form, function);

    function.setId(StrUtil.isBlank(form.getId()) ? RandomUtil.simpleUUID() : form.getId());

    commonService.insertOrUpdate(function, functionMapper);
    return function;
  }

  public List<Function> getFunction() {
    return functionMapper.selectList(null);
  }

  @Transactional
  public void funDisabled(String id) {
    FunctionDto functionDto = funIsExist(id);
    Boolean flag = Objects.equals(functionDto.getDisabled(), DisabledEnum.ENABLED.getCode());
    if (flag && CollectionUtil.isNotEmpty(functionDto.getChildren()))
      functionDto.getChildren().forEach(item -> {
        item.setDisabled(DisabledEnum.DISABLED.getCode());
        commonService.insertOrUpdate(item, functionMapper);
      });
    functionDto.setDisabled(flag ?
      DisabledEnum.DISABLED.getCode() :
      DisabledEnum.ENABLED.getCode());
    commonService.insertOrUpdate(functionDto, functionMapper);
  }

  @Transactional
  public void funDelete(String id, Boolean type) {
    FunctionDto functionDto = funIsExist(id);
    if (Objects.equals(type, DeleteEnum.NORMAL.getInfo())) relationExist(functionDto);
    else functionService.deleteRelation(functionDto);
    functionMapper.deleteById(functionDto);
  }

  @Transactional
  public void deleteRelation(FunctionDto functionDto) {
    if (CollectionUtil.isNotEmpty(functionDto.getChildren()))
      functionDto.getChildren().forEach(item -> functionMapper.deleteById(item));
    commonMapper.deleteJoinTable(
      Kv.by(SqlFactory.TABLE, "t_role_function")
        .set(SqlFactory.AK, "FUNCTION_ID")
        .set(SqlFactory.AK_VALUE, functionDto.getId())
    );
  }

  private FunctionDto funIsExist(String id) {
    FunctionDto function = functionMapper.findDtoById(id);
    if (function == null) throw new LogicException(ResultEnum.DATA_ERROR, "id为" + id + "的菜单不存在");
    return function;
  }

  public void relationExist(FunctionDto functionDto) {
    if (CollectionUtil.isNotEmpty(functionDto.getChildren()))
      throw new LogicException(ResultEnum.DATA_ERROR, "id为" + functionDto.getId() + "的菜单有子菜单");
    if (functionMapper.relationExist(functionDto.getId()) > 0)
      throw new LogicException(ResultEnum.DATA_ERROR, "id为" + functionDto.getId() + "的菜单有角色关联");
  }
}
