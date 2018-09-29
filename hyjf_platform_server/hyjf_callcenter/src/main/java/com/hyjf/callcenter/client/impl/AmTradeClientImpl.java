/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.callcenter.*;
import com.hyjf.am.response.trade.RUserResponse;
import com.hyjf.am.resquest.callcenter.*;
import com.hyjf.am.vo.callcenter.*;
import com.hyjf.am.vo.trade.RUserVO;
import com.hyjf.callcenter.client.AmTradeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author wangjun
 * @version AmTradeClientImpl, v0.1 2018/7/6 17:15
 */
@Service
public class AmTradeClientImpl implements AmTradeClient {
    private static Logger logger = LoggerFactory.getLogger(AmTradeClientImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<CallCenterCouponUserVO> selectCouponUserList(CallCenterBaseRequest callCenterBaseRequest){
        CallCenterCouponUserResponse callCenterCouponUserResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/getUserCouponInfoList", callCenterBaseRequest,
                        CallCenterCouponUserResponse.class).getBody();
        if (callCenterCouponUserResponse != null) {
            return callCenterCouponUserResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallCenterCouponTenderVO> selectCouponTenderList(CallCenterBaseRequest callCenterBaseRequest){
        CallCenterCouponTenderResponse callCenterCouponTenderResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/getUserCouponTenderList", callCenterBaseRequest,
                        CallCenterCouponTenderResponse.class).getBody();
        if (callCenterCouponTenderResponse != null) {
            return callCenterCouponTenderResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallCenterCouponBackMoneyVO> selectCouponBackMoneyList(CallCenterBaseRequest callCenterBaseRequest){
        CallCenterCouponBackMoneyResponse callCenterCouponBackMoneyResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/getUserCouponBackMoneyList", callCenterBaseRequest,
                        CallCenterCouponBackMoneyResponse.class).getBody();
        if (callCenterCouponBackMoneyResponse != null) {
            return callCenterCouponBackMoneyResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallcenterHtjInvestVO> selectBorrowInvestList(CallcenterHtjInvestRequest callcenterHtjInvestRequest) {
        CallcenterHtjInvestResponse callcenterHtjInvestResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/queryHtjBorrowInvestList", callcenterHtjInvestRequest, CallcenterHtjInvestResponse.class)
                .getBody();
        if (callcenterHtjInvestResponse != null) {
            return callcenterHtjInvestResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallcenterHztInvestVO> selectBorrowInvestList(CallcenterHztInvestRequest callcenterHztInvestRequest) {
        CallcenterHztInvestResponse callcenterHztInvestResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/queryBorrowInvestList", callcenterHztInvestRequest, CallcenterHztInvestResponse.class)
                .getBody();
        if (callcenterHztInvestResponse != null) {
            return callcenterHztInvestResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallCenterHztRepaymentDetailVO> getHztRepaymentDetailList(CallCenterBaseRequest callCenterBaseRequest) {
        CallCenterHztRepaymentResponse callCenterHztRepaymentResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/getHztRepaymentDetailList/", callCenterBaseRequest, CallCenterHztRepaymentResponse.class)
                .getBody();
        if (callCenterHztRepaymentResponse != null) {
            return callCenterHztRepaymentResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallCenterHtjRepaymentDetailVO> getHtjRepaymentDetailList(CallCenterBaseRequest callCenterBaseRequest){
        CallCenterHtjRepaymentResponse callCenterHtjRepaymentResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/getHtjRepaymentDetailList/", callCenterBaseRequest, CallCenterHtjRepaymentResponse.class)
                .getBody();
        if (callCenterHtjRepaymentResponse != null) {
            return callCenterHtjRepaymentResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallCenterBankAccountManageVO> queryAccountInfos(
            CallCenterBankAccountManageRequest callCenterBankAccountManageRequest) {
        CallCenterBankAccountManageResponse manageResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/queryAccountInfos/", callCenterBankAccountManageRequest, CallCenterBankAccountManageResponse.class)
                .getBody();
        if (manageResponse != null) {
            return manageResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallCenterAccountDetailVO> queryAccountDetails(CallCenterAccountDetailRequest callCenterAccountDetailRequest) {
        CallCenterAccountDetailResponse callCenterAccountDetailResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/queryAccountDetails/",
                        callCenterAccountDetailRequest, CallCenterAccountDetailResponse.class).getBody();
        if (callCenterAccountDetailResponse != null) {
            return callCenterAccountDetailResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallCenterRechargeVO> queryRechargeList(CallCenterBaseRequest callCenterBaseRequest){
        CallCenterRechargeResponse callCenterRechargeResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/queryRechargeList", callCenterBaseRequest, CallCenterRechargeResponse.class)
                .getBody();
        if (callCenterRechargeResponse != null) {
            return callCenterRechargeResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallCenterBorrowCreditVO> selectBorrowInvestList(SrchTransferInfoRequest srchTransferInfoRequest) {

        SrchTransferInfoResponse srchTransferInfoResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/selectBorrowCreditList", srchTransferInfoRequest, SrchTransferInfoResponse.class)
                .getBody();
        if (srchTransferInfoResponse != null) {
            return srchTransferInfoResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallCenterBorrowCreditVO> selectBorrowCreditTenderList(
            SrchTransferInfoRequest srchTransferInfoRequest) {
        SrchTransferInfoResponse srchTransferInfoResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/selectBorrowCreditTenderList", srchTransferInfoRequest, SrchTransferInfoResponse.class)
                .getBody();
        if (srchTransferInfoResponse != null) {
            return srchTransferInfoResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallCenterWithdrawVO> getWithdrawRecordList(CallCenterBaseRequest callCenterBaseRequest){
        CallCenterWithdrawResponse callCenterWithdrawResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/getWithdrawRecordList", callCenterBaseRequest, CallCenterWithdrawResponse.class)
                .getBody();
        if (callCenterWithdrawResponse != null) {
            return callCenterWithdrawResponse.getResultList();
        }
        return null;
    }

    /**
     * 根据用户ID查询推荐人信息
     * @param userId
     * @return
     */
    @Override
    public RUserVO getRefereerInfoByUserId(Integer userId){
        String url = "http://AM-TRADE/am-trade/callcenter/getRefereerInfoByUserId/" + userId;
        RUserResponse response = restTemplate.getForEntity(url, RUserResponse.class).getBody();
        if(response != null && Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }
}
