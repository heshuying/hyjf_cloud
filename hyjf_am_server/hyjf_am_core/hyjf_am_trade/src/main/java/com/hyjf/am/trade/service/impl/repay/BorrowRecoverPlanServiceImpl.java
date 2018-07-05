/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.repay;

import com.hyjf.am.trade.dao.mapper.auto.BorrowRecoverMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowRecoverPlanMapper;
import com.hyjf.am.trade.dao.mapper.auto.TenderAgreementMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BorrowRecoverService;
import com.hyjf.am.trade.service.repay.BorrowRecoverPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 还款计划
 * @author hesy
 * @version BorrowRecoverPlanServiceImpl, v0.1 2018/6/26 18:15
 */
@Service
public class BorrowRecoverPlanServiceImpl implements BorrowRecoverPlanService {

    @Resource
    BorrowRecoverPlanMapper borrowRecoverPlanMapper;

    /**
     * 根据id检索BorrowRecoverPlan
     * @param id
     * @return
     */
    @Override
    public BorrowRecoverPlan selectRecoverPlanById(Integer id){
        return borrowRecoverPlanMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BorrowRecoverPlan> selectRecoverPlan(String borrowNid, Integer period){
        BorrowRecoverPlanExample example = new BorrowRecoverPlanExample();
        BorrowRecoverPlanExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        crt.andRecoverPeriodEqualTo(period);
        List<BorrowRecoverPlan> borrowRecovers = borrowRecoverPlanMapper.selectByExample(example);
        return borrowRecovers;
    }

}
