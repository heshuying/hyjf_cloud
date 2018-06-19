package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.Accountwithdraw;
import com.hyjf.am.vo.trade.account.AccountVO;

import java.util.List;

/**
 *江西银行提现掉单异常处理定时任务
 * create by jijun 20170614
 */
public interface BankWithdrawService {

    List<Accountwithdraw> selectBankWithdrawList();

	int updateBankWithdraw(AccountVO accountVO);

}
