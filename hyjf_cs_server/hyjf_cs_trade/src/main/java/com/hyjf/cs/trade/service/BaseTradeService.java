package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.config.WithdrawRuleConfigVO;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.RightBorrowVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.cs.common.bean.result.WebResult;
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
    BigDecimal getBankBalancePay(Integer userId, String accountId);

    /**
     * @Description 检查风险测评到期时间
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/20 11:43
     */
    void checkEvaluation(UserVO user);

    /**
     * 检查用户是否是新手 true 是  false 不是
     * @param userId
     * @return
     */
    boolean checkIsNewUserCanInvest(Integer userId);

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
     * 根据标的编号查询Borrow信息
     * @param borrowNid
     * @return
     */
    RightBorrowVO selectBorrowByBorrowNid(String borrowNid);
     /** 获取标的还款信息
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

    /**
     * 获取散标债转信息表
     *
     * @param creditNid
     * @return
     */
    BorrowCreditVO selectBorrowCreditByCreditNid(String creditNid);

    /**
     * 获取散标债转承接人的承接信息
     *
     * @param creditNid
     * @return
     */
    List<CreditTenderVO> selectCreditTenderByCreditNid(String creditNid);

    /**
     * 承接人承接记录
     *
     * @param creditNid
     * @return
     */
    List<HjhDebtCreditTenderVO> selectHjhDebtCreditTenderByCreditNid(String creditNid);

    /**
     * 汇计划债转表
     *
     * @param creditNid
     * @return
     */
    HjhDebtCreditVO selectHjhDebtCreditByCreditNid(String creditNid);

    /**
     * 压缩zip文件包
     *
     * @param sb
     * @param zipName
     * @return
     */
    boolean writeZip(StringBuffer sb, String zipName);


    /**
     * 用户提现校验
     *
     * @param userId
     * @param withdrawMoney
     * @return
     */
    WebResult<Object> userBankWithdrawCheck(Integer userId, String withdrawMoney);



    /**
     * 获取提现规则配置
     *
     * @param userId
     * @param withdrawMoney
     * @return
     */
    WithdrawRuleConfigVO getWithdrawRuleConfig(int userId, String withdrawMoney);
}
