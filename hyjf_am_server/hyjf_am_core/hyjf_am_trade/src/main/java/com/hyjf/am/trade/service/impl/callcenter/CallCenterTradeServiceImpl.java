/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.callcenter;

import com.hyjf.am.resquest.callcenter.CallCenterAccountDetailRequest;
import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.trade.dao.mapper.customize.callcenter.CallCenterAccountDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.callcenter.CallCenterRepaymentDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.callcenter.CallcenterRechargeCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.callcenter.CallcenterWithdrawCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.callcenter.*;
import com.hyjf.am.trade.service.callcenter.CallCenterTradeService;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    @Autowired
    private CallcenterRechargeCustomizeMapper callcenterRechargeCustomizeMapper;

    @Autowired
    private CallcenterWithdrawCustomizeMapper callcenterWithdrawCustomizeMapper;

    /**
     *
     * 按照用户名/手机号查询还款明细（直投产品，含承接的债权）
     * @author wangjun
     * @param callCenterBaseRequest
     * @return
     */
    @Override
    public List<CallCenterHztRepaymentDetailCustomize> getHztRepaymentDetailList(CallCenterBaseRequest callCenterBaseRequest) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("userId", callCenterBaseRequest.getUserId());
        paraMap.put("limitStart", callCenterBaseRequest.getLimitStart());
        paraMap.put("limitEnd", callCenterBaseRequest.getLimitEnd());
        return callCenterRepaymentDetailCustomizeMapper.getHztRepaymentDetailList(paraMap);
    }
    /**
     *
     * 按照用户名/手机号查询还款明细（汇添金）
     * @author wangjun
     * @param callCenterBaseRequest
     * @return
     */
    @Override
    public List<CallCenterHtjRepaymentDetailCustomize> getHtjRepaymentDetailList(CallCenterBaseRequest callCenterBaseRequest) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("userId", callCenterBaseRequest.getUserId());
        paraMap.put("limitStart", callCenterBaseRequest.getLimitStart());
        paraMap.put("limitEnd", callCenterBaseRequest.getLimitEnd());
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

    /**
     *
     * 查询充值明细
     * @author wangjun
     * @param centerBaseRequest
     * @return
     */
    @Override
    public List<CallCenterRechargeCustomize> queryRechargeList(CallCenterBaseRequest centerBaseRequest) {
        List<CallCenterRechargeCustomize> list = callcenterRechargeCustomizeMapper.queryRechargeList(centerBaseRequest);
       if(!CollectionUtils.isEmpty(list)){
            Map<String, String> statusMap = CacheUtil.getParamNameMap("RECHARGE_STATUS");
            Map<String, String> bankMap = CacheUtil.getParamNameMap("BANK_TYPE");
            for(CallCenterRechargeCustomize callCenterRechargeCustomize : list){
                callCenterRechargeCustomize.setStatus(statusMap.getOrDefault(callCenterRechargeCustomize.getStatus(),null));
                callCenterRechargeCustomize.setIsBank(bankMap.getOrDefault(callCenterRechargeCustomize.getIsBank(),null));
            }
        }
        return list;
    }

    /**
     * 查询提现明细
     * @author wangjun
     * @param centerBaseRequest
     * @return
     */
    @Override
    public List<CallCenterWithdrawCustomize> getWithdrawRecordList(CallCenterBaseRequest centerBaseRequest) {
        List<CallCenterWithdrawCustomize> list = callcenterWithdrawCustomizeMapper.getWithdrawRecordList(centerBaseRequest);
        if(!CollectionUtils.isEmpty(list)){
            Map<String, String> clientMap = CacheUtil.getParamNameMap("CLIENT");
            for(CallCenterWithdrawCustomize callCenterWithdrawCustomize : list){
                callCenterWithdrawCustomize.setClientStr(clientMap.getOrDefault(callCenterWithdrawCustomize.getClientStr(),null));
            }
        }
        return list;
    }
}
