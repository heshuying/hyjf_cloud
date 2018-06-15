/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.client;

import java.util.List;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.vo.callcenter.CallCenterWithdrawVO;

/**
 * @author wangjun
 * @version SrchWithdrawalInfoClient, v0.1 2018/6/15 11:30
 */
public interface SrchWithdrawalInfoClient {
    /**
     * 查询提现明细
     * @param callCenterBaseRequest
     * @return List<CallCenterWithdrawVO>
     * @author wangjun
     */
    List<CallCenterWithdrawVO> getWithdrawRecordList(CallCenterBaseRequest callCenterBaseRequest);
}
