package com.hyjf.cs.trade.client;


import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.trade.BankCallBeanVO;
import com.hyjf.am.vo.trade.BankCardVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;

/**
 * 江西银行提现掉单异常处理Client
 * create by jijun 20180614
 */
public interface BankWithdrawExceptionClient {


    List<AccountWithdrawVO> selectBankWithdrawList();

    BankCallBeanVO bankCallFundTransQuery(AccountWithdrawVO accountWithdraw);

    BankCardVO selectBankCardByUserId(int userId);

    int getAccountlistCntByOrdId(String ordId, String cash_success);

    boolean updateAccountwithdraw(AccountWithdrawVO accountWithdraw);

	boolean updateBankWithdraw(AccountVO newAccount);

	AccountVO getAccount(int userId);

	boolean addAccountList(AccountListVO accountList);

	void selectAndUpdateAccountWithdraw(JSONObject paraMap);

	List<FeeConfigVO> getFeeConfig(String bankCode);

	BankCardVO getBankInfo(Integer userId, String bankId);

}
