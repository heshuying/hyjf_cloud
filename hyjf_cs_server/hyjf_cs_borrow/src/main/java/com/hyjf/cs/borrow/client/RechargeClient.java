package com.hyjf.cs.borrow.client;


import com.hyjf.am.vo.borrow.AccountVO;
import com.hyjf.am.vo.borrow.BankCardVO;
import com.hyjf.am.vo.borrow.BanksConfigVO;
import com.hyjf.am.vo.borrow.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.borrow.AccountRechargeVO;
import com.hyjf.am.vo.borrow.BankReturnCodeConfigVO;
import com.hyjf.am.vo.borrow.AccountListVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

public interface RechargeClient {

    /**
     * 根据用户Id,银行卡号检索用户银行卡信息
     * @param userId
     * @param cardNo
     * @return
     */
    BankCardVO selectBankCardByUserId(Integer userId);
    /**
     * 获取银行卡配置信息
     * @param bankId
     * @return
     */
    BanksConfigVO getBanksConfigByBankId(Integer bankId);

    /**
     * 根据用户ID查询企业用户信息
     * @param userId
     * @return
     */
    CorpOpenAccountRecordVO getCorpOpenAccountRecord(Integer userId);

    AccountVO getAccount(Integer userId);

    UserVO getUsers(Integer userId);

    /**
     * 插入充值记录
     *
     * @param bean
     * @param params
     * @return
     */
    int insertRechargeInfo(BankCallBean bean);

    AccountRechargeVO selectByOrderId(String orderId);

    int updateByExampleSelective(AccountRechargeVO accountRecharge,String orderId);

    int updateBankRechargeSuccess(AccountVO newAccount);

    int insertSelective(AccountListVO accountList);

    void updateByPrimaryKeySelective(AccountRechargeVO accountRecharge);

    BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);

    BankOpenAccountVO selectById(int userId);

    UserInfoVO findUsersInfoById(int userId);
}
