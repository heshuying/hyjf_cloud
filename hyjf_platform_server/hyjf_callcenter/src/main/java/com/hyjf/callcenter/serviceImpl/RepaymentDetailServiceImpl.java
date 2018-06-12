/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.serviceImpl;

import com.hyjf.am.resquest.callcenter.CallCenterRepaymentRequest;
import com.hyjf.am.vo.callcenter.CallCenterHtjRepaymentDetailVO;
import com.hyjf.am.vo.callcenter.CallCenterHztRepaymentDetailVO;
import com.hyjf.callcenter.client.RepaymentDetailClient;
import com.hyjf.callcenter.service.RepaymentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version RepaymentDetailServiceImpl, v0.1 2018/6/11 11:40
 */
@Service
public class RepaymentDetailServiceImpl implements RepaymentDetailService {
    @Autowired
    private RepaymentDetailClient repaymentDetailClient;
    /**
     *
     * 按照用户名/手机号查询还款明细（直投产品，含承接的债权）
     * @author wangjun
     * @param callCenterRepaymentRequest
     * @return
     */
    @Override
    public List<CallCenterHztRepaymentDetailVO> getHztRepaymentDetailList(
            CallCenterRepaymentRequest callCenterRepaymentRequest) {
        return repaymentDetailClient.getHztRepaymentDetailList(callCenterRepaymentRequest);
    }
    /**
     *
     * 按照用户名/手机号查询还款明细（汇添金）
     * @author wangjun
     * @param callCenterRepaymentRequest
     * @return
     */
    @Override
    public List<CallCenterHtjRepaymentDetailVO> getHtjRepaymentDetailList(
            CallCenterRepaymentRequest callCenterRepaymentRequest) {
        return repaymentDetailClient.getHtjRepaymentDetailList(callCenterRepaymentRequest);
    }
}
