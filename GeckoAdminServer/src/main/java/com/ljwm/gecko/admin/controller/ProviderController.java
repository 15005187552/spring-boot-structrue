package com.ljwm.gecko.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.config.LikeFormatter;
import com.ljwm.gecko.base.model.dto.ConfirmProviderDto;
import com.ljwm.gecko.base.model.dto.ConfirmProviderInfoDto;
import com.ljwm.gecko.base.model.dto.ProviderQueryDto;
import com.ljwm.gecko.base.model.dto.ServeDto;
import com.ljwm.gecko.base.model.form.ServicePathForm;
import com.ljwm.gecko.base.model.form.ServiceTypeQuery;
import com.ljwm.gecko.base.model.vo.ProviderServicesVo;
import com.ljwm.gecko.base.model.vo.ProviderVo;
import com.ljwm.gecko.base.model.vo.ServeSimpleVo;
import com.ljwm.gecko.base.model.vo.ServiceVo;
import com.ljwm.gecko.base.service.ProviderService;
import com.ljwm.gecko.base.service.ServiceTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/provider")
@Api(tags = "服务商铺 API")
@SuppressWarnings("all")
public class ProviderController extends BaseController {

  @Autowired
  private ServiceTypeService serviceTypeService;

  @Autowired
  private ProviderService providerService;

  @PostMapping("findByPage")
  @ApiOperation("查询服务商---带分页")
  public Result<Page<ProviderVo>> findByPage(@RequestBody ProviderQueryDto providerQueryDto) {
    return success(providerService.findByPage(providerQueryDto));
  }

  @PostMapping("confirmProvider")
  @ApiOperation("审核服务商入驻申请")
  public Result<List<ProviderServicesVo>> confirmProvider(@RequestBody ConfirmProviderDto confirmProviderDto) {
    return success(providerService.confirmProvider(confirmProviderDto));
  }

  @PostMapping("confirmProviderInfo")
  @ApiOperation("服务商审核服务商基本信息")
  public Result confirmProviderInfo(@RequestBody ConfirmProviderInfoDto confirmProviderInfoDto) {
    confirmProviderInfoDto.setValidatorId(SecurityKit.currentId());
    providerService.confirmProviderInfo(confirmProviderInfoDto);
    return success();
  }

  @GetMapping("find")
  @ApiOperation("获取服务类型树")
  public Result<List<ServiceVo>> find() {
    return success(serviceTypeService.find());
  }

  @PostMapping("findContent")
  @ApiOperation("获取服务类型树")
  public Result<Page<ServiceVo>> findContent(@RequestBody ServiceTypeQuery query) {
    LikeFormatter.set(query.getText());
    return success(serviceTypeService.findByPage(query));
  }

  @PostMapping("savePath")
  @ApiOperation("服务类型头像保存")
  public Result savePath(@RequestBody ServicePathForm form) {
    return success(serviceTypeService.savePath(form));
  }

  @PostMapping("save")
  @ApiOperation("保存服务类型")
  public Result<ServeSimpleVo> save(@RequestBody ServeDto serveDto) {
    return success(serviceTypeService.save(serveDto));
  }

  @GetMapping("typeDisabled/{id}")
  @ApiOperation("服务类型禁用")
  public Result typeDisabled(@PathVariable @Valid Integer id) {
    return success(serviceTypeService.disabled(id));
  }

  private static final Integer level = 1;

  @GetMapping("findTypeByLevel")
  @ApiOperation("获取所有等级为 1 的服务类型")
  public Result findTypeByLevel() {
    return success(serviceTypeService.findTypeByLevel(level));
  }

  @GetMapping("saveCashDeposit")
  @ApiOperation("保存限额")
  public Result saveCashDeposit(@RequestParam Long providerId,@RequestParam BigDecimal cashDeposit) {
    return success(providerService.saveCashDeposit(providerId,cashDeposit));
  }

  @GetMapping("saveTop/{id}")
  @ApiOperation("置顶")
  public Result saveTop(@PathVariable Integer id){
    return success(serviceTypeService.saveTop(id));
  }
}
