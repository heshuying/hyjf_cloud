/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.account.impl;

import com.hyjf.am.resquest.user.AccountMobileSynchRequest;
import com.hyjf.am.user.dao.model.auto.AccountMobileSynch;
import com.hyjf.am.user.dao.model.auto.AccountMobileSynchExample;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.service.front.account.BindCardService;
import com.hyjf.am.user.service.front.account.MobileSynchronizeService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjun
 * @version MobileSynchronizeServiceImpl, v0.1 2018/6/22 11:40
 */
@Service
public class MobileSynchronizeServiceImpl extends BaseServiceImpl implements MobileSynchronizeService {

    @Autowired
    protected BindCardService bindCardService;

    @Override
    public List<AccountMobileSynch> searchAccountMobileSynch(String flag) {
        AccountMobileSynchExample accountMobileSynchExample = new AccountMobileSynchExample();
        AccountMobileSynchExample.Criteria criteria = accountMobileSynchExample.createCriteria();
        criteria.andStatusEqualTo(0);
        if (StringUtils.isNotBlank(flag) && StringUtils.equals("1", flag)) {
            criteria.andFlagEqualTo(1);
        } else if (StringUtils.isNotBlank(flag) && StringUtils.equals("2", flag)) {
            criteria.andFlagEqualTo(2);
        }

        return accountMobileSynchMapper.selectByExample(accountMobileSynchExample);
    }

    /**
     * 更新银行卡号手机号同步表
     *
     * @param accountMobileSynchRequest
     * @return
     */
    @Override
    public boolean updateAccountMobileSynch(AccountMobileSynchRequest accountMobileSynchRequest) {
        if (accountMobileSynchRequest.getUpdateFlag() == 1) {
            //根据用户ID删除银行卡
            int deleteBankCard = bindCardService.deleteUserCardByUserId(accountMobileSynchRequest.getBankCardRequest().getUserId());

            //插入银行卡
            BankCard bankCard = new BankCard();
            BeanUtils.copyProperties(accountMobileSynchRequest.getBankCardRequest(), bankCard);
            int insertBankCard = bindCardService.insertUserCard(bankCard);

            //更新银行卡号手机号同步表
            AccountMobileSynch accountMobileSynch = new AccountMobileSynch();
            BeanUtils.copyProperties(accountMobileSynchRequest.getAccountMobileSynchVO(), accountMobileSynch);
            int updateAccountMobile = accountMobileSynchMapper.updateByPrimaryKeySelective(accountMobileSynch);

            return deleteBankCard > 0 && insertBankCard > 0 && updateAccountMobile > 0 ? true : false;
        } else {
            AccountMobileSynch accountMobileSynch = new AccountMobileSynch();
            BeanUtils.copyProperties(accountMobileSynchRequest.getAccountMobileSynchVO(), accountMobileSynch);
            return accountMobileSynchMapper.updateByPrimaryKeySelective(accountMobileSynch) > 0 ? true : false;
        }
    }
}
