/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.recharge.impl;

import com.hyjf.am.vo.config.BankRechargeConfigVo;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.recharge.AppRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fq
 * @version AppRechargeServiceImpl, v0.1 2018/7/30 9:45
 */
@Service
public class AppRechargeServiceImpl extends BaseUserServiceImpl implements AppRechargeService {

    /**
     * 根据userId查询Account
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    @Override
    public AccountVO getAccountByUserId(Integer userId) {
        return amTradeClient.getAccount(userId);
    }

    /**
     * 根据userId查询银行卡信息
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    @Override
    public BankCardVO selectBankCardByUserId(Integer userId) {
        return amUserClient.getBankCardByUserId(userId);
    }

    /**
     * 根据id查询银行配置
     * @auth sunpeikai
     * @param bankId 主键id
     * @return
     */
    @Override
    public BankConfigVO getBankConfigByBankId(Integer bankId) {
        return amConfigClient.getBankConfigById(bankId);
    }

    /**
     * 根据bankId查询BankRechargeConfig
     * @auth sunpeikai
     * @param bankId
     * @return
     */
    @Override
    public BankRechargeConfigVo getBankRechargeConfigByBankId(Integer bankId) {
        return amConfigClient.getBankRechargeConfigByBankId(bankId);
    }

    /**
     * 根据userId查询银行开户信息
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    @Override
    public BankOpenAccountVO getBankOpenAccountByUserId(Integer userId) {
        return amUserClient.selectById(userId);
    }

    /**
     * 根据用户id查询用户信息
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    @Override
    public UserInfoVO getUserInfoByUserId(Integer userId) {
        return amUserClient.findUserInfoById(userId);
    }

    /**
     * 根据userId 查询user
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public UserVO getUserByUserId(Integer userId) {
        return amUserClient.findUserById(userId);
    }

    /**
     * 根据userId查询CorpOpenAccountRecord
     * @auth sunpeikai
     * @param userId
     * @return
     */
    @Override
    public CorpOpenAccountRecordVO getCorpOpenAccountRecordByUserId(Integer userId) {
        return amUserClient.getCorpOpenAccountRecord(userId);
    }
}
