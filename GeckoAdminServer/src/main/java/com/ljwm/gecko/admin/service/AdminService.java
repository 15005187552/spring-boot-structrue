package com.ljwm.gecko.admin.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlHelper;
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
import com.ljwm.gecko.base.entity.Admin;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.mapper.AdminMapper;
import com.ljwm.gecko.base.mapper.RoleMapper;
import com.ljwm.gecko.base.model.vo.AdminVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Service
@SuppressWarnings("all")
public class AdminService {

  @Autowired
  private Dict dict;

  @Autowired
  private RoleMapper roleMapper;

  @Autowired
  private AdminMapper adminMapper;

  @Autowired
  private AdminService adminService;

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
      .setPassword(password)
      .setId(Long.valueOf(id))
      .setUsername(username);
    adminService.saveAdmin(admin);
  }

  @Transactional
  public Admin saveAdmin(AdminSaveForm adminSaveForm) {
    Admin admin = null;

    //1.获取后管用户对象
    if (Objects.isNull(adminSaveForm.getId()))
      admin = adminMapper.selectById(adminSaveForm.getId());
    if (admin == null)
      admin = new Admin();
    if (StrUtil.isBlank(adminSaveForm.getPassword())) adminSaveForm.setPassword(dict.getInitPassword());
    //2.设置新参数并插入数据库
    admin.setPassword(SecureUtil.md5(SecureUtil.md5(adminSaveForm.getPassword()) + adminSaveForm.getUsername()))
      .setId( adminSaveForm.getId())
      .setUsername(adminSaveForm.getUsername())
      .setNickName(adminSaveForm.getNickName())
      .setUpdateTime(DateUtil.date())
      .setCreateTime(DateUtil.date());
    if (Objects.isNull(adminSaveForm.getId()))
      adminMapper.insert(admin);
    else if (!SqlHelper.retBool(adminMapper.updateById(admin)))
      adminMapper.insertAll(admin);
    //3.更新用户角色
    if (CollectionUtil.isNotEmpty(adminSaveForm.getRoleIds()))
      adminService.updateRoles(admin.getId(), adminSaveForm.getRoleIds());
    return admin;
  }

  @Transactional
  public void updateRoles(Long adminId, List<Integer> roleIds) {
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
   *
   * @param adminQuery
   * @return
   */
  public Page<AdminVo> findAdmin(AdminQuery adminQuery) {
    Page page = new Page(adminQuery.getPage().getCurrent(), adminQuery.getPage().getSize());
//    adminMapper.find(page, 0, "admin");
    return commonService.find(adminQuery, (p, q) -> adminMapper.find(p, adminQuery.getDisabled(), adminQuery.getText(), adminQuery.getAsc()));
  }

  public List<Admin> getAdmin() {
    return adminMapper.selectList(null);
  }

  @Transactional
  public void adminDisabled(String id) {
    Admin admin = adminIsExist(id);
    admin.setDisabled(Objects.equals(admin.getDisabled(), DisabledEnum.ENABLED.getCode()) ?
      DisabledEnum.DISABLED.getCode() :
      DisabledEnum.ENABLED.getCode());
    commonService.insertOrUpdate(admin, adminMapper);
  }

  @Transactional
  public void adminDelete(String id) {
    Admin admin = adminIsExist(id);
    adminMapper.deleteById(admin);
    commonMapper.deleteJoinTable(
      Kv.by(SqlFactory.TABLE, "t_admin_role")
        .set(SqlFactory.AK, "ADMIN_ID")
        .set(SqlFactory.AK_VALUE, id)
    );
  }

  private Admin adminIsExist(String id) {
    Admin admin = adminMapper.selectById(id);
    if (admin == null) throw new LogicException(ResultEnum.DATA_ERROR, "id为" + id + "的后管用户不存在");
    return admin;
  }
}
