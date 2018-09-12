package com.hyjf.am.user.service.callcenter;

import com.hyjf.am.resquest.callcenter.CallCenterServiceUsersRequest;
import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.customize.CallcenterAccountHuifuCustomize;
import com.hyjf.am.user.dao.model.customize.CallcenterUserBaseCustomize;
import com.hyjf.am.user.service.BaseService;

import java.util.List;

/**
 * @author wangjun
 * @version UpdateBankCardBatchService, v0.1 2018/5/29 14:58
 */
public interface CallCenterBankService extends BaseService{
    /**
     * 查询江西银行绑卡
     * @param userId
     * @return
     */
    List<BankCard> getTiedCardOfAccountBank(Integer userId);

    /**
     * 查询呼叫中心未分配客服的用户
     * @param callCenterUserInfoRequest
     * @return
     */
    List<CallcenterUserBaseCustomize> getNoServiceUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

    /**
     * 更新客服分配状态
     * @param callCenterServiceUsersRequest
     * @return
     */
    Integer updateRecord(CallCenterServiceUsersRequest callCenterServiceUsersRequest);

    /**
     * 查询用户基本信息
     * @param callCenterUserInfoRequest
     * @return
     */
    List<CallcenterUserBaseCustomize> getBasicUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

    /**
     * 查询用户详细信息
     * @param callCenterUserInfoRequest
     * @return
     */
    List<CallcenterUserBaseCustomize> getUserDetailById(CallCenterUserInfoRequest callCenterUserInfoRequest);

    /**
     * 查询汇付绑卡
     * @param callcenterAccountHuifuRequest
     * @return
     */
    List<CallcenterAccountHuifuCustomize> getHuifuTiedcardInfo(CallcenterAccountHuifuRequest callcenterAccountHuifuRequest);

    /**
     * 获取优惠券内容
     * @param couponSource
     * @return
     */
    String getCouponContent(String couponSource);
}