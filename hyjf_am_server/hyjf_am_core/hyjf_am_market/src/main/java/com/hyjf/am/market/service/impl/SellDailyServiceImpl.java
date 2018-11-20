/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.auto.SellDailyMapper;
import com.hyjf.am.market.dao.mapper.customize.market.SellDailyCustomizeMapper;
import com.hyjf.am.market.dao.model.auto.SellDaily;
import com.hyjf.am.market.dao.model.auto.SellDailyExample;
import com.hyjf.am.market.service.SellDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yaoyong
 * @version SellDailyServiceImpl, v0.1 2018/11/16 17:54
 */
@Service
public class SellDailyServiceImpl implements SellDailyService {

    @Resource
    private SellDailyMapper sellDailyMapper;
    @Resource
    private SellDailyCustomizeMapper sellDailyCustomizeMapper;

    @Override
    public List<SellDaily> selectDailyByDateStr(String dateStr) {
        SellDailyExample example = new SellDailyExample();
        SellDailyExample.Criteria criteria = example.createCriteria();
        criteria.andDateStrEqualTo(dateStr);
        example.setOrderByClause(" id asc ");
        List<SellDaily> list = sellDailyMapper.selectByExample(example);
        return list;
    }

    @Override
    public SellDaily selectOCSum(String formatDateStr) {
        return sellDailyCustomizeMapper.selectOCSum(formatDateStr);
    }

    @Override
    public SellDaily selectPrimaryDivisionSum(String dateStr, int drawOrder) {
        return sellDailyCustomizeMapper.selectPrimaryDivisionSum(dateStr, drawOrder);
    }

    @Override
    public SellDaily selectAllSum(String dateStr) {
        return sellDailyCustomizeMapper.selectAllSum(dateStr);
    }
}
