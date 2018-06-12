package com.hyjf.cs.trade.client;


import com.hyjf.am.resquest.user.BankAccountBeanRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.vo.trade.AccountRechargeVO;
import com.hyjf.am.vo.trade.AccountVO;
import com.hyjf.am.vo.trade.BankCardVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

public interface RechargeClient {

    /**
     * 根据用户Id,银行卡号检索用户银行卡信息
     * @param userId
     * @param
     * @return
     */
    BankCardVO selectBankCardByUserId(Integer userId);

    AccountVO getAccount(Integer userId);

    UserVO getUsers(Integer userId);

    AccountRechargeVO selectByOrderId(String orderId);


    void updateByPrimaryKeySelective(AccountRechargeVO accountRecharge);

    BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);

    BankOpenAccountVO selectById(int userId);

    UserInfoVO findUsersInfoById(int userId);

    int selectByOrdId(String ordId);

    int insertSelectiveBank(BankRequest bankRequest);

    BankCardVO getBankCardByCardNo(Integer userId, String cardNo);

    boolean updateBanks(BankAccountBeanRequest bankAccountBeanRequest);
}
