package com.hyjf.callcenter.client;

import java.util.List;
import com.hyjf.am.resquest.callcenter.CallCenterBankAccountManageRequest;
import com.hyjf.am.vo.callcenter.CallCenterBankAccountManageVO;

/**
 * @author libin
 * @version SrchBalanceInfoClient, v0.1 2018/6/6 13:49
 */
public interface SrchBalanceInfoClient {
	List<CallCenterBankAccountManageVO> queryAccountInfos(CallCenterBankAccountManageRequest callCenterBankAccountManageRequest);
}
