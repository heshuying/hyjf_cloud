package com.hyjf.am.config.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.hyjf.am.config.dao.mapper.auto.FeerateModifyLogMapper;
import com.hyjf.am.config.dao.model.auto.FeerateModifyLog;
import com.hyjf.am.config.service.FeerateModifyLogService;
import com.hyjf.am.resquest.admin.FinmanChargeNewRequest;
import com.hyjf.common.util.GetDate;

/**
 * @author xiehuili on 2018/8/14.
 */
@Service
public class FeerateModifyLogServiceImpl implements FeerateModifyLogService {

    @Autowired
    private FeerateModifyLogMapper feerateModifyLogMapper;
    /**
     * 添加费率配置日志
     * @author xiehuili
     * @return
     */
    @Override
    public int insertFeerateModifyLog(@RequestBody FinmanChargeNewRequest form){
        FeerateModifyLog record = new FeerateModifyLog();
        record.setInstCode(form.getInstCode());
        record.setAssetType(form.getAssetType());
        record.setBorrowPeriod(form.getManChargeTime());
        if ("endday".equals(form.getManChargeTimeType())) {
            // 天标
            record.setBorrowStyle("天标");
        } else if ("month".equals(form.getManChargeTimeType())) {
            // 月标
            record.setBorrowStyle("月标");
        }
        record.setBorrowApr(new BigDecimal(form.getAutoBorrowApr()));
        record.setServiceFee(form.getChargeRate());
        record.setManageFee(form.getManChargeRate());
        record.setRevenueDiffRate(form.getReturnRate());
        record.setLateInterestRate(form.getLateInterest());
        record.setLateFreeDays(form.getLateFreeDays());
        record.setStatus(form.getStatus());
        record.setModifyType(1);//修改类型 0:全部 1：增加 2:修改 3:删除
//        AdminSystem adminSystem = (AdminSystem) SessionUtils.getSession(CustomConstants.LOGIN_USER_INFO);
//        record.setCreateUser(Integer.parseInt(adminSystem.getId()));
        record.setCreateTime(new Date());//record.setCreateTime(GetDate.getMyTimeInMillis());
        return feerateModifyLogMapper.insertSelective(record);
    }
    /**
     * 修改费率配置日志
     * @author xiehuili
     * @return
     */
    @Override
    public int updateFeerateModifyLog(@RequestBody FinmanChargeNewRequest form){
        FeerateModifyLog record = new FeerateModifyLog();
        record.setInstCode(form.getInstCode());
        record.setAssetType(form.getAssetType());
        record.setBorrowPeriod(form.getManChargeTime());
        if ("endday".equals(form.getManChargeTimeType())) {
            // 天标
            record.setBorrowStyle("天标");
        } else if ("month".equals(form.getManChargeTimeType())) {
            // 月标
            record.setBorrowStyle("月标");
        }
        record.setBorrowApr(new BigDecimal(form.getAutoBorrowApr()));
        record.setServiceFee(form.getChargeRate());
        record.setManageFee(form.getManChargeRate());
        record.setRevenueDiffRate(form.getReturnRate());
        record.setLateInterestRate(form.getLateInterest());
        record.setLateFreeDays(form.getLateFreeDays());
        record.setStatus(form.getStatus());

        record.setModifyType(2);//修改类型 0:全部 1：增加 2:修改 3:删除

//        AdminSystem adminSystem = (AdminSystem) SessionUtils.getSession(CustomConstants.LOGIN_USER_INFO);
//        record.setCreateUser(Integer.parseInt(adminSystem.getId()));
        record.setCreateTime(new Date());
        return feerateModifyLogMapper.insertSelective(record);
    }
    /**
     * 删除费率配置日志
     * @author xiehuili
     * @return
     */
    @Override
    public int deleteFeerateModifyLog(@RequestBody FinmanChargeNewRequest form){
//        BorrowFinmanNewCharge borrowFinmanNewCharge = new BorrowFinmanNewCharge();
//        if (StringUtils.isNotEmpty(form.getManChargeCd())) {
//            borrowFinmanNewCharge = borrowFinmanNewChargeMapper.selectByPrimaryKey(form.getManChargeCd());
//        }
        FeerateModifyLog record = new FeerateModifyLog();

        record.setInstCode(form.getInstCode());
        record.setAssetType(form.getAssetType());
        record.setBorrowPeriod(form.getManChargeTime());

        if ("endday".equals(form.getManChargeTimeType())) {
            // 天标
            record.setBorrowStyle("天标");
        } else if ("month".equals(form.getManChargeTimeType())) {
            // 月标
            record.setBorrowStyle("月标");
        }

        record.setBorrowApr(new BigDecimal(0));
        record.setServiceFee("0");
        record.setManageFee("0");
        record.setRevenueDiffRate("0");
        record.setLateInterestRate("0");
        record.setLateFreeDays(0);

        record.setStatus(form.getStatus());

        record.setModifyType(3);//修改类型 0:全部 1：增加 2:修改 3:删除

//        AdminSystem adminSystem = (AdminSystem) SessionUtils.getSession(CustomConstants.LOGIN_USER_INFO);
//        record.setCreateUser(Integer.parseInt(adminSystem.getId()));
        int nowTime = GetDate.getNowTime10();
        record.setCreateTime(new Date());
        record.setDelFlag(1);//0 未删除 ，1 已删除
        return feerateModifyLogMapper.insertSelective(record);
    }
}
