package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.service.FundChangeStatisticsService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 资金变化统计 ServiceImpl
 * @Author : huanghui
 */
@Service
public class FundChangeStatisticsServiceImpl extends BaseServiceImpl implements FundChangeStatisticsService {

    @Override
    public Integer countRechargeMoney(Map<String, Object> params) {
        return this.fundChangeStatisticsCustomizeMapper.countRechargeMoney(params);
    }

    @Override
    public Integer countInvestmentMoney(Map<String, Object> params) {
        return this.fundChangeStatisticsCustomizeMapper.countInvestmentMoney(params);
    }

    @Override
    public Integer countInvestmentCreditTenderMoney(Map<String, Object> params) {
        return this.fundChangeStatisticsCustomizeMapper.countInvestmentCreditTenderMoney(params);
    }

    @Override
    public Integer countInvestmentHjhCreditTenderMoney(Map<String, Object> params) {
        return this.fundChangeStatisticsCustomizeMapper.countInvestmentHjhCreditTenderMoney(params);
    }

    @Override
    public Integer countAllAccount() {
        return this.fundChangeStatisticsCustomizeMapper.countAllAccount();
    }

    @Override
    public Integer getNumberOfInvestors(Map<String, Object> params) {
        return this.fundChangeStatisticsCustomizeMapper.getNumberOfInvestors(params);
    }

    @Override
    public Integer countTodayNewRechargeNum(Map<String, Object> params) {
        return this.fundChangeStatisticsCustomizeMapper.countTodayNewRechargeNum(params);
    }
}
