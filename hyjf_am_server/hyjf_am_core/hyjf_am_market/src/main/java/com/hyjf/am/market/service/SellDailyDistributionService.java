/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.SellDailyDistribution;

import java.util.List;

/**
 * @author yaoyong
 * @version SellDailyDistributionService, v0.1 2018/11/16 10:01
 */
public interface SellDailyDistributionService {

    /**
     * 获取后台发送邮件配置
     * @return
     */
    List<SellDailyDistribution> selectDistributionList();
}
