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
    List<BankCard> getTiedCardOfAccountBank(Integer userId);

    List<CallcenterUserBaseCustomize> getNoServiceFuTouUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

    List<CallcenterUserBaseCustomize> getNoServiceLiuShiUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

    List<CallcenterUserBaseCustomize> getNoServiceUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

    Integer updateRecord(CallCenterServiceUsersRequest callCenterServiceUsersRequest);

    List<CallcenterUserBaseCustomize> getBasicUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

    List<CallcenterUserBaseCustomize> getUserDetailById(CallCenterUserInfoRequest callCenterUserInfoRequest);
    
    List<CallcenterAccountHuifuCustomize> getHuifuTiedcardInfo(CallcenterAccountHuifuRequest callcenterAccountHuifuRequest);

    String getCouponContent(String couponSource);
}