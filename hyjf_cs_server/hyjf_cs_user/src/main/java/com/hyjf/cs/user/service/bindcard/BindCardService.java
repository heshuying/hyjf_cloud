package com.hyjf.cs.user.service.bindcard;

import java.text.ParseException;

import com.hyjf.cs.user.vo.BindCardVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

public interface BindCardService {

	boolean checkIsOpen(Integer userId);

	void checkParamBindCard(BindCardVO bindCardVO, Integer userId);

	BankCallBean callBankBindCard(BindCardVO bindCardVO, Integer userId, String userIp);

	void updateAfterBindCard(BankCallBean bean) throws ParseException;

	void checkParamUnBindCard(BindCardVO bindCardVO, Integer userId);

	void updateAfterUnBindCard(BankCallBean bean);

	BankCallBean callBankUnBindCard(BindCardVO bindCardVO, Integer userId);

	void checkParamSendcode(Integer userId, String mobile, String cardNo);

	BankCallBean callSendCode(Integer userId, String cardNo, String mobile);

	boolean updateAfterSendCode(BankCallBean bean);
}

	