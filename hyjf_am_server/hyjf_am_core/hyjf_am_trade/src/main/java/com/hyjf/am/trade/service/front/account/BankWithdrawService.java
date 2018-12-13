package com.hyjf.am.trade.service.front.account;

import com.hyjf.am.resquest.trade.AfterCashParamRequest;
import com.hyjf.am.trade.dao.model.auto.AccountWithdraw;
import com.hyjf.am.vo.trade.account.AccountVO;

import java.util.List;

/**
 *江西银行提现掉单异常处理定时任务
 * create by jijun 20170614
 */public interface BankWithdrawService {

    List<AccountWithdraw> selectBankWithdrawList();

	int updateBankWithdraw(AccountVO accountVO);

    void updateHandlerAfterCash(AfterCashParamRequest para) throws Exception;
}
