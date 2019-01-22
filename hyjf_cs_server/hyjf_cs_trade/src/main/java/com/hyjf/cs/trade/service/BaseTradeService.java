package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.trade.bean.BaseBean;

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
     * 根据标的编号，查询汇计划信息
     * @author liubin
     * @return
     */
    HjhPlanVO getPlanByNid(String borrowNid) ;

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

    BorrowInfoVO getBorrowInfoByNid(String borrowNid);

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

    /**
     * 验证外部请求签名
     * @param paramBean
     * @param methodName
     * @return
     */
    boolean verifyRequestSign(BaseBean paramBean, String methodName);

    boolean checkIsNewUserCanInvest2(Integer userId);

    /**
     * 封装借款人企业信息缓存方法
     * @author zhangyk
     * @date 2019/1/22 17:13
     */
    BorrowUserVO getCacheBorrowUser(String borrowNid);
    /**
     * 封装借款人主体信息缓存方法
     * @author zhangyk
     * @date 2019/1/22 17:13
     */
    BorrowManinfoVO getCacheBorrowMainInfo(String borrowNid);
}
