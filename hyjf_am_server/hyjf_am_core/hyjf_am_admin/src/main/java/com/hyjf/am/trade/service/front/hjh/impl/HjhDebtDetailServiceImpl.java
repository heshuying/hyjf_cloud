/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.hjh.impl;

import com.hyjf.am.trade.dao.mapper.auto.HjhDebtDetailMapper;
import com.hyjf.am.trade.dao.model.auto.HjhDebtDetail;
import com.hyjf.am.trade.dao.model.auto.HjhDebtDetailExample;
import com.hyjf.am.trade.service.front.hjh.HjhDebtDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjun
 * @version HjhDebtDetailServiceImpl, v0.1 2018/7/17 10:10
 */
@Service
public class HjhDebtDetailServiceImpl implements HjhDebtDetailService {
    @Autowired
    HjhDebtDetailMapper hjhDebtDetailMapper;
    /**
     * 查询债券详情
     * @param accedeOrderId
     * @return
     */
    @Override
    public List<HjhDebtDetail> selectHjhDebtDetailListByAccedeOrderId(String accedeOrderId){
        HjhDebtDetailExample example = new HjhDebtDetailExample();
        example.createCriteria().andPlanOrderIdEqualTo(accedeOrderId);
        return hjhDebtDetailMapper.selectByExample(example );
    }
}
