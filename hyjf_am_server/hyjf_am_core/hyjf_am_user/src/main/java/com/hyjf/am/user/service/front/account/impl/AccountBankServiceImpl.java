/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.account.impl;

import com.hyjf.am.user.dao.model.auto.AccountBank;
import com.hyjf.am.user.dao.model.auto.AccountBankExample;
import com.hyjf.am.user.service.front.account.AccountBankService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;
import com.hyjf.common.validator.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountBankServiceImpl, v0.1 2018/7/23 16:17
 */
@Service
public class AccountBankServiceImpl extends BaseServiceImpl implements AccountBankService {

    /**
     * 根据用户id查询银行卡信息
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    @Override
    public List<AccountBank> getBankCardByUserId(Integer userId) {
        AccountBankExample example = new AccountBankExample();
        AccountBankExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return accountBankMapper.selectByExample(example);
    }

    @Override
    public List<AccountBank> selectAccountBank(Integer userId, Integer status) {
        AccountBankExample example = new AccountBankExample();
        example.createCriteria().andUserIdEqualTo(userId).andBankStatusEqualTo(0);
        List<AccountBank> accountBanks = accountBankMapper.selectByExample(example);
        return accountBanks;
    }

    @Override
    public List<AdminBankAccountCheckCustomizeVO> queryAllBankOpenAccount(Integer userId) {
        AdminBankAccountCheckCustomizeVO customize = new AdminBankAccountCheckCustomizeVO();
        customize.setUserId(userId);
        List<AdminBankAccountCheckCustomizeVO> bankOpenAccountList = userAdminBankAccountCheckCustomizeMapper.queryAllBankOpenAccount(customize);
        return bankOpenAccountList;
    }

    @Override
    public AccountBank getBankInfo(Integer userId, Integer bankId) {
        if (Validator.isNotNull(userId) && Validator.isNotNull(bankId)) {
            // 取得用户银行卡信息
            AccountBankExample accountBankExample = new AccountBankExample();
            accountBankExample.createCriteria().andUserIdEqualTo(userId).andIdEqualTo(bankId);
            List<AccountBank> listAccountBank = this.accountBankMapper.selectByExample(accountBankExample);
            if (listAccountBank != null && listAccountBank.size() > 0) {
                return listAccountBank.get(0);
            }
        }
        return null;
    }
}
