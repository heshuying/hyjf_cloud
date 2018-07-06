/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.service.impl;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.vo.callcenter.CallCenterWithdrawVO;
import com.hyjf.callcenter.client.AmTradeClient;
import com.hyjf.callcenter.service.SrchWithdrawalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjun
 * @version SrchWithdrawalInfoServiceImpl, v0.1 2018/6/14 14:42
 */
@Service
public class SrchWithdrawalInfoServiceImpl implements SrchWithdrawalInfoService {
    @Autowired
    private AmTradeClient amTradeClient;
    /**
     * 查询提现明细
     * @author wangjun
     * @param callCenterBaseRequest
     * @return
     */
    @Override
    public List<CallCenterWithdrawVO> getWithdrawRecordList(CallCenterBaseRequest callCenterBaseRequest) {
        return amTradeClient.getWithdrawRecordList(callCenterBaseRequest);
    }
}
