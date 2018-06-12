/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.user.dao.mapper.auto.BankCardMapper;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.auto.BankCardExample;
import com.hyjf.am.user.service.CallCenterBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjun
 * @version CallCenterBankServiceImpl, v0.1 2018/6/6 14:22
 */
@Service
public class CallCenterBankServiceImpl implements CallCenterBankService {
    @Autowired
    private BankCardMapper bankCardMapper;

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
}
