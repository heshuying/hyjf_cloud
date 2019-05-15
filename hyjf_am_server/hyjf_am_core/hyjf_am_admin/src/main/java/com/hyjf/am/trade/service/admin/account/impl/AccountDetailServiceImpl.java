/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.account.impl;

import com.hyjf.am.resquest.admin.AccountListRequest;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountExample;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.dao.model.auto.AccountTrade;
import com.hyjf.am.trade.dao.model.customize.AdminAccountDetailCustomize;
import com.hyjf.am.trade.dao.model.customize.AdminAccountDetailDataRepairCustomize;
import com.hyjf.am.trade.service.admin.account.AccountDetailService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version AccountDetailServiceImpl, v0.1 2018/6/29 13:58
 */
@Service
public class AccountDetailServiceImpl extends BaseServiceImpl implements AccountDetailService {


    /**
     * 资金明细 （列表）
     *
     * @param mapParam
     * @return
     */
    @Override
    public List<AdminAccountDetailCustomize> queryAccountDetails(Map<String, Object> mapParam) {
        List<AdminAccountDetailCustomize> listAccountDetail = adminAccountDetailCustomizeMapper.queryAccountDetails(mapParam);
        return listAccountDetail;
    }

    @Override
    public int countAccountDetail(Map<String, Object> mapParam) {
        int intCount = adminAccountDetailCustomizeMapper.queryAccountDetailCount(mapParam);
        return intCount;
    }

    /**
     * 查询出款后,交易明细有问题的用户ID
     * @return
     */
    @Override
    public List<AdminAccountDetailDataRepairCustomize> queryAccountDetailErrorUserList() {
        return adminAccountDetailCustomizeMapper.queryAccountDetailErrorUserList();
    }

    /**
     * 查询交易明细最小的id
     * @param userId
     * @return
     */
    @Override
    public AdminAccountDetailDataRepairCustomize queryAccountDetailIdByUserId(int userId) {
        List<AdminAccountDetailDataRepairCustomize> accountDetailDataRepairCustomizeList = adminAccountDetailCustomizeMapper.queryAccountDetailIdByUserId(userId);
        if (null != accountDetailDataRepairCustomizeList && accountDetailDataRepairCustomizeList.size() > 0) {
            return accountDetailDataRepairCustomizeList.get(0);
        }
        return null;
    }

    /**
     * 根据Id查询此条交易明细
     * @param accountId
     * @return
     */
    @Override
    public AccountList selectAccountById(int accountId) {
        AccountList accountLists = accountListMapper.selectByPrimaryKey(accountId);
        return accountLists;
    }

    /**
     * 查询此用户的下一条交易明细
     * @param accountId
     * @param userId
     * @return
     */
    @Override
    public AccountList selectNextAccountList(int accountId, int userId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("id", accountId);
        AccountList accountList = adminAccountDetailCustomizeMapper.selectNextAccountList(param);
        return accountList;
    }

    /**
     * 根据查询用交易类型查询用户操作金额
     * @param tradeValue
     * @return
     */
    @Override
    public AccountTrade selectAccountTradeByValue(String tradeValue) {
        List<AccountTrade> accountTradeList = adminAccountDetailCustomizeMapper.selectAccountTrade(tradeValue);
        if (null != accountTradeList && accountTradeList.size() > 0) {
            return accountTradeList.get(0);
        }
        return null;
    }

    /**
     * 更新用户的交易明细
     * @param accountListRequest
     * @return
     */
    @Override
    public int updateAccountList(AccountListRequest accountListRequest) {
        AccountList accountList = new AccountList();
        BeanUtils.copyProperties(accountListRequest, accountList);
        int updFlg = accountListMapper.updateByPrimaryKeySelective(accountList);
        if (updFlg > 0) {
            logger.info("==================更新用户的交易明细成功!======");
        } else {
            throw new RuntimeException("============更新用户的交易明细失败!========");
        }
        return updFlg;
    }

    /**
     * 修改Account表的字段
     *
     * @param userId
     * @param accountId
     * @return
     */
    @Override
    public Integer updateAccountNumberByUserId(Integer userId, String accountId) {
        Account account = getAccount(userId);
        if(account!=null){
            account.setAccountId(accountId);
            return this.accountMapper.updateByPrimaryKeySelective(account);
        }

        return 0;
    }

    /**
     * 未开户用户销户成功后,更新删除用户账户表
     *
     * @param userId
     * @return
     */
    @Override
    public int deleteUserAccountAction(String userId) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(Integer.parseInt(userId));
        return this.accountMapper.deleteByExample(example);
    }
}
