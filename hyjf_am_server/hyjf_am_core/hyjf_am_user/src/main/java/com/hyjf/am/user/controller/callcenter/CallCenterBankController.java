/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.callcenter;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.callcenter.CallCenterAccountHuifuResponse;
import com.hyjf.am.response.callcenter.CallCenterUserBaseResponse;
import com.hyjf.am.response.user.BankCardResponse;
import com.hyjf.am.resquest.callcenter.CallCenterServiceUsersRequest;
import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.customize.CallcenterAccountHuifuCustomize;
import com.hyjf.am.user.dao.model.customize.CallcenterUserBaseCustomize;
import com.hyjf.am.user.service.callcenter.CallCenterBankService;
import com.hyjf.am.vo.callcenter.CallCenterUserBaseVO;
import com.hyjf.am.vo.callcenter.CallcenterAccountHuifuVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import javax.validation.Valid;

/**
 * @author wangjun
 * @version CallCenterBankController, v0.1 2018/6/6 14:14
 */
@RestController
@RequestMapping("/am-user/callcenter")
public class CallCenterBankController extends BaseController{
    @Autowired
    CallCenterBankService callCenterBankService;

    /**
     * 查询江西银行绑卡
     * @param userId
     * @return
     */
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

    /**
     * 查询呼叫中心未分配客服的用户
     * @param callCenterUserInfoRequest
     * @return
     */
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

    /**
     * 更新客服分配状态
     * @param callCenterServiceUsersRequest
     * @return
     */
    @RequestMapping("/executeRecord")
    public IntegerResponse executeRecord(@RequestBody @Valid CallCenterServiceUsersRequest callCenterServiceUsersRequest){
        return new IntegerResponse(callCenterBankService.updateRecord(callCenterServiceUsersRequest));
    }

    /**
     * 查询用户基本信息
     * @param callCenterUserInfoRequest
     * @return
     */
    @RequestMapping("/getBasicUsersList")
    public CallCenterUserBaseResponse getBasicUsersList(@RequestBody @Valid CallCenterUserInfoRequest callCenterUserInfoRequest){
        CallCenterUserBaseResponse callCenterUserBaseResponse = new CallCenterUserBaseResponse();
        List<CallcenterUserBaseCustomize> list = callCenterBankService.getBasicUsersList(callCenterUserInfoRequest);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterUserBaseVO> callCenterUserBaseVOS = CommonUtils.convertBeanList(list,CallCenterUserBaseVO.class);
            callCenterUserBaseResponse.setResultList(callCenterUserBaseVOS);
        }
        return callCenterUserBaseResponse;
    }

    /**
     * 查询用户详细信息
     * @param callCenterUserInfoRequest
     * @return
     */
    @RequestMapping("/getUserDetailById")
    public CallCenterUserBaseResponse getUserDetailById(@RequestBody @Valid CallCenterUserInfoRequest callCenterUserInfoRequest){
        CallCenterUserBaseResponse callCenterUserBaseResponse = new CallCenterUserBaseResponse();
        List<CallcenterUserBaseCustomize> list = callCenterBankService.getUserDetailById(callCenterUserInfoRequest);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterUserBaseVO> callCenterUserBaseVOS = CommonUtils.convertBeanList(list,CallCenterUserBaseVO.class);
            callCenterUserBaseResponse.setResultList(callCenterUserBaseVOS);
        }
        return callCenterUserBaseResponse;
    }

    /**
     * 查询汇付绑卡
     * @param callcenterAccountHuifuRequest
     * @return
     */
    @RequestMapping("/getHuifuTiedcardInfo")
    public CallCenterAccountHuifuResponse getHuifuTiedcardInfo(@RequestBody @Valid CallcenterAccountHuifuRequest callcenterAccountHuifuRequest){
    	CallCenterAccountHuifuResponse CallCenterAccountHuifuResponse = new CallCenterAccountHuifuResponse();
    	List<CallcenterAccountHuifuCustomize> list = callCenterBankService.getHuifuTiedcardInfo(callcenterAccountHuifuRequest);
        if(!CollectionUtils.isEmpty(list)){
            List<CallcenterAccountHuifuVO> callCenterAccountHuifuVOS = CommonUtils.convertBeanList(list,CallcenterAccountHuifuVO.class);
            CallCenterAccountHuifuResponse.setResultList(callCenterAccountHuifuVOS);
        }
    	return CallCenterAccountHuifuResponse;
    }

    /**
     * 获取优惠券内容
     * @param couponCode
     * @return
     */
    @RequestMapping("/getCouponContent/{couponCode}")
    public StringResponse getCouponContent(@PathVariable String couponCode){
        return new StringResponse(callCenterBankService.getCouponContent(couponCode));
    }
}
