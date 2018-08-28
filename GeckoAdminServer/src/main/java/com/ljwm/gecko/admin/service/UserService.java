package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
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
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.bean.Dict;
import com.ljwm.gecko.admin.model.form.AdminQuery;
import com.ljwm.gecko.admin.model.form.AdminSaveForm;
import com.ljwm.gecko.admin.model.vo.AdminVo;
import com.ljwm.gecko.base.entity.Admin;
import com.ljwm.gecko.base.entity.Role;
import com.ljwm.gecko.base.mapper.AdminMapper;
import com.ljwm.gecko.base.mapper.RoleMapper;
import com.ljwm.gecko.base.model.dto.AdminDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@SuppressWarnings("all")
public class UserService {

  @Autowired
  private Dict dict;

  @Autowired
  private RoleMapper roleMapper;

  @Autowired
  private AdminMapper adminMapper;

  @Autowired
  private UserService userService;

  @Autowired
  private CommonMapper commonMapper;

  @Autowired
  private CommonService commonService;

  @Transactional
  public void init() {
    // 1. 初始化系统管理员用户
    String[] sysAdminInfo = dict.getSysAdmin().split(":");
    // 2. sysAdmin 的ID
    String id = sysAdminInfo[0];
    // 3. sysAdmin 的用户名
    String username = sysAdminInfo[1];
    // 4. sysAdmin 的密码
    String password = sysAdminInfo[2];

    String[] roles = sysAdminInfo[3].replaceAll(ReUtil.escape("("), "").replaceAll(ReUtil.escape(")"), "").split("-");


    AdminSaveForm admin = new AdminSaveForm()
      .setRoleIds(Arrays.stream(roles).map(Integer::new).collect(Collectors.toList()))
      .setPassword(SecureUtil.md5(SecureUtil.md5(password) + username))
      .setId(id)
      .setUsername(username);
    userService.saveAdmin(admin);
  }

  @Transactional
  public Admin saveAdmin(AdminSaveForm adminSaveForm) {
    Admin admin = null;

    //1.获取后管用户对象
    if (Objects.isNull(adminSaveForm.getId()))
      admin = adminMapper.selectById(adminSaveForm.getId());
    if (admin == null)
      admin = new Admin();
    //2.设置新参数并插入数据库
    admin.setPassword(SecureUtil.md5(SecureUtil.md5(adminSaveForm.getPassword()) + adminSaveForm.getUsername()))
      .setId(StrUtil.isBlank(adminSaveForm.getId()) ? RandomUtil.simpleUUID() : adminSaveForm.getId())
      .setUsername(adminSaveForm.getUsername())
      .setNickName(adminSaveForm.getNickName())
      .setUpdateTime(DateUtil.date())
      .setCreateTime(DateUtil.date());
    commonService.insertOrUpdate(admin, adminMapper);
    //3.更新用户角色
    if (CollectionUtil.isNotEmpty(adminSaveForm.getRoleIds()))
      userService.updateRoles(admin.getId(), adminSaveForm.getRoleIds());
    return admin;
  }

  @Transactional
  public void updateRoles(String adminId, List<Integer> roleIds) {
    if (CollectionUtil.isEmpty(roleMapper.selectBatchIds(roleIds)))
      throw new LogicException(ResultEnum.DATA_ERROR, "角色ID为" + roleIds.toArray().toString() + "不存在");

    // 1. 删除该用户在旧的角色中的排布
    commonMapper.deleteJoinTable(
      Kv.by(SqlFactory.TABLE, "t_admin_role")
        .set(SqlFactory.AK, "ADMIN_ID")
        .set(SqlFactory.AK_VALUE, adminId)
    );
    // 2. 重构该用户在旧的角色中的排布
    if (CollectionUtil.isNotEmpty(roleIds)) {
      commonMapper.batchInsert(
        Kv.by(SqlFactory.TABLE, "t_admin_role")
          .set(SqlFactory.COLUMNS, new String[]{"ROLE_ID", "ADMIN_ID"})
          .set(SqlFactory.VALUES, new HashSet<>(roleIds).stream().map(roleId -> new String[]{roleId + "", adminId + ""}).collect(Collectors.toList()))
      );
    }
  }

  /**
   * 查询后管用户
   * @param adminQuery
   * @return
   */
  public Page<AdminVo> findAdmin(AdminQuery adminQuery) {
    Page page = new Page(adminQuery.getPage().getCurrent(),adminQuery.getPage().getSize());
    adminMapper.find(page,Kv.by("disabled",0));
    return commonService.find(adminQuery, (p, q) -> adminMapper.find(p, BeanUtil.beanToMap(adminQuery)));
  }
}