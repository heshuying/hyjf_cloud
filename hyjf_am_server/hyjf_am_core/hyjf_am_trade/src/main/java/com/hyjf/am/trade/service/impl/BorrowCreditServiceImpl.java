/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowCreditMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.BorrowCreditCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.dao.model.auto.BorrowCreditExample;
import com.hyjf.am.trade.service.BorrowCreditService;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowCreditServiceImpl, v0.1 2018/6/24 10:59
 */
@Service
public class BorrowCreditServiceImpl implements BorrowCreditService {

    @Autowired
    BorrowCreditMapper borrowCreditMapper;

    @Autowired
    BorrowCreditCustomizeMapper borrowCreditCustomizeMapper;


    @Override
    public List<BorrowCredit> selectBorrowCreditList() {
        BorrowCreditExample borrowCreditExample = new BorrowCreditExample();
        BorrowCreditExample.Criteria borrowCreditCra = borrowCreditExample.createCriteria();
        borrowCreditCra.andCreditStatusEqualTo(0);
        return this.borrowCreditMapper.selectByExample(borrowCreditExample);
    }

    @Override
    public Integer updateBorrowCredit(BorrowCreditVO borrowCreditVO) {
        BorrowCredit borrowCredit = new BorrowCredit();
        BeanUtils.copyProperties(borrowCreditVO,borrowCredit);
        boolean result =  this.borrowCreditMapper.updateByPrimaryKeySelective(borrowCredit) >0 ? true:false;
        return result?1:0;
    }

    @Override
    public BorrowCreditDetailVO getBorrowCreditDetail(String creditNid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("creditNid", creditNid);
        List<BorrowCreditDetailVO> creditDetailList = borrowCreditCustomizeMapper.getBorrowCreditDetail(params);
        if (creditDetailList != null && creditDetailList.size() == 1) {
            BorrowCreditDetailVO creditDetail = creditDetailList.get(0);
            creditDetail.setCreditTimeInt(creditDetail.getCreditTimeInt() + 3 * 24 * 3600);
            return creditDetail;
        }
        return null;
    }

    /**
     * 获取债转信息
     */
	@Override
	public List<BorrowCredit> getBorrowCreditList(BorrowCreditRequest request1) {
		 // 获取债转信息
        BorrowCreditExample borrowCreditExample = new BorrowCreditExample();
        BorrowCreditExample.Criteria borrowCreditCra = borrowCreditExample.createCriteria();
        borrowCreditCra.andCreditNidEqualTo(Integer.parseInt(request1.getCreditNid())).andBidNidEqualTo(request1.getBidNid()).andTenderNidEqualTo(request1.getTenderNid());
        List<BorrowCredit> borrowCredit = this.borrowCreditMapper.selectByExample(borrowCreditExample);
		return borrowCredit;
	}


}
