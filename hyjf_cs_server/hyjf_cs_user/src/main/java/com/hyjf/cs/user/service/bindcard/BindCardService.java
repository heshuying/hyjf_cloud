package com.hyjf.cs.user.service.bindcard;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.user.bean.BindCardPageBean;
import com.hyjf.cs.user.bean.BindCardPageRequestBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.cs.user.util.ResultEnum;
import com.hyjf.cs.user.vo.BindCardVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.web.servlet.ModelAndView;

public interface BindCardService extends BaseUserService {

	void checkParamBindCard(BindCardVO bindCardVO, Integer userId);

	void checkParamBindCardPage(WebViewUserVO user);

    String checkParamBindCardPageAPP(WebViewUserVO user);

    ResultEnum checkParamBindCardPageWeChat(WebViewUserVO user);

    Map<String,String> checkParamBindCardPageApi(BindCardPageRequestBean bankCardRequestBean);

    Map<String,Object> callBankBindCardPage(WebViewUserVO user, String userIp, String urlstatus) throws Exception;

	BankCallBean callBankBindCard(BindCardVO bindCardVO, Integer userId, String userIp);

    ModelAndView getCallbankMV(BindCardPageBean bean);

    void updateAfterBindCard(BankCallBean bean) throws ParseException;

	void checkParamUnBindCard(BindCardVO bindCardVO, Integer userId);

    String checkParamUnBindCardAPP(WebViewUserVO webViewUserVO, String cardNo);

    boolean updateAfterUnBindCard(BankCallBean bean);

	BankCallBean callBankUnBindCard(String cardNo, Integer userId);

	void checkParamSendcode(Integer userId, String mobile, String cardNo);

    boolean updateAfterDeleteCard(Integer userId, String userName, String cardNo);

    Integer getBankInterfaceFlagByType(String type);

    BankCardVO queryUserCardValid(String userId, String cardNo);

    BigDecimal getBankBalance(Integer userId, String account);
}

	