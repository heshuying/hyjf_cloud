/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.synbalance;

import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.vo.admin.UnderLineRechargeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.SynBalanceVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.bean.SynBalanceResultBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.List;

/**
 * @author zhangqingqing
 * @version SynBalanceService, v0.1 2018/7/25 15:10
 */
public interface SynBalanceService extends BaseUserService {
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

    /**
     * @Description 根据用户id获取账户信息
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    AccountVO getAccount(Integer userId);

    /**
     * @Description 获取银行错误返回码
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    String getBankRetMsg(String retCode);

    /**
     * 插入用户线下充值信息
     * @param accountUser
     * @param synBalanceBean
     * @param user
     * @param ipAddr
     * @Author pangchengchao
     * @return
     */
    boolean insertAccountDetails(AccountVO accountUser, SynBalanceVO synBalanceBean, UserVO user, String ipAddr);

    /**
     * 获取数据表中线下充值类型
     * @return
     * @Author : huanghui
     */
    List<UnderLineRechargeVO> selectUnderLineRechargeList(UnderLineRechargeRequest request);

    /**
     * 同步余额
     * @param accountId
     * @param ip
     * @return
     */
    SynBalanceResultBean synBalance(String accountId, String ip);
}
