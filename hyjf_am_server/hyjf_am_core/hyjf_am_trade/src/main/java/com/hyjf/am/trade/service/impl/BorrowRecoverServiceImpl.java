/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowRecoverMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowRepayPlanMapper;
import com.hyjf.am.trade.dao.mapper.auto.TenderAgreementMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BorrowRecoverService;
import com.hyjf.am.trade.service.BorrowRepayPlanService;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jijun
 * @date 20180630
 */
@Service
public class BorrowRecoverServiceImpl implements BorrowRecoverService {

    @Autowired
    TenderAgreementMapper tenderAgreementMapper;
    @Autowired
    BorrowRecoverMapper borrowRecoverMapper;

    @Override
    public BorrowRecover selectBorrowRecoverByTenderNid(String tenderAgreementID) {
        TenderAgreement tenderAgreement = this.tenderAgreementMapper.selectByPrimaryKey(Integer.valueOf(tenderAgreementID));
        String tenderNid = tenderAgreement.getTenderNid();
        BorrowRecoverExample example = new BorrowRecoverExample();
        example.createCriteria().andNidEqualTo(tenderNid);
        List<BorrowRecover> borrowRecoverList = this.borrowRecoverMapper.selectByExample(example);
        if (borrowRecoverList!= null && borrowRecoverList.size() >0){
            return borrowRecoverList.get(0);
        }
        return null;
    }

    @Override
    public int updateBorrowRecover(BorrowRecover borrowRecover) {
        return this.borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecover);
    }


}
