package com.hyjf.cs.trade.service.synbalance;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.SynBalanceVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

/**
 * @author pangchengchao
 * @version SynBalanceService, v0.1 2018/6/19 18:05
 */
public interface SynBalanceService extends BaseTradeService {
    /**
     * @Description 获取用户开户信息
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    BankOpenAccountVO getBankOpenAccount(String accountId);
    /**
     * @Description 获取用户信息
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    UserVO getUsers(Integer userId);

    /**
     * @Description 根据用户id获取账户信息
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    AccountVO getAccount(Integer userId);
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    BankCallBean queryAccountDetails(Integer userId,String accountId,String startDate,String endDate,String type,
                                     String transType,String pageNum,String pageSize,String inpDate,String inpTime,
                                     String relDate,String traceNo);
    /**
     * @Description 获取银行错误返回码
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    String getBankRetMsg(String retCode);
    /**
     * @Description 插入用户线下充值信息
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    boolean insertAccountDetails(AccountVO accountUser, SynBalanceVO synBalanceBean, String username, String ipAddr);
}
