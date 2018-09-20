/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.hjh.impl;

import com.hyjf.am.trade.dao.model.auto.HjhPlanBorrowTmp;
import com.hyjf.am.trade.dao.model.auto.HjhPlanBorrowTmpExample;
import com.hyjf.am.trade.service.front.hjh.HjhPlanBorrowTmpService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author liubin
 * @version HjhPlanBorrowTmpServiceImpl, v0.1 2018/7/4 19:43
 */
@Service
public class HjhPlanBorrowTmpServiceImpl extends BaseServiceImpl implements HjhPlanBorrowTmpService {
    @Override
    public int insertHjhPlanBorrowTmp(HjhPlanBorrowTmp hjhPlanBorrowTmp) {
        return this.hjhPlanBorrowTmpMapper.insertSelective(hjhPlanBorrowTmp);
    }

    @Override
    public int deleteHjhPlanBorrowTmp(HjhPlanBorrowTmp hjhPlanBorrowTmp) {
        HjhPlanBorrowTmpExample hjhPlanBorrowTmpExample = new HjhPlanBorrowTmpExample();
        HjhPlanBorrowTmpExample.Criteria crt = hjhPlanBorrowTmpExample.createCriteria();
        crt.andAccedeOrderIdEqualTo(hjhPlanBorrowTmp.getAccedeOrderId());
        crt.andBorrowNidEqualTo(hjhPlanBorrowTmp.getBorrowNid());
        crt.andUserIdEqualTo(hjhPlanBorrowTmp.getUserId());
        return this.hjhPlanBorrowTmpMapper.deleteByExample(hjhPlanBorrowTmpExample);
    }

    @Override
    public int updateHjhPlanBorrowTmpByPK(HjhPlanBorrowTmp hjhPlanBorrowTmp) {
        return this.hjhPlanBorrowTmpMapper.updateByPrimaryKeySelective(hjhPlanBorrowTmp);
    }

    @Override
    public int updateHjhPlanBorrowTmp(HjhPlanBorrowTmp hjhPlanBorrowTmp) {
        HjhPlanBorrowTmpExample example = new HjhPlanBorrowTmpExample();
        HjhPlanBorrowTmpExample.Criteria crt = example.createCriteria();
        crt.andAccedeOrderIdEqualTo(hjhPlanBorrowTmp.getAccedeOrderId());
        crt.andBorrowNidEqualTo(hjhPlanBorrowTmp.getBorrowNid());
        return this.hjhPlanBorrowTmpMapper.updateByExampleSelective(hjhPlanBorrowTmp, example);
    }
}
