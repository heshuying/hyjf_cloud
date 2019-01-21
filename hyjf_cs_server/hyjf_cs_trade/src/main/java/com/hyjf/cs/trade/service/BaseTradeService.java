package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.trade.bean.BaseBean;

import java.math.BigDecimal;
import java.util.List;


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
     * 获取标的还款信息
     *
     * @param borrowNid
     * @return
     */
    BorrowRepayVO selectBorrowRepay(String borrowNid);

    /**
     * 获取标的放款信息
     *
     * @param borrowNid
     * @return
     */
    List<BorrowRecoverVO> selectBorrowRecoverListByBorrowNid(String borrowNid);

    /**
     * 获取标的投资人信息
     *
     * @param borrowNid
     * @return
     */
    List<BorrowTenderVO> selectBorrowTenderByBorrowNid(String borrowNid);

    /**
     * 获取借款人信息（企业）
     *
     * @param borrowNid
     * @return
     */
    BorrowUserVO selectBorrowUsersByBorrowNid(String borrowNid);

    /**
     * 获取借款人信息（个人）
     *
     * @param borrowNid
     * @return
     */
    BorrowManinfoVO selectBorrowMainfo(String borrowNid);

    /**
     * 获取用户CA认证信息
     *
     * @param username
     * @param cardNo
     * @param idType
     * @return
     */
    CertificateAuthorityVO selectCAInfoByUsername(String username, String cardNo, Integer idType);

    /**
     * 根据平台借款等级变换中互金借款等级
     *
     * @param borrowLevel
     * @return
     */
    Integer getBorrowLevel(String borrowLevel);
}
