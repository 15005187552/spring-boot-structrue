package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.*;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.enums.SpecialType;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.client.dao.CalTaxDao;
import com.ljwm.gecko.client.dao.CalcDao;
import com.ljwm.gecko.client.model.dto.CalcForm;
import com.ljwm.gecko.client.model.dto.EvaluateForm;
import com.ljwm.gecko.client.model.dto.EvaluateForm.IncomeDto;
import com.ljwm.gecko.client.model.dto.NoticeQuery;
import com.ljwm.gecko.client.model.vo.AdviceVo;
import com.ljwm.gecko.client.model.vo.CalcVo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Janiffy
 * @date 2018/9/7 9:44
 */
@Service
public class CalcService {

  @Autowired
  CalcDao calcDao;

  @Autowired
  CalTaxMapper calTaxMapper;

  @Autowired
  CalTaxDao calTaxDao;

  @Autowired
  GuestMapper guestMapper;

  @Autowired
  SpecialDeductionMapper specialDeductionMapper;

  @Autowired
  NoticeMapper noticeMapper;

  @Autowired
  CommonService commonService;

  @Autowired
  ProtocolMapper protocolMapper;

  @Transactional
  public Result calc(CalcForm calcForm) {
    Integer code = calcForm.getCode();
    BigDecimal enterMoney = calcForm.getMoney();
    List<CityItem> list = calcDao.calc(code);
    BigDecimal tax = BigDecimal.ZERO;
    BigDecimal money;
    BigDecimal singleMoney = BigDecimal.ZERO;
    //五险一金扣除项
    if(CollectionUtil.isNotEmpty(list)) {
      BigDecimal upLimit;
      BigDecimal lowLimit;
      BigDecimal personPer;
      Integer perType;
      Long itemId = null;
      SpecialDeduction specialDeduction = specialDeductionMapper.selectOne(new QueryWrapper<SpecialDeduction>().like(SpecialDeduction.NAME, "公积金"));
      if (specialDeduction != null) {
        itemId = specialDeduction.getId();
      }
      for (CityItem cityItem : list) {
        upLimit = cityItem.getUpperLimit();
        lowLimit = cityItem.getLowerLimit();
        personPer = cityItem.getPersonPer();
        perType = cityItem.getPerType();
        if (perType == SpecialType.PERCENT.getCode()) {
          if (enterMoney.compareTo(upLimit) > 0) {
            if (cityItem.getItemType().equals(itemId)){
              singleMoney = upLimit.multiply(personPer);
            }
              tax = tax.add(upLimit.multiply(personPer));
          } else if (enterMoney.compareTo(lowLimit) < 0) {
            if (cityItem.getItemType().equals(itemId)){
              singleMoney = lowLimit.multiply(personPer);
            }
            tax = tax.add(lowLimit.multiply(personPer));
          } else {
            if (cityItem.getItemType().equals(itemId)){
              singleMoney = enterMoney.multiply(personPer);
            }
            tax = tax.add(enterMoney.multiply(personPer));
          }
        } else {
          tax = tax.add(personPer);
        }
      }
    }
    money = enterMoney.subtract(tax);
    BigDecimal oldTax, newTax;
    BigDecimal oldPort = new BigDecimal("3500");
    BigDecimal newPort = new BigDecimal("5000");
    if(money.compareTo(new BigDecimal("83500"))>=0){
      oldTax = (money.subtract(oldPort).multiply(new BigDecimal("0.45")).subtract(new BigDecimal("13505")));
    } else if(money.compareTo(new BigDecimal("58500"))>=0){
      oldTax = (money.subtract(oldPort).multiply(new BigDecimal("0.35")).subtract(new BigDecimal("5505")));
    } else if(money.compareTo(new BigDecimal("38500"))>=0){
      oldTax = (money.subtract(oldPort).multiply(new BigDecimal("0.30")).subtract(new BigDecimal("2755")));
    } else if(money.compareTo(new BigDecimal("12500"))>=0){
      oldTax = (money.subtract(oldPort).multiply(new BigDecimal("0.25")).subtract(new BigDecimal("1005")));
    } else if(money.compareTo(new BigDecimal("8000"))>=0){
      oldTax = (money.subtract(oldPort).multiply(new BigDecimal("0.20")).subtract(new BigDecimal("555")));
    } else if(money.compareTo(new BigDecimal("5000"))>=0){
      oldTax = (money.subtract(oldPort).multiply(new BigDecimal("0.10")).subtract(new BigDecimal("105")));
    } else if(money.compareTo(new BigDecimal("3500"))>=0){
      oldTax = (money.subtract(oldPort).multiply(new BigDecimal("0.03")));
    } else{
      oldTax = BigDecimal.ZERO;
    }
    newTax = calNew(money, newPort);
    BigDecimal difference = oldTax.subtract(newTax);
    BigDecimal percent = BigDecimal.ZERO;
    if (oldTax != BigDecimal.ZERO) {
      percent = difference.divide(oldTax, 4, BigDecimal.ROUND_HALF_UP);
    }
    if(difference.compareTo(BigDecimal.ZERO)>=0){
      String id;
      if (SecurityKit.currentId() instanceof String){
        id = SecurityKit.currentId();
      } else {
        id = SecurityKit.currentId().toString();
      }
      String guestId;
      if(LoginType.codeOf(LoginInfoHolder.getLoginType()) == LoginType.GUEST){
        guestId = id;
      }else {
        Map<String, Object> map = new HashedMap();
        map.put("MEMBER_ID", id);
        List<Guest> guestList = guestMapper.selectByMap(map);
        if(CollectionUtil.isNotEmpty(list)){
          guestId = guestList.get(0).getGuestId();
        }else {
          guestId = id;
        }
      }
      CalTax calTax = new CalTax(guestId, code, enterMoney, oldTax, newTax, new Date(), new Date());
      calTaxDao.insertOrUpdate(calTax, difference);
    }
    CalcVo calcVo = new CalcVo(percent, difference, oldTax, newTax, tax.subtract(singleMoney));
    return Result.success(calcVo);
  }

