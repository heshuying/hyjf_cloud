package com.hyjf.am.user.service;

import com.hyjf.am.resquest.callcenter.CallCenterServiceUsersRequest;
import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.customize.CallcenterUserBaseCustomize;
import com.hyjf.am.vo.callcenter.CallCenterServiceUsersVO;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version UpdateBankCardBatchService, v0.1 2018/5/29 14:58
 */
public interface CallCenterBankService {
    List<BankCard> getTiedCardOfAccountBank(Integer userId);
    
    List<CallcenterUserBaseCustomize> getNoServiceFuTouUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);
    
    List<CallcenterUserBaseCustomize> getNoServiceLiuShiUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);
    
    List<CallcenterUserBaseCustomize> getNoServiceUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

    Integer updateRecord(CallCenterServiceUsersRequest callCenterServiceUsersRequest);

}