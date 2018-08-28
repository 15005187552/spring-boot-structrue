package com.ljwm.gecko.admin.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.dto.SqlFactory;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.mapper.CommonMapper;
import com.ljwm.bootbase.security.JwtKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.bean.Dict;
import com.ljwm.gecko.admin.model.form.LoginForm;
import com.ljwm.gecko.admin.model.form.RoleQuery;
import com.ljwm.gecko.admin.model.form.RoleSaveForm;
import com.ljwm.gecko.admin.security.JwtUser;
import com.ljwm.gecko.base.entity.Function;
import com.ljwm.gecko.base.entity.Role;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.mapper.FunctionMapper;
import com.ljwm.gecko.base.mapper.RoleMapper;
import com.ljwm.gecko.base.model.vo.ResultMe;
import com.ljwm.gecko.base.model.vo.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@SuppressWarnings("all")
public class AuthService {

  @Autowired
  private Dict dict;

  @Autowired
  private RoleMapper roleMapper;

  @Autowired
  private CommonMapper commonMapper;

  @Autowired
  private FunctionMapper functionMapper;

  @Autowired
  private AuthService authService;

  @Autowired
  private CommonService commonService;

  @Autowired
  private AuthenticationManager authenticationManager;


  @Transactional
  public void init() {
    String[] roles = dict.getInitRole().split(",");
    for (String role : roles) {
      String[] temp = role.split(":");
      // 获取角色ID
      String id = temp[0];
      // 获取角色名称
      String name = temp[1];

      String[] functions = temp[2].replaceAll(ReUtil.escape("("), "").replaceAll(ReUtil.escape(")"), "").split("-");

      authService.saveRole(new RoleSaveForm()
        .setFunctionIds(Arrays.stream(functions).map(Integer::new).collect(Collectors.toList()))
        .setRoleName(name)
        .setId(id)
      );
    }
  }

  /**
   * 保存角色
   *
   * @param roleSaveForm
   * @return
   */
  @Transactional
  public Role saveRole(RoleSaveForm roleSaveForm) {
    Role role = null;
    //1.获取角色对象
    if (StrUtil.isNotBlank(roleSaveForm.getId().toString()))
      role = roleMapper.selectById(roleSaveForm.getId());
    if (role == null)
      role = new Role();
    //2.设置新参数并保存
    role.setId(StrUtil.isBlank(roleSaveForm.getId()) ? RandomUtil.simpleUUID() : roleSaveForm.getId())
      .setRoleName(roleSaveForm.getRoleName());
    commonService.insertOrUpdate(role, roleMapper);
    //3.更新角色绑定菜单（权限）
    if (CollectionUtil.isNotEmpty(roleSaveForm.getFunctionIds()))
      authService.updateFunction(role.getId(), roleSaveForm.getFunctionIds());
    return role;
  }

  /**
   * 更新菜单（权限）
   *
   * @param roleId
   * @param functionIds
   */
  public void updateFunction(String roleId, List<Integer> functionIds) {
    if (CollectionUtil.isEmpty(functionMapper.selectBatchIds(functionIds)))
      throw new LogicException(ResultEnum.DATA_ERROR, "角色ID为" + functionIds.toArray().toString() + "不存在");

    // 1. 删除该用户在旧的角色中的排布
    commonMapper.deleteJoinTable(
      Kv.by(SqlFactory.TABLE, "t_role_function")
        .set(SqlFactory.AK, "ROLE_ID")
        .set(SqlFactory.AK_VALUE, roleId)
    );
    // 2. 重构该用户在旧的角色中的排布
    if (CollectionUtil.isNotEmpty(functionIds)) {
      commonMapper.batchInsert(
        Kv.by(SqlFactory.TABLE, "t_role_function")
          .set(SqlFactory.COLUMNS, new String[]{"ROLE_ID", "FUNCTION_ID"})
          .set(SqlFactory.VALUES, new HashSet<>(functionIds).stream().map(functionId -> new String[]{roleId + "", functionId + ""}).collect(Collectors.toList()))
      );
    }
  }

  /**
   * 公共验证逻辑实现
   *
   * @param loginForm
   * @return
   */
  private JwtUser validate(LoginForm loginForm) {
    return Optional
      .ofNullable(loginForm)
      .map(form -> new UsernamePasswordAuthenticationToken(form.getUsername(), SecureUtil.md5(SecureUtil.md5(form.getPassword()) + form.getUsername())))
      .map(upToken -> authenticationManager.authenticate(upToken))
      .map(authentication -> {
        if (!authentication.isAuthenticated())
          throw new LogicException(ResultEnum.BAD_CREDENTIALS, "登陆失败");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("登陆成功, 为该用户创建Token: {} ", loginForm);
        return (JwtUser) authentication.getPrincipal();
      })
      .get();
  }


  public ResultMe login(LoginForm loginForm) {
    return Optional
      .ofNullable(loginForm)
      .map(this::validate)
      .map(jwtUser -> me(jwtUser).setToken(JwtKit.generateToken(jwtUser)))
      .get();
  }

  public ResultMe me(JwtUser jwtUser) {

    return Optional
      .ofNullable(jwtUser)
      .map(user -> new ResultMe()
        .setId(user.getId())
        .setUserName(user.getAdmin().getUsername())
        .setNickName(user.getAdmin().getNickName())
      )
      .get();
  }

  /**
   * 角色分页查询
   *
   * @param query
   * @return
   */
  public Page<RoleVo> findRole(RoleQuery query) {
    return commonService.find(query, (p, q) -> roleMapper.find(p, query.getText(), query.getDisabled(), query.getAsc()));
  }

  /**
   * 获取角色
   *
   * @return
   */
  public List<Role> getRole() {
    return roleMapper.selectList(null);
  }

  public void roleDisabled(String id) {
    Role role = roleIsExist(id);
    role.setDisabled(Objects.equals(role.getDisabled(), DisabledEnum.ENABLED.getCode()) ?
      DisabledEnum.DISABLED.getCode() :
      DisabledEnum.ENABLED.getCode());
    commonService.insertOrUpdate(role, roleMapper);
  }

  public void roleDelete(String id) {
    Role role = roleIsExist(id);
    roleMapper.deleteById(role);
    commonMapper.deleteJoinTable(
      Kv.by(SqlFactory.TABLE, "t_role_function")
        .set(SqlFactory.AK, "ROLE_ID")
        .set(SqlFactory.AK_VALUE, id)
    );
    commonMapper.deleteJoinTable(
      Kv.by(SqlFactory.TABLE, "t_admin_role")
        .set(SqlFactory.AK, "ROLE_ID")
        .set(SqlFactory.AK_VALUE, id)
    );
  }

  private Role roleIsExist(String id) {
    Role role = roleMapper.selectById(id);
    if (role == null) throw new LogicException(ResultEnum.DATA_ERROR, "id为" + id + "的角色不存在");
    return role;
  }
}
