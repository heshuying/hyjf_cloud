/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.callcenter;

import com.hyjf.am.resquest.callcenter.CallCenterAccountDetailRequest;
import com.hyjf.am.resquest.callcenter.CallCenterRepaymentRequest;
import com.hyjf.am.trade.dao.mapper.customize.callcenter.CallCenterAccountDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.callcenter.CallCenterRepaymentDetailCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.callcenter.CallCenterAccountDetailCustomize;
import com.hyjf.am.trade.dao.model.customize.callcenter.CallCenterHtjRepaymentDetailCustomize;
import com.hyjf.am.trade.dao.model.customize.callcenter.CallCenterHztRepaymentDetailCustomize;
import com.hyjf.am.trade.service.callcenter.CallCenterTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version CallCenterTradeServiceImpl, v0.1 2018/6/11 17:52
 */
@Service
public class CallCenterTradeServiceImpl implements CallCenterTradeService {
    @Autowired
    private CallCenterRepaymentDetailCustomizeMapper callCenterRepaymentDetailCustomizeMapper;

    @Autowired
    private CallCenterAccountDetailCustomizeMapper callCenterAccountDetailCustomizeMapper;

    /**
     *
     * 按照用户名/手机号查询还款明细（直投产品，含承接的债权）
     * @author wangjun
     * @param callCenterRepaymentRequest
     * @return
     */
    @Override
    public List<CallCenterHztRepaymentDetailCustomize> getHztRepaymentDetailList(CallCenterRepaymentRequest callCenterRepaymentRequest) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("userId", callCenterRepaymentRequest.getUserId());
        paraMap.put("limitStart", callCenterRepaymentRequest.getLimitStart());
        paraMap.put("limitEnd", callCenterRepaymentRequest.getLimitEnd());
        return callCenterRepaymentDetailCustomizeMapper.getHztRepaymentDetailList(paraMap);
    }
    /**
     *
     * 按照用户名/手机号查询还款明细（汇添金）
     * @author wangjun
     * @param callCenterRepaymentRequest
     * @return
     */
    @Override
    public List<CallCenterHtjRepaymentDetailCustomize> getHtjRepaymentDetailList(CallCenterRepaymentRequest callCenterRepaymentRequest) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("userId", callCenterRepaymentRequest.getUserId());
        paraMap.put("limitStart", callCenterRepaymentRequest.getLimitStart());
        paraMap.put("limitEnd", callCenterRepaymentRequest.getLimitEnd());
        return callCenterRepaymentDetailCustomizeMapper.getHtjRepaymentDetailList(paraMap);
    }

    /**
     *
     * 查询资金明细
     * @author wangjun
     * @param callCenterAccountDetailRequest
     * @return
     */
    @Override
    public List<CallCenterAccountDetailCustomize> queryAccountDetails(CallCenterAccountDetailRequest callCenterAccountDetailRequest) {
        return callCenterAccountDetailCustomizeMapper.queryAccountDetails(callCenterAccountDetailRequest);
    }
}
