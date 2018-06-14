/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.HjhInstConfigMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhLabelMapper;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfigExample;
import com.hyjf.am.trade.dao.model.auto.HjhLabel;
import com.hyjf.am.trade.dao.model.auto.HjhLabelExample;
import com.hyjf.am.trade.service.HjhPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuqiang
 * @version HjhPlanServiceImpl, v0.1 2018/6/13 17:23
 */
@Service
public class HjhPlanServiceImpl implements HjhPlanService {

    @Autowired
    private HjhInstConfigMapper hjhInstConfigMapper;

    @Autowired
    private HjhLabelMapper hjhLabelMapper;

    @Override
    public List<HjhInstConfig> selectHjhInstConfigByInstCode(String instCode) {
        HjhInstConfigExample example = new HjhInstConfigExample();
        HjhInstConfigExample.Criteria cra = example.createCriteria();
        cra.andInstCodeEqualTo(instCode);
        cra.andDelFlgEqualTo(0);
        List<HjhInstConfig> list = this.hjhInstConfigMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<HjhLabel> seleHjhLabelByBorrowStyle(String borrowStyle) {
        HjhLabelExample example = new HjhLabelExample();
        HjhLabelExample.Criteria cra = example.createCriteria();

        cra.andDelFlgEqualTo(0);
        cra.andLabelStateEqualTo(1);
        cra.andBorrowStyleEqualTo(borrowStyle);
        cra.andIsCreditEqualTo(0); // 原始标
        cra.andIsLateEqualTo(0); // 是否逾期
        example.setOrderByClause(" update_time desc ");

        List<HjhLabel> list = this.hjhLabelMapper.selectByExample(example);
        return list;
    }
}
