/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.recharge;

import com.hyjf.am.vo.config.BankRechargeConfigVo;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.service.BaseUserService;

/**
 * @author fq
 * @version AppRechargeService, v0.1 2018/7/30 9:45
 */
public interface AppRechargeService extends BaseUserService {

    /**
     * 根据userId查询Account
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    AccountVO getAccountByUserId(Integer userId);

    /**
     * 根据userId查询银行卡信息
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    BankCardVO selectBankCardByUserId(Integer userId);

    /**
     * 根据id查询银行配置
     * @auth sunpeikai
     * @param bankId 主键id
     * @return
     */
    BankConfigVO getBankConfigByBankId(Integer bankId);

    /**
     * 根据bankId查询BankRechargeConfig
     * @auth sunpeikai
     * @param bankId
     * @return
     */
    BankRechargeConfigVo getBankRechargeConfigByBankId(Integer bankId);

    /**
     * 根据userId查询银行开户信息
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    BankOpenAccountVO getBankOpenAccountByUserId(Integer userId);

    /**
     * 根据用户id查询用户信息
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    UserInfoVO getUserInfoByUserId(Integer userId);

    /**
     * 根据userId 查询user
     * @auth sunpeikai
     * @param
     * @return
     */
    UserVO getUserByUserId(Integer userId);

    /**
     * 根据userId查询CorpOpenAccountRecord
     * @auth sunpeikai
     * @param userId
     * @return
     */
    CorpOpenAccountRecordVO getCorpOpenAccountRecordByUserId(Integer userId);
}