  public BigDecimal calNew(BigDecimal money, BigDecimal newPort){
    BigDecimal newTax;
    if(money.compareTo(new BigDecimal("85000"))>=0){
      newTax = (money.subtract(newPort).multiply(new BigDecimal("0.45")).subtract(new BigDecimal("15160")));
    } else if(money.compareTo(new BigDecimal("60000"))>=0){
      newTax = (money.subtract(newPort).multiply(new BigDecimal("0.35")).subtract(new BigDecimal("7160")));
    } else if(money.compareTo(new BigDecimal("40000"))>=0){
      newTax = (money.subtract(newPort).multiply(new BigDecimal("0.30")).subtract(new BigDecimal("4410")));
    } else if(money.compareTo(new BigDecimal("30000"))>=0){
      newTax = (money.subtract(newPort).multiply(new BigDecimal("0.25")).subtract(new BigDecimal("2660")));
    } else if(money.compareTo(new BigDecimal("17000"))>=0){
      newTax = (money.subtract(newPort).multiply(new BigDecimal("0.20")).subtract(new BigDecimal("1410")));
    } else if(money.compareTo(new BigDecimal("8000"))>=0){
      newTax = (money.subtract(newPort).multiply(new BigDecimal("0.10")).subtract(new BigDecimal("210")));
    } else if(money.compareTo(new BigDecimal("5000"))>=0){
      newTax = (money.subtract(newPort).multiply(new BigDecimal("0.03")));
    } else {
      newTax = BigDecimal.ZERO;
    }
    return newTax;
  }

  public Result redPackage() {
    return Result.success(calTaxMapper.redPackage());
  }

  public Result find(NoticeQuery query) {
    return Result.success(commonService.find(query,(p,q) -> noticeMapper.find(p, BeanUtil.beanToMap(q))));
  }

  public Result evaluateTax(EvaluateForm evaluateForm) {
    List<IncomeDto> incomeList = evaluateForm.getIncomeList();
    List<IncomeDto> specialList = evaluateForm.getSpecialList();
    List<IncomeDto> specialAddList = evaluateForm.getSpecialAddList();
    BigDecimal income = BigDecimal.ZERO;
    BigDecimal special = BigDecimal.ZERO;
    BigDecimal specialAdd = BigDecimal.ZERO;
    for (IncomeDto incomeDto : incomeList){
      income = income.add(incomeDto.getMoney());
    }
    for (IncomeDto incomeDto : specialList){
      special = special.add(incomeDto.getMoney());
    }
    for (IncomeDto incomeDto : specialAddList){
      specialAdd = specialAdd.add(incomeDto.getMoney());
    }
    BigDecimal newTax = calNew(income.subtract(special).subtract(specialAdd), new BigDecimal(5000));
    AdviceVo adviceVo = new AdviceVo().setTax(newTax).setAfterTax(income.subtract(special).subtract(specialAdd).subtract(newTax));
    return Result.success(adviceVo);
  }

  public Result protocol(Integer code) {
    Protocol protocol = protocolMapper.selectOne(new QueryWrapper<Protocol>().eq(Protocol.CODE, code));
    return Result.success(protocol);
  }
}
