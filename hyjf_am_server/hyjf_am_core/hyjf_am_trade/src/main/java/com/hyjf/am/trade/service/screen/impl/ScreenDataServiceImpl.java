package com.hyjf.am.trade.service.screen.impl;

import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.trade.dao.mapper.auto.AccountListMapper;
import com.hyjf.am.trade.dao.mapper.auto.AccountMapper;
import com.hyjf.am.trade.dao.mapper.auto.RepaymentPlanMapper;
import com.hyjf.am.trade.dao.mapper.auto.UserOperateListMapper;
import com.hyjf.am.trade.dao.mapper.customize.ScreenYearMoneyCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.screen.ScreenDataService;
import com.hyjf.am.vo.trade.RepaymentPlanVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lisheng
 * @version ScreenDataServiceImpl, v0.1 2019/3/18 13:44
 */
@Service
public class ScreenDataServiceImpl implements ScreenDataService {
    @Autowired
    UserOperateListMapper userOperateListMapper;
    @Autowired
    AccountListMapper accountListMapper;
    @Autowired
    ScreenYearMoneyCustomizeMapper screenYearMoneyCustomizeMapper;
    @Autowired
    RepaymentPlanMapper repaymentPlanMapper;
    @Autowired
    AccountMapper accountMapper;
    @Override
    public Integer addUserOperateList(ScreenDataBean screenDataBean) {
        UserOperateList userOperateList = CommonUtils.convertBean(screenDataBean, UserOperateList.class);

        return userOperateListMapper.insertSelective(userOperateList);
    }

    @Override
    public BigDecimal findUserFreeMoney(Integer userId) {
        AccountExample accountExample = new AccountExample();
        AccountExample.Criteria criteria = accountExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<Account> accounts = accountMapper.selectByExample(accountExample);
        if (!CollectionUtils.isEmpty(accounts)) {
           return accounts.get(0).getBankBalance();
        }
        return null;
    }

    @Override
    public BigDecimal findYearMoney(Integer userId, String orderId, Integer productType,BigDecimal investMoney) {
        String productStyle = "";
        BigDecimal yearAmount = new BigDecimal(0);
        if (productType == 3) {
            productStyle = screenYearMoneyCustomizeMapper.queryPlanList(orderId, userId);
        } else {
            productStyle = screenYearMoneyCustomizeMapper.queryTenderList(orderId, userId);
        }
        if (StringUtils.isNotBlank(productStyle)) {
            if (productStyle.contains("个月")) {
                String number = StringUtils.substringBefore(productStyle, "个月");
                if (StringUtils.isEmpty(number)) {
                    return null;
                }
                yearAmount = investMoney.multiply(new BigDecimal(number)).divide(new BigDecimal(12), 4, BigDecimal.ROUND_HALF_UP);
            }
            //天=出借金额*天数/360 (产品要求按照360天计算)
            else if (productStyle.contains("天")) {
                String number = StringUtils.substringBefore(productStyle, "天");
                if (StringUtils.isEmpty(number)) {
                    return null;
                }
                yearAmount = investMoney.multiply(new BigDecimal(number)).divide(new BigDecimal(360), 4, BigDecimal.ROUND_HALF_UP);
            }
        }
        return yearAmount;
    }




    @Override
    public Integer updateRepayMoney(ScreenDataBean screenDataBean, Date startTime,Date endTime) {
        return screenYearMoneyCustomizeMapper.updateRepayMoney(screenDataBean,startTime,endTime);
    }

    @Override
    public RepaymentPlan findRepayUser(ScreenDataBean screenDataBean, Integer startTime, Integer endTime) {
        return screenYearMoneyCustomizeMapper.findRepayUser(screenDataBean,startTime,endTime);
    }

    @Override
    public Integer insertRepayUser(RepaymentPlan repaymentPlan) {
        return repaymentPlanMapper.insertSelective(repaymentPlan);
    }
}
