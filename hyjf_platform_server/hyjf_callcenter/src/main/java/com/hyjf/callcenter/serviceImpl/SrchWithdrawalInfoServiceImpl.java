/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.serviceImpl;

import java.util.List;

import com.hyjf.am.vo.callcenter.CallCenterWithdrawVO;
import com.hyjf.callcenter.client.SrchWithdrawalInfoClient;
import com.hyjf.callcenter.service.SrchWithdrawalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;

/**
 * @author wangjun
 * @version SrchWithdrawalInfoServiceImpl, v0.1 2018/6/14 14:42
 */
@Service
public class SrchWithdrawalInfoServiceImpl implements SrchWithdrawalInfoService {
    @Autowired
    private SrchWithdrawalInfoClient srchWithdrawalInfoClient;
    /**
     * 查询提现明细
     * @author wangjun
     * @param callCenterBaseRequest
     * @return
     */
    @Override
    public List<CallCenterWithdrawVO> getWithdrawRecordList(CallCenterBaseRequest callCenterBaseRequest) {
        return srchWithdrawalInfoClient.getWithdrawRecordList(callCenterBaseRequest);
    }
}
