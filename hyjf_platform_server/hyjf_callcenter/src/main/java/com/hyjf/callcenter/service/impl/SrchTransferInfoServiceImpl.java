package com.hyjf.callcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.callcenter.SrchTransferInfoRequest;
import com.hyjf.am.vo.callcenter.CallCenterBorrowCreditVO;
import com.hyjf.callcenter.client.SrchTransferInfoClient;
import com.hyjf.callcenter.service.SrchTransferInfoService;

/**
 * @author libin
 * @version SrchTransferInfoServiceImpl, v0.1 2018/6/5
 */
@Service
public class SrchTransferInfoServiceImpl implements SrchTransferInfoService {
    @Autowired
    private SrchTransferInfoClient srchTransferInfoClient;
	
	@Override
	public List<CallCenterBorrowCreditVO> selectBorrowCreditList(CallCenterBorrowCreditVO callCenterBorrowCreditVO) {
		SrchTransferInfoRequest SrchTransferInfoRequest = new SrchTransferInfoRequest();
		SrchTransferInfoRequest.setLimitStart(callCenterBorrowCreditVO.getLimitStart());
		SrchTransferInfoRequest.setLimitSize(callCenterBorrowCreditVO.getLimitEnd());
		SrchTransferInfoRequest.setUsernameSrch(callCenterBorrowCreditVO.getUsernameSrch());
		List<CallCenterBorrowCreditVO> list = srchTransferInfoClient.selectBorrowInvestList(SrchTransferInfoRequest);
		return list;
	}
}
