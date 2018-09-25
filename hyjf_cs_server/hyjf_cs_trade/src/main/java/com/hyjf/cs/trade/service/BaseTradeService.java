package com.hyjf.cs.trade.service;

import java.math.BigDecimal;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.service.BaseService;


public interface BaseTradeService extends BaseService{
    /**
     * @Description 根据token查询user
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/12 10:34
     */
    WebViewUserVO getUserFromCache(int userId);

    UserVO getUserByUserId(Integer userId);

    /**
     * 获取银行开户信息
     *
     * @param userId
     * @return
     */
    BankOpenAccountVO getBankOpenAccount(Integer userId);

    /**
     * @Description 获得江西银行的余额  调用江西银行接口
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/20 9:19
     */
    public BigDecimal getBankBalancePay(Integer userId, String accountId);

    /**
     * @Description 检查风险测评到期时间
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/20 11:43
     */
    public void checkEvaluation(UserVO user);

    /**
     * 检查用户是否是新手 true 是  false 不是
     * @param userId
     * @return
     */
    public boolean checkIsNewUserCanInvest(Integer userId);

    AccountVO getAccountByUserId(Integer userId);

    BankCardVO getBankCardVOByUserId(Integer userId);

    BorrowAndInfoVO getBorrowByNid(String borrowNid);

    /**
     * 根据用户Id,银行卡号检索用户银行卡信息
     * @param userId
     * @param
     * @return
     */
    BankCardVO selectBankCardByUserId(Integer userId);

    /**
     * 查询userInfo
     * @param userId
     * @return
     */
    UserInfoVO getUsersInfoByUserId(Integer userId);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    UserVO getUsers(Integer userId);
}
