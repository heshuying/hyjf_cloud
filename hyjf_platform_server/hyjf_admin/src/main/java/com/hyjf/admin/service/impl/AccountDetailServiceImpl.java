/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.AccountDetailService;
import com.hyjf.am.response.admin.AccountDetailResponse;
import com.hyjf.am.response.admin.AdminAccountDetailDataRepairResponse;
import com.hyjf.am.response.trade.account.AccountListResponse;
import com.hyjf.am.response.trade.account.AccountTradeResponse;
import com.hyjf.am.resquest.admin.AccountDetailRequest;
import com.hyjf.am.resquest.admin.AccountListRequest;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author nixiaoling
 * @version RegistRecordServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class AccountDetailServiceImpl implements AccountDetailService {
    @Autowired
    private AmTradeClient accountDetailClient;

    /**
     * 查找资金明细列表
     *
     * @param request
     * @return
     */

    @Override
    public AccountDetailResponse findAccountDetailList(AccountDetailRequest request) {
        AccountDetailResponse accountResponse = accountDetailClient.findAccountDetailList(request);
        return accountResponse;
    }

    /**
     * 查询交易明细最小的id
     */
    @Override
    public AdminAccountDetailDataRepairResponse getdetailDataRepair(int userId) {
        AdminAccountDetailDataRepairResponse accountResponse = accountDetailClient.accountdetailDataRepair(userId);
        return accountResponse;
    }

    /**
     * 查询出还款后,交易明细有问题的用户ID
     */
    @Override
    public AdminAccountDetailDataRepairResponse queryAccountDetailErrorUserList() {
        AdminAccountDetailDataRepairResponse accountResponse = accountDetailClient.queryAccountDetailErrorUserList();
        return accountResponse;
    }

    /**
     * 根据Id查询此条交易明细
     */
    @Override
    public AccountListResponse selectAccountById(int accountId) {
        return accountDetailClient.selectAccountById(accountId);
    }

    /**
     * 查询此用户的下一条交易明细
     * @param accountId
     * @param userId
     * @return
     */
    @Override
    public AccountListResponse selectNextAccountList(int accountId, int userId) {
        return accountDetailClient.selectNextAccountList(accountId, userId);
    }

    /**
     * 根据查询用交易类型查询用户操作金额
     * @param tradeValue
     * @return
     */
    @Override
    public AccountTradeResponse selectAccountTradeByValue(String tradeValue) {
        return accountDetailClient.selectAccountTradeByValue(tradeValue);
    }

    /**
     * 更新用户的交易明细
     * @param accountListRequest
     * @return
     */
    @Override
    public int updateAccountList(AccountListRequest accountListRequest) {
        return accountDetailClient.updateAccountList(accountListRequest);
    }


    /**
     * @Description 获取交易类型列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public List<AccountTradeVO> selectTradeTypes(){
        return accountDetailClient.selectTradeTypes();
    }

    /**
     * 交易明细修复
     * @param userId
     * @param accountListId
     * @return
     */
    @Override
    public String repayDataRepair(Integer userId, Integer accountListId) {
        // 根据Id查询此条交易明细
        AccountListResponse accountListResponse = this.selectAccountById(accountListId);
        if (null != accountListResponse && null != accountListResponse.getResult()) {
            AccountListVO accountListVO = accountListResponse.getResult();
            // 获取账户可用余额
            BigDecimal balance = accountListVO.getBalance();
            // 查询此用户的下一条交易明细
            AccountListResponse accountListResponseNext = this.selectNextAccountList(accountListId, userId);
            // 如果下一条交易明细不为空
            if (null != accountListResponseNext && null != accountListResponseNext.getResult()) {
                AccountListVO accountListVOnext = accountListResponseNext.getResult();
                // 根据查询用交易类型查询用户操作金额
                AccountTradeResponse accountTradeResponse = this.selectAccountTradeByValue(accountListVOnext.getTrade());
                if (null != accountTradeResponse && null != accountTradeResponse.getResult()) {
                    AccountTradeVO accountTradeVO = accountTradeResponse.getResult();
                    // 更新交易明细的账户余额
                    if ("ADD".equals(accountTradeVO.getOperation())) {
                        accountListVOnext.setBalance(balance.add(accountListVOnext.getAmount()));
                    } else if ("SUB".equals(accountTradeVO.getOperation()) && !"cash_success".equals(accountListVOnext.getTrade())) {
                        accountListVOnext.setBalance(balance.subtract(accountListVOnext.getAmount()));
                    } /*else if ("SUB".equals(accountTradeVO.getOperation())&&"cash_success".equals(accountListVOnext.getTrade())){
                        // 提现不处理
                        //return ;
                    }*/ else if ("UNCHANGED".equals(accountTradeVO.getOperation())) {
                        accountListVOnext.setBalance(balance);
                    }
                    // 更新用户的交易明细
                    AccountListRequest accountListRequest = new AccountListRequest();
                    //
                    BeanUtils.copyProperties(accountListVOnext, accountListRequest);
                    boolean isAccountListUpdateFlag = this.updateAccountList(accountListRequest) > 0 ? true : false;
                    if (isAccountListUpdateFlag) {
                        // 递归更新下一条交易明细
                        this.repayDataRepair(userId, accountListVOnext.getId());

                    } else {
                        return "交易明细更新失败,交易明细ID:" + accountListVOnext.getId();
                    }
                } else {
                    return "查询ht_account_trade交易类型失败,交易明细Value:" + accountListVOnext.getTrade();
                }

            } else {
                return "未查询到下一条交易明细,上一条交易明细ID:" + accountListId;
            }
        } else {
            return "获取交易明细失败" + accountListId;
        }
        return null;
    }
}
