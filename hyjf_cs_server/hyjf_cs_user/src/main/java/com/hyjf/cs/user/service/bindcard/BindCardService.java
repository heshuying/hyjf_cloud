package com.hyjf.cs.user.service.bindcard;

import java.text.ParseException;
import java.util.Map;

import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.cs.user.vo.BindCardVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

public interface BindCardService extends BaseUserService {

	void checkParamBindCard(BindCardVO bindCardVO, Integer userId);

	void checkParamBindCardPage(WebViewUserVO user);

	Map<String,Object> callBankBindCardPage(WebViewUserVO user, String userIp, String urlstatus) throws Exception;

	BankCallBean callBankBindCard(BindCardVO bindCardVO, Integer userId, String userIp);

	void updateAfterBindCard(BankCallBean bean) throws ParseException;

	void checkParamUnBindCard(BindCardVO bindCardVO, Integer userId);

    String checkParamUnBindCardAPP(WebViewUserVO webViewUserVO, String cardNo);

    void updateAfterUnBindCard(BankCallBean bean);

	BankCallBean callBankUnBindCard(String cardNo, Integer userId);

	void checkParamSendcode(Integer userId, String mobile, String cardNo);

    boolean updateAfterDeleteCard(Integer userId, String userName, String cardNo);

    Integer getBankInterfaceFlagByType(String type);
}

	