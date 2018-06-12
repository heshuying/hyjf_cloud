/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

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
	public List<CallcenterUserBaseCustomize> getNoServiceFuTouUsersList(Map<String, Object> conditionMap) {
		List<CallcenterUserBaseCustomize> CallcenterUserBaseCustomizeList = callCenterCustomizeMapper.findNoServiceFuTouUsersList(conditionMap);
		return CallcenterUserBaseCustomizeList;
	}

	@Override
	public List<CallcenterUserBaseCustomize> getNoServiceLiuShiUsersList(Map<String, Object> conditionMap) {
		List<CallcenterUserBaseCustomize> CallcenterUserBaseCustomizeList = callCenterCustomizeMapper.findNoServiceLiuShiUsersList(conditionMap);
		return CallcenterUserBaseCustomizeList;
	}

	@Override
	public List<CallcenterUserBaseCustomize> getNoServiceUsersList(Map<String, Object> conditionMap) {
		List<CallcenterUserBaseCustomize> CallcenterUserBaseCustomizeList = callCenterCustomizeMapper.findNoServiceUsersList(conditionMap);
		return CallcenterUserBaseCustomizeList;
	}
}
