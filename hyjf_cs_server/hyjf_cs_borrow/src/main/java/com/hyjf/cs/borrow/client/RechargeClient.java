package com.hyjf.cs.borrow.client;


import com.hyjf.am.borrow.dao.model.auto.*;
import com.hyjf.am.user.dao.model.auto.BankOpenAccount;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountExample;
import com.hyjf.am.user.dao.model.auto.Users;
import com.hyjf.am.user.dao.model.auto.UsersInfo;
import com.hyjf.am.vo.borrow.*;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.web.bind.annotation.RequestBody;

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

    AccountRechargeVO selectByExample(AccountRechargeExample example);

    int updateByExampleSelective(AccountRechargeVO accountRecharge,AccountRechargeExample accountRechargeExample);

    int updateBankRechargeSuccess(Account newAccount);

    int insertSelective(AccountList accountList);

    void updateByPrimaryKeySelective(AccountRechargeVO accountRecharge);

    AccountVO selectByExample(@RequestBody AccountExample example);

    BankReturnCodeConfig getBankReturnCodeConfig(BankReturnCodeConfigExample example);

    BankOpenAccountVO selectByExample(BankOpenAccountExample example);

    UserInfoVO findUsersInfoById(int userId);
}
