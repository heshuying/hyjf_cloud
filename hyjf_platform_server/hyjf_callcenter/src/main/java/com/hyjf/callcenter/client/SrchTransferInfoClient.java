package com.hyjf.callcenter.client;

import java.util.List;

import com.hyjf.am.resquest.callcenter.SrchTransferInfoRequest;
import com.hyjf.am.vo.callcenter.CallCenterBorrowCreditVO;

/**
 * @author libin
 * @version SrchTransferInfoClient, v0.1 2018/6/6 10:02
 */
public interface SrchTransferInfoClient {
	
	/**
	 * 取得呼叫中心转让信息接口
	 * @return
	 */
	List<CallCenterBorrowCreditVO> selectBorrowInvestList(SrchTransferInfoRequest srchTransferInfoRequest);	
}
