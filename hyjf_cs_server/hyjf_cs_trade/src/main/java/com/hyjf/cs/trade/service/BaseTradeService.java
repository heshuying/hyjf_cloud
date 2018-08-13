package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.trade.config.SystemConfig;

import java.math.BigDecimal;


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

    BorrowVO getBorrowByNid(String borrowNid);
}
