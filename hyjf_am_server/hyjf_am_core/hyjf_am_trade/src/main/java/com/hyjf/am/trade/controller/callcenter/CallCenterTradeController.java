/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.callcenter;

import com.hyjf.am.response.callcenter.*;
import com.hyjf.am.resquest.callcenter.CallCenterAccountDetailRequest;
import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.resquest.callcenter.CallcenterHztInvestRequest;
import com.hyjf.am.resquest.callcenter.SrchTransferInfoRequest;
import com.hyjf.am.trade.dao.model.customize.callcenter.*;
import com.hyjf.am.trade.service.callcenter.CallCenterTradeService;
import com.hyjf.am.vo.callcenter.*;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wangjun
 * @version CallCenterTradeController, v0.1 2018/6/11 17:52
 */
@RestController
@RequestMapping("/am-trade/callcenter")
public class CallCenterTradeController {
    @Autowired
    CallCenterTradeService callCenterTradeService;

    private static final Logger logger = LoggerFactory.getLogger(CallCenterTradeController.class);

    /**
     * 查询还款明细（直投产品，含承接的债权）
     * @author wangjun
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHztRepaymentDetailList",method = RequestMethod.POST)
    public CallCenterHztRepaymentResponse getHztRepaymentDetailList(@RequestBody @Valid CallCenterBaseRequest request){
        CallCenterHztRepaymentResponse callCenterHztRepaymentResponse = new CallCenterHztRepaymentResponse();
        List<CallCenterHztRepaymentDetailCustomize> list = callCenterTradeService.getHztRepaymentDetailList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterHztRepaymentDetailVO> callCenterHztRepaymentDetailVOS = CommonUtils.convertBeanList(list,CallCenterHztRepaymentDetailVO.class);
            callCenterHztRepaymentResponse.setResultList(callCenterHztRepaymentDetailVOS);
        }
        return callCenterHztRepaymentResponse;
    }

    /**
     * 查询还款明细（汇添金）
     * @author wangjun
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHtjRepaymentDetailList", method = RequestMethod.POST)
    public CallCenterHtjRepaymentResponse getHtjRepaymentDetailList(@RequestBody @Valid CallCenterBaseRequest request){
        CallCenterHtjRepaymentResponse callCenterHtjRepaymentResponse = new CallCenterHtjRepaymentResponse();
        List<CallCenterHtjRepaymentDetailCustomize> list = callCenterTradeService.getHtjRepaymentDetailList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterHtjRepaymentDetailVO> callCenterHtjRepaymentDetailVOS = CommonUtils.convertBeanList(list,CallCenterHtjRepaymentDetailVO.class);
            callCenterHtjRepaymentResponse.setResultList(callCenterHtjRepaymentDetailVOS);
        }
        return callCenterHtjRepaymentResponse;
    }

    /**
     * 查询资金明细
     * @author wangjun
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryAccountDetails", method = RequestMethod.POST)
    public CallCenterAccountDetailResponse queryAccountDetails(@RequestBody @Valid CallCenterAccountDetailRequest request){
        CallCenterAccountDetailResponse callCenterAccountDetailResponse = new CallCenterAccountDetailResponse();
        List<CallCenterAccountDetailCustomize> list = callCenterTradeService.queryAccountDetails(request);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterAccountDetailVO> callCenterAccountDetailVOS = CommonUtils.convertBeanList(list,CallCenterAccountDetailVO.class);
            callCenterAccountDetailResponse.setResultList(callCenterAccountDetailVOS);
        }
        return callCenterAccountDetailResponse;
    }

    /**
     * 查询充值明细
     * @author wangjun
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryRechargeList", method = RequestMethod.POST)
    public CallCenterRechargeResponse queryRechargeList(@RequestBody @Valid CallCenterBaseRequest request){
        CallCenterRechargeResponse callCenterRechargeResponse = new CallCenterRechargeResponse();
        List<CallCenterRechargeCustomize> list = callCenterTradeService.queryRechargeList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterRechargeVO> callCenterRechargeVOS = CommonUtils.convertBeanList(list,CallCenterRechargeVO.class);
            callCenterRechargeResponse.setResultList(callCenterRechargeVOS);
        }
        return callCenterRechargeResponse;
    }

    /**
     * 查询提现明细
     * @author wangjun
     * @param request
     * @return
     */
    @RequestMapping(value = "/getWithdrawRecordList", method = RequestMethod.POST)
    public CallCenterWithdrawResponse getWithdrawRecordList(@RequestBody @Valid CallCenterBaseRequest request){
        CallCenterWithdrawResponse callCenterWithdrawResponse = new CallCenterWithdrawResponse();
        List<CallCenterWithdrawCustomize> list = callCenterTradeService.getWithdrawRecordList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterWithdrawVO> callCenterWithdrawVOS = CommonUtils.convertBeanList(list,CallCenterWithdrawVO.class);
            callCenterWithdrawResponse.setResultList(callCenterWithdrawVOS);
        }
        return callCenterWithdrawResponse;
    }

    /**
     * 查询投资明细(直投产品)
     * @author libin
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryBorrowInvestList", method = RequestMethod.POST)
    public CallcenterHztInvestResponse getBorrowInvestList(@RequestBody @Valid CallcenterHztInvestRequest request){
    	CallcenterHztInvestResponse callcenterHztInvestResponse = new CallcenterHztInvestResponse();
        List<CallcenterHztInvestCustomize> list = callCenterTradeService.getBorrowInvestList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<CallcenterHztInvestVO> callcenterHztInvestVO = CommonUtils.convertBeanList(list,CallcenterHztInvestVO.class);
            callcenterHztInvestResponse.setResultList(callcenterHztInvestVO);
        }
        return callcenterHztInvestResponse;
    }
    
    
    /**
     * 按照用户名/手机号查询转让信息
     * @author libin
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectBorrowCreditList", method = RequestMethod.POST)
    public SrchTransferInfoResponse selectBorrowCreditList(@RequestBody @Valid SrchTransferInfoRequest request){
    	SrchTransferInfoResponse srchTransferInfoResponse = new SrchTransferInfoResponse();
    	List<CallCenterBorrowCreditCustomize> list = callCenterTradeService.getBorrowCreditList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterBorrowCreditVO> callCenterBorrowCreditVO = CommonUtils.convertBeanList(list,CallCenterBorrowCreditVO.class);
            srchTransferInfoResponse.setResultList(callCenterBorrowCreditVO);
        }
        return srchTransferInfoResponse;
    } 
}
