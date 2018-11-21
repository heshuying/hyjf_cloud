/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.controller;

import com.hyjf.am.market.dao.model.auto.SellDailyDistribution;
import com.hyjf.am.market.service.SellDailyDistributionService;
import com.hyjf.am.response.market.SellDailyDistributionResponse;
import com.hyjf.am.vo.admin.SellDailyDistributionVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author yaoyong
 * @version SellDailyDistributionController, v0.1 2018/11/16 9:59
 */
@RestController
@RequestMapping("/am-market/daily_distribution")
public class SellDailyDistributionController {

    @Autowired
    private SellDailyDistributionService dailyDistributionService;

    @RequestMapping("/selectdistribution")
    public SellDailyDistributionResponse selectDistribution() {
        SellDailyDistributionResponse response = new SellDailyDistributionResponse();
        List<SellDailyDistribution> distributions = dailyDistributionService.selectDistributionList();
        if (!CollectionUtils.isEmpty(distributions)) {
            response.setResultList(CommonUtils.convertBeanList(distributions, SellDailyDistributionVO.class));
        }
        return response;
    }
}
