/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import com.hyjf.am.response.callcenter.CallCenterHztRepaymentResponse;
import com.hyjf.am.response.callcenter.CallCenterServiceUsersResponse;
import com.hyjf.am.response.callcenter.CallCenterUserBaseResponse;
import com.hyjf.am.response.user.BankCardResponse;
import com.hyjf.am.resquest.callcenter.CallCenterRepaymentRequest;
import com.hyjf.am.resquest.callcenter.CallCenterServiceUsersRequest;
import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.customize.CallcenterUserBaseCustomize;
import com.hyjf.am.user.service.CallCenterBankService;
import com.hyjf.am.vo.callcenter.CallCenterHztRepaymentDetailVO;
import com.hyjf.am.vo.callcenter.CallCenterUserBaseVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

/**
 * @author wangjun
 * @version CallCenterBankController, v0.1 2018/6/6 14:14
 */
@RestController
@RequestMapping("/am-user/callcenter")
public class CallCenterBankController {
    @Autowired
    CallCenterBankService callCenterBankService;
    private static final Logger logger = LoggerFactory.getLogger(CallCenterBankController.class);
    
    @RequestMapping("/getTiedCardForBank/{userId}")
    public BankCardResponse getTiedCardOfAccountBank(@PathVariable Integer userId){
        BankCardResponse bankCardResponse = new BankCardResponse();
        List<BankCard> bankCardList = callCenterBankService.getTiedCardOfAccountBank(userId);
        if(!CollectionUtils.isEmpty(bankCardList)){
            List<BankCardVO> bankCardVOList = CommonUtils.convertBeanList(bankCardList,BankCardVO.class);
            bankCardResponse.setResultList(bankCardVOList);
        }
        return bankCardResponse;
    }
    
    
    @RequestMapping("/getNoServiceFuTouUsersList")
    public CallCenterUserBaseResponse getNoServiceFuTouUsersList(@RequestBody @Valid CallCenterUserInfoRequest callCenterUserInfoRequest){
    	CallCenterUserBaseResponse callCenterUserBaseResponse = new CallCenterUserBaseResponse();
        List<CallcenterUserBaseCustomize> list = callCenterBankService.getNoServiceFuTouUsersList(callCenterUserInfoRequest);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterUserBaseVO> callCenterUserBaseVOS = CommonUtils.convertBeanList(list,CallCenterUserBaseVO.class);
            callCenterUserBaseResponse.setResultList(callCenterUserBaseVOS);
        }
        return callCenterUserBaseResponse;
    }
    
    
    @RequestMapping("/getNoServiceLiuShiUsersList")
    public CallCenterUserBaseResponse getNoServiceLiuShiUsersList(@RequestBody @Valid CallCenterUserInfoRequest callCenterUserInfoRequest){
    	CallCenterUserBaseResponse callCenterUserBaseResponse = new CallCenterUserBaseResponse();
        List<CallcenterUserBaseCustomize> list = callCenterBankService.getNoServiceLiuShiUsersList(callCenterUserInfoRequest);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterUserBaseVO> callCenterUserBaseVOS = CommonUtils.convertBeanList(list,CallCenterUserBaseVO.class);
            callCenterUserBaseResponse.setResultList(callCenterUserBaseVOS);
        }
        return callCenterUserBaseResponse;
    }
    
    
    @RequestMapping("/getNoServiceUsersList")
    public CallCenterUserBaseResponse getNoServiceUsersList(@RequestBody @Valid CallCenterUserInfoRequest callCenterUserInfoRequest){
    	CallCenterUserBaseResponse callCenterUserBaseResponse = new CallCenterUserBaseResponse();
        List<CallcenterUserBaseCustomize> list = callCenterBankService.getNoServiceUsersList(callCenterUserInfoRequest);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterUserBaseVO> callCenterUserBaseVOS = CommonUtils.convertBeanList(list,CallCenterUserBaseVO.class);
            callCenterUserBaseResponse.setResultList(callCenterUserBaseVOS);
        }
        return callCenterUserBaseResponse;
    }

    @RequestMapping("/executeRecord")
    public Integer executeRecord(@RequestBody @Valid CallCenterServiceUsersRequest callCenterServiceUsersRequest){
        CallCenterServiceUsersResponse callCenterServiceUsersResponse = new CallCenterServiceUsersResponse();
        return callCenterBankService.updateRecord(callCenterServiceUsersRequest);
    }
    
}
