/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.withdraw;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.AccountBankVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.cs.user.bean.UserWithdrawRequestBean;
import com.hyjf.cs.user.service.BaseUserService;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserWithdrawService, v0.1 2018/7/23 15:18
 */
public interface UserWithdrawService extends BaseUserService {
    /**
     * 根据userId获取accountBank
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    List<AccountBankVO> getBankHfCardByUserId(Integer userId);

    /**
     * 根据银行名查询银行配置
     * @auth sunpeikai
     * @param bank 银行code，例如：招商银行,CMB
     * @return
     */
    BankConfigVO getBankInfo(String bank);

    /**
     * 根据id查询银行配置
     * @auth sunpeikai
     * @param bankId 主键id
     * @return
     */
    JxBankConfigVO getJxBankConfigByBankId(Integer bankId);

    /**
     * 获取提现信息
     * @param userId
     * @param ret
     * @param version
     * @param bankCode
     * @param getcash
     * @return
     */
    JSONObject getCashInfo(Integer userId, JSONObject ret, String version, String bankCode, String getcash);

    /**
     * 根据userId获取BankCard
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    List<BankCardVO> getBankCardByUserId(Integer userId);

    /**
     * api用户提现
     * @auth sunpeikai
     * @param
     * @return
     */
    ModelAndView withdraw(UserWithdrawRequestBean userWithdrawRequestBean, HttpServletRequest request);

}
