package com.hyjf.am.trade.service.admin.finance.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyjf.am.resquest.admin.TenderCommissionRequest;
import com.hyjf.am.trade.dao.mapper.auto.TenderCommissionMapper;
import com.hyjf.am.trade.dao.model.auto.TenderCommission;
import com.hyjf.am.trade.dao.model.auto.TenderCommissionExample;
import com.hyjf.am.trade.service.admin.finance.TenderCommissionService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.CommonUtils;

/**
 * @version TenderCommissionServiceImpl, v0.1 2018/8/7 9:39
 * @Author: Zha Daojian
 */
@Service
public class TenderCommissionServiceImpl  extends BaseServiceImpl implements TenderCommissionService {

    @Autowired
    private TenderCommissionMapper tenderCommissionMapper;

    /**
     * 插入
     * @auth Zha Daojian
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertTenderCommission(TenderCommissionRequest request) {
        TenderCommission tenderCommission = CommonUtils.convertBean(request,TenderCommission.class);
        return tenderCommissionMapper.insertSelective(tenderCommission)>0;
    }

    /**
     * 根据BorrowTender表的id和TenderType查询条数
     * @auth Zha Daojian
     * @param
     * @return
     */
    @Override
    public int countTenderCommissionByTenderIdAndTenderType(TenderCommissionRequest request) {
        TenderCommissionExample tenderCommissionExample = new TenderCommissionExample();
        TenderCommissionExample.Criteria criteria = tenderCommissionExample.createCriteria();
        criteria.andTenderIdEqualTo(request.getTenderId());
        criteria.andBorrowNidEqualTo(request.getBorrowNid());
        criteria.andTenderTimeEqualTo(1);
        return tenderCommissionMapper.countByExample(tenderCommissionExample);
    }
}
