package com.ljwm.gecko.base.model.bean;

import cn.hutool.core.collection.CollectionUtil;
import com.ljwm.gecko.base.entity.Function;
import com.ljwm.gecko.base.model.dto.FunctionDto;
import com.ljwm.gecko.base.model.dto.RoleDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yunqisong on 2018/1/17/017.
 */
@Data
@Accessors(chain = true)
@ApiModel("菜单树")
public class FunctionTree {

  @ApiModelProperty("菜单的id")
  private Long Id;

  @ApiModelProperty("名称")
  private String title;

  @ApiModelProperty("图标")
  private String icon;

  @ApiModelProperty("是否展示")
  private Integer isShow;

  @ApiModelProperty("路由")
  private String url;

  @ApiModelProperty("子菜单")
  private List<FunctionTree> children;

  public FunctionTree(Function function) {
    this
      .setId(function.getId())
      .setIcon(function.getIcon())
      .setIsShow(function.getIsShow())
      .setTitle(function.getTitle())
      .setUrl(function.getUrl())
      .setChildren(new LinkedList<>());
  }

//  public static List<FunctionTree> createdByTargetRoleId(Integer roleid) {
//    RoleMapper roleMapper = SpringUtil.getBean(RoleMapper.class);
//    Role role = roleMapper.findById(roleid);
//    return (role == null || CollectionUtil.isEmpty(role.getFunctions())) ? Collections.EMPTY_LIST :
//      role.getFunctions()
//        .stream()
//        .filter(function -> function.getParent() == null)
//        .map(FunctionTree::new)
//        .map(root -> root.setChildren(fillTree(root, role.getFunctions())))
//        .collect(Collectors.toList());
//  }


  private static List<FunctionTree> fillTree(FunctionTree root, List<FunctionDto> has) {
    return (root == null || root.getId() == null || CollectionUtil.isEmpty(has)) ? Collections.EMPTY_LIST :
      has.stream()
        .filter(function -> Objects.equals(function.getParentId(), root.getId()))
        .map(FunctionTree::new)
        .map(child -> child.setChildren(fillTree(child, has)))
        .collect(Collectors.toList());
  }

  public static List<FunctionTree> createByRoles(List<RoleDto> roles) {
    LinkedHashMap<Long, FunctionDto> ret = new LinkedHashMap<>();
    roles.forEach(role -> role.getFunctions().forEach(function -> ret.put(function.getId(), function)));
    List<FunctionDto> has = ret.values().stream().sorted((f1, f2) -> (f1.getSort() - f2.getSort())).collect(Collectors.toCollection(LinkedList::new));
    return has
      .stream()
      .filter(function -> function.getParent() == null)
      .map(FunctionTree::new)
      .map(root -> root.setChildren(fillTree(root, has)))
      .collect(Collectors.toList());
  }


//  public static List<Integer> genWithOutFullByRoleId(Integer roleid) {
//    RoleMapper roleMapper = SpringUtil.getBean(RoleMapper.class);
//    Role role = roleMapper.findById(roleid);
//    // 获取一颗满树
//    FunctionMapper functionMapper = SpringUtil.getBean(FunctionMapper.class);
//    Map<Integer, FunctionTree> fullMapTree = genMapTree(functionMapper.tree(Kv.by("TYPE", role.getType() == 0 ? 1 : 2)), new HashMap<>());
//    List<Integer> ret = new LinkedList<>();
//    if (!CollectionUtil.isEmpty(role.getFunctions()))
//      role.getFunctions()
//        .forEach(function -> {
//          if (CollectionUtil.isEmpty(function.getChildren())) ret.add(function.getFunctionId());
//          else {
//            FunctionTree root = fullMapTree.get(function.getFunctionId());
//            if (root == null) ret.add(function.getFunctionId());
//            else {
//              List<Integer> fullTrees = root.getChildren().stream().map(FunctionTree::getFunctionId).collect(Collectors.toList());
//              List<Integer> lackTrees = role.getFunctions().stream()
//                .filter(f -> Objects.equals(f.getParentId(), function.getFunctionId()))
//                .map(Function::getFunctionId).collect(Collectors.toList());
//              if (lackTrees.containsAll(fullTrees))
//                ret.add(function.getFunctionId());
//            }
//          }
//        });
//    return ret;
//  }

  public static Map<String, FunctionTree> genMapTree(List<FunctionTree> tree, Map<String, FunctionTree> fullMapTree) {
    if (fullMapTree == null) fullMapTree = new HashMap<>();
    if (CollectionUtil.isEmpty(tree)) return fullMapTree;
    for (FunctionTree functionTree : tree) {
      fullMapTree.put("" + functionTree.getId(), functionTree);
      genMapTree(functionTree.getChildren(), fullMapTree);
    }
    return fullMapTree;
  }


//  public static List<Integer> functionIds(List<Integer> ids, List<Integer> total) {
//    FunctionMapper functionMapper = SpringUtil.getBean(FunctionMapper.class);
//    if (CollectionUtil.isEmpty(total)) total = new LinkedList<>();
//    total.addAll(ids);
//    if (!CollectionUtil.isEmpty(ids)) {
//      List<Integer> parents = functionMapper.findParentIds(Kv.by("children", ids));
//      functionIds(parents, total);
//    }
//    return total;
//  }


}
