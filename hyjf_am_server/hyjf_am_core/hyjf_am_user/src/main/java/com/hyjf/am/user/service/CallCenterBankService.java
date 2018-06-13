package com.hyjf.am.user.service;

import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.customize.CallcenterUserBaseCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version UpdateBankCardBatchService, v0.1 2018/5/29 14:58
 */
public interface CallCenterBankService {
    List<BankCard> getTiedCardOfAccountBank(Integer userId);
    
    List<CallcenterUserBaseCustomize> getNoServiceFuTouUsersList(Map<String, Object> conditionMap);
    
    List<CallcenterUserBaseCustomize> getNoServiceLiuShiUsersList(Map<String, Object> conditionMap);

}