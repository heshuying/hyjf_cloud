/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.auto.SellDailyDistributionMapper;
import com.hyjf.am.market.dao.model.auto.SellDailyDistribution;
import com.hyjf.am.market.dao.model.auto.SellDailyDistributionExample;
import com.hyjf.am.market.service.SellDailyDistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yaoyong
 * @version SellDailyDistributionServiceImpl, v0.1 2018/11/16 10:02
 */
@Service
public class SellDailyDistributionServiceImpl implements SellDailyDistributionService {

    @Resource
    private SellDailyDistributionMapper sellDailyDistributionMapper;

    @Override
    public List<SellDailyDistribution> selectDistributionList() {
        SellDailyDistributionExample example = new SellDailyDistributionExample();
        SellDailyDistributionExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(1);
        List<SellDailyDistribution> list = sellDailyDistributionMapper.selectByExample(example);
        return list;
    }
}
