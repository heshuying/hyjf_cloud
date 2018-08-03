/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.finance.impl;

import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.user.dao.mapper.customize.admin.finance.BankAccountManageCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.BankOpenAccount;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountExample;
import com.hyjf.am.user.dao.model.customize.admin.finance.BankAccountManageCustomize;
import com.hyjf.am.user.service.admin.finance.BankAccountManageService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageServiceImpl, v0.1 2018/6/29 14:02
 */
@Service
public class BankAccountManageServiceImpl extends BaseServiceImpl implements BankAccountManageService {

    @Autowired
    BankAccountManageCustomizeMapper bankAccountManageCustomizeMapper;

    @Override
    public Integer queryAccountCount(BankAccountManageRequest bankAccountManageRequest) {
        // 部门
        if (Validator.isNotNull(bankAccountManageRequest.getCombotreeSrch())) {
            if (bankAccountManageRequest.getCombotreeSrch().contains(StringPool.COMMA)) {
                String[] list = bankAccountManageRequest.getCombotreeSrch().split(StringPool.COMMA);
                bankAccountManageRequest.setCombotreeListSrch(list);
            } else {
                bankAccountManageRequest.setCombotreeListSrch(new String[] { bankAccountManageRequest.getCombotreeSrch() });
            }
        }
        Integer accountCount = null;

        // 为了优化检索查询，判断参数是否全为空，为空不进行带join count
        if(checkFormAllBlank(bankAccountManageRequest)){
            accountCount = this.bankAccountManageCustomizeMapper.queryAccountCountAll(bankAccountManageRequest);
        }else{
            accountCount = this.bankAccountManageCustomizeMapper.queryAccountCount(bankAccountManageRequest);
        }
        return accountCount;
    }

    @Override
    public List<BankAccountManageCustomize> queryAccountInfos(BankAccountManageRequest bankAccountManageRequest) {
        // 为了优化检索查询，判断参数是否全为空，为空不进行带join count
        if(checkFormAllBlank(bankAccountManageRequest)){
            bankAccountManageRequest.setInitQuery(1);
        }
        List<BankAccountManageCustomize> accountInfos = this.bankAccountManageCustomizeMapper.queryAccountInfos(bankAccountManageRequest);
        return accountInfos;
    }

    @Override
    public BankOpenAccount getBankOpenAccount(Integer userId) {
        BankOpenAccountExample accountExample = new BankOpenAccountExample();
        BankOpenAccountExample.Criteria crt = accountExample.createCriteria();
        crt.andUserIdEqualTo(userId);
        List<BankOpenAccount> bankAccounts = this.bankOpenAccountMapper.selectByExample(accountExample);
        if (bankAccounts != null && bankAccounts.size() == 1) {
            return bankAccounts.get(0);
        }
        return null;
    }

    private boolean checkFormAllBlank(BankAccountManageRequest bankAccountManageRequest) {
        if (StringUtils.isBlank(bankAccountManageRequest.getUserNameSrch())
                && StringUtils.isBlank(bankAccountManageRequest.getCombotreeSrch())
                && StringUtils.isBlank(bankAccountManageRequest.getAccountSrch())
                && StringUtils.isBlank(bankAccountManageRequest.getVipSrch())) {
            return true;
        }
        return false;
    }
}
