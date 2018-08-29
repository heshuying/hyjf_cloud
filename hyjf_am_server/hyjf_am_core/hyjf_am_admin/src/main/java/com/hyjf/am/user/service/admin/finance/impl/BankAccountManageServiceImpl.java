/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.finance.impl;

import com.hyjf.am.user.dao.model.auto.BankOpenAccount;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountExample;
import com.hyjf.am.user.service.admin.finance.BankAccountManageService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageServiceImpl, v0.1 2018/6/29 14:02
 */
@Service("amUserBankAccountManageServiceImpl")
public class BankAccountManageServiceImpl extends BaseServiceImpl implements BankAccountManageService {

    @Override
    public BankOpenAccount getBankOpenAccount(Integer userId) {
        BankOpenAccountExample accountExample = new BankOpenAccountExample();
        BankOpenAccountExample.Criteria crt = accountExample.createCriteria();
        crt.andUserIdEqualTo(userId);
        List<BankOpenAccount> bankAccounts = bankOpenAccountMapper.selectByExample(accountExample);
        if (bankAccounts != null && bankAccounts.size() == 1) {
            return bankAccounts.get(0);
        }
        return null;
    }
}
