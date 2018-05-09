package com.hyjf.cs.borrow.client;


import com.hyjf.am.resquest.user.BankAccountBeanRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.vo.borrow.AccountRechargeVO;
import com.hyjf.am.vo.borrow.AccountVO;
import com.hyjf.am.vo.borrow.BankCardVO;
import com.hyjf.am.vo.borrow.BankReturnCodeConfigVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.Map;

public interface RechargeClient {

    /**
     * 根据用户Id,银行卡号检索用户银行卡信息
     * @param userId
     * @param cardNo
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
