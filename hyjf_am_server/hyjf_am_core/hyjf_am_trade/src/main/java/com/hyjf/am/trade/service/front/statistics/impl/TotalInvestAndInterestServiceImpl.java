/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.statistics.impl;

import com.hyjf.am.trade.dao.mapper.customize.trade.HjhPlanCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.OperationReportJobCustomizeMapper;
import com.hyjf.am.trade.service.front.statistics.TotalInvestAndInterestService;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version TotalInvestAndInterestServiceImpl, v0.1 2018/7/31 11:26
 */
@Service
public class TotalInvestAndInterestServiceImpl implements TotalInvestAndInterestService {
    @Autowired
    private OperationReportJobCustomizeMapper customizeMapper;
    @Autowired
    private HjhPlanCustomizeMapper hjhPlanCustomizeMapper;

    @Override
    public TotalInvestAndInterestVO getTotalInvestAndInterest() {
        TotalInvestAndInterestVO vo = new TotalInvestAndInterestVO();
        // 累计交易笔数(实时)
        int totalInvestNum = customizeMapper.countTotalInvestNum();
        // 累计交易总额(实时)
        BigDecimal totalInvestAmount = customizeMapper.countTotalInvestAmount();
        // 累计为用户赚取收益(实时)
        BigDecimal totalInterestAmount = customizeMapper.countTotalInterestAmount();
        vo.setTotalInvestNum(totalInvestNum);
        vo.setTotalInvestAmount(totalInvestAmount);
        vo.setTotalInterestAmount(totalInterestAmount);

        List<Map<String, Object>> list = hjhPlanCustomizeMapper.searchPlanStatisticData();

        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> map = list.get(0);
            BigDecimal interestTotal = (BigDecimal) map.get("interest_total");
            BigDecimal accountTotal = (BigDecimal) map.get("accede_account_total");
            Long accedeTimes = (Long) map.get("accede_times");
            vo.setHjhTotalInterestAmount(interestTotal);
            vo.setHjhTotalInvestAmount(accountTotal);
            vo.setHjhTotalInvestNum(accedeTimes.intValue());
        }
        return vo;
    }
}
