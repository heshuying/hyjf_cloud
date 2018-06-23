package com.hyjf.am.trade.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.AccountWithdraw;
import com.hyjf.am.vo.trade.account.AccountVO;

import java.util.List;

/**
 *江西银行提现掉单异常处理定时任务
 * create by jijun 20170614
 */
public interface BankWithdrawService {

    List<AccountWithdraw> selectBankWithdrawList();

	int updateBankWithdraw(AccountVO accountVO);

    Boolean updateHandlerAfterCash(JSONObject para) throws Exception;
}
