package com.ljwm.gecko.base.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ljwm.gecko.base.entity.Admin;
import com.ljwm.gecko.base.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class AdminVo extends Admin {

  private List<Role> roles;
}
