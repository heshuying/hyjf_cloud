/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.user.dao.mapper.auto.BankCardMapper;
import com.hyjf.am.user.dao.mapper.customize.CallCenterCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.auto.BankCardExample;
import com.hyjf.am.user.dao.model.customize.CallcenterUserBaseCustomize;
import com.hyjf.am.user.service.CallCenterBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version CallCenterBankServiceImpl, v0.1 2018/6/6 14:22
 */
@Service
public class CallCenterBankServiceImpl implements CallCenterBankService {
    @Autowired
    private BankCardMapper bankCardMapper;
    @Autowired
    private CallCenterCustomizeMapper callCenterCustomizeMapper;

    public List<BankCard> getTiedCardOfAccountBank(Integer userId){
        BankCardExample example = new BankCardExample();
        BankCardExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<BankCard> bankCardList = bankCardMapper.selectByExample(example);
        if(bankCardList!= null && bankCardList.size()>0){
            return bankCardList;
        }
        return null;
    }

	@Override
	public List<CallcenterUserBaseCustomize> getNoServiceFuTouUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
		List<CallcenterUserBaseCustomize> CallcenterUserBaseCustomizeList = callCenterCustomizeMapper.findNoServiceFuTouUsersList(callCenterUserInfoRequest);
		return CallcenterUserBaseCustomizeList;
	}

	@Override
	public List<CallcenterUserBaseCustomize> getNoServiceLiuShiUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
		List<CallcenterUserBaseCustomize> CallcenterUserBaseCustomizeList = callCenterCustomizeMapper.findNoServiceLiuShiUsersList(callCenterUserInfoRequest);
		return CallcenterUserBaseCustomizeList;
	}

	@Override
	public List<CallcenterUserBaseCustomize> getNoServiceUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
		List<CallcenterUserBaseCustomize> CallcenterUserBaseCustomizeList = callCenterCustomizeMapper.findNoServiceUsersList(callCenterUserInfoRequest);
		return CallcenterUserBaseCustomizeList;
	}
}
