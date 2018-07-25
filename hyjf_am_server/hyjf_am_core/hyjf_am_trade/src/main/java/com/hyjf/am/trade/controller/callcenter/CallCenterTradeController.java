/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.callcenter;

import com.hyjf.am.response.callcenter.*;
import com.hyjf.am.response.trade.RUserResponse;
import com.hyjf.am.resquest.callcenter.*;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.RUser;
import com.hyjf.am.trade.dao.model.customize.callcenter.*;
import com.hyjf.am.trade.service.callcenter.CallCenterTradeService;
import com.hyjf.am.vo.callcenter.*;
import com.hyjf.am.vo.trade.RUserVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wangjun
 * @version CallCenterTradeController, v0.1 2018/6/11 17:52
 */
@RestController
@RequestMapping("/am-trade/callcenter")
public class CallCenterTradeController extends BaseController {
    @Autowired
    CallCenterTradeService callCenterTradeService;

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
    
    /**
     * 按照用户名/手机号查询承接债权信息
     * @author libin
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectBorrowCreditTenderList", method = RequestMethod.POST)
    public SrchTransferInfoResponse selectBorrowCreditTenderList(@RequestBody @Valid SrchTransferInfoRequest request){
    	SrchTransferInfoResponse srchTransferInfoResponse = new SrchTransferInfoResponse();
    	List<CallCenterBorrowCreditCustomize> list = callCenterTradeService.getBorrowCreditTenderList(request);
    	if(!CollectionUtils.isEmpty(list)){
    		List<CallCenterBorrowCreditVO> callCenterBorrowCreditVO = CommonUtils.convertBeanList(list,CallCenterBorrowCreditVO.class);
    		srchTransferInfoResponse.setResultList(callCenterBorrowCreditVO);
    	}
    	return srchTransferInfoResponse;
    }

    /**
     * 查询优惠券
     * @author wangjun
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserCouponInfoList", method = RequestMethod.POST)
    public CallCenterCouponUserResponse getUserCouponInfoList(@RequestBody @Valid CallCenterBaseRequest request){
        CallCenterCouponUserResponse callCenterCouponUserResponse = new CallCenterCouponUserResponse();
        List<CallCenterCouponUserCustomize> list = callCenterTradeService.getUserCouponInfoList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterCouponUserVO> callCenterCouponUserVOS = CommonUtils.convertBeanList(list,CallCenterCouponUserVO.class);
            callCenterCouponUserResponse.setResultList(callCenterCouponUserVOS);
        }
        return callCenterCouponUserResponse;
    }

    /**
     * 查询优惠券使用（直投产品）
     * @author wangjun
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserCouponTenderList", method = RequestMethod.POST)
    public CallCenterCouponTenderResponse getUserCouponTenderList(@RequestBody @Valid CallCenterBaseRequest request){
        CallCenterCouponTenderResponse callCenterCouponTenderResponse = new CallCenterCouponTenderResponse();
        List<CallCenterCouponTenderCustomize> list = callCenterTradeService.getUserCouponTenderList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterCouponTenderVO> callCenterCouponTenderVOS = CommonUtils.convertBeanList(list,CallCenterCouponTenderVO.class);
            callCenterCouponTenderResponse.setResultList(callCenterCouponTenderVOS);
        }
        return callCenterCouponTenderResponse;
    }

    /**
     * 查询优惠券回款（直投产品）
     * @author wangjun
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserCouponBackMoneyList", method = RequestMethod.POST)
    public CallCenterCouponBackMoneyResponse getUserCouponBackMoneyList(@RequestBody @Valid CallCenterBaseRequest request){
        CallCenterCouponBackMoneyResponse callCenterCouponBackMoneyResponse = new CallCenterCouponBackMoneyResponse();
        List<CallCenterCouponBackMoneyCustomize> list = callCenterTradeService.getUserCouponBackMoneyList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterCouponBackMoneyVO> callCenterCouponBackMoneyVOS = CommonUtils.convertBeanList(list,CallCenterCouponBackMoneyVO.class);
            callCenterCouponBackMoneyResponse.setResultList(callCenterCouponBackMoneyVOS);
        }
        return callCenterCouponBackMoneyResponse;
    }
    
    /**
     * 查询投资明细(汇添金)
     * @author libin
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryHtjBorrowInvestList", method = RequestMethod.POST)
    public CallcenterHtjInvestResponse queryHtjBorrowInvestList(@RequestBody @Valid CallcenterHtjInvestRequest request){
    	CallcenterHtjInvestResponse callcenterHtjInvestResponse = new CallcenterHtjInvestResponse();
        List<CallcenterHtjInvestCustomize> list = callCenterTradeService.getHtjBorrowInvestList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<CallcenterHtjInvestVO> callcenterHtjInvestVO = CommonUtils.convertBeanList(list,CallcenterHtjInvestVO.class);
            callcenterHtjInvestResponse.setResultList(callcenterHtjInvestVO);
        }
        return callcenterHtjInvestResponse;
    }

    /**
     * 根据用户ID查询推荐人信息
     * @param userId
     * @return
     */
    @RequestMapping("/getRefereerInfoByUserId/{userId}")
    public RUserResponse getRefereerInfoByUserId(@PathVariable Integer userId){
        RUserResponse response = new RUserResponse();
        RUser rUser = callCenterTradeService.getRefereerInfoByUserId(userId);
        if(rUser != null){
            RUserVO vo = new RUserVO();
            BeanUtils.copyProperties(rUser, vo);
            response.setResult(vo);
        }
        return response;
    }
}
