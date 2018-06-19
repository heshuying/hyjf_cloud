package com.hyjf.cs.trade.client.impl;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.config.FeeConfigResponse;
import com.hyjf.am.response.trade.AccountResponse;
import com.hyjf.am.response.trade.AccountwithdrawResponse;
import com.hyjf.am.response.trade.BankCardBeanResponse;
import com.hyjf.am.response.trade.BankCardResponse;
import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.trade.BankCallBeanVO;
import com.hyjf.am.vo.trade.BankCardVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.cs.trade.client.BankWithdrawExceptionClient;

/**
 * 江西银行提现掉单异常处理Client实现类 create by jijun 20180614
 */
@Service
public class BankWithdrawExceptionClientImpl implements BankWithdrawExceptionClient {
	private static Logger logger = LoggerFactory.getLogger(BankWithdrawExceptionClientImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<AccountWithdrawVO> selectBankWithdrawList() {
		AccountwithdrawResponse response = restTemplate
				.getForEntity("http://AM-TRADE/bankWithdrawException/selectBankWithdrawList",
						AccountwithdrawResponse.class)
				.getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 调用银行接口
	 * 
	 * @param accountWithdraw
	 * @return
	 */
	@Override
	public BankCallBeanVO bankCallFundTransQuery(AccountWithdrawVO accountWithdraw) {
		BankCardBeanResponse response = restTemplate
				.postForEntity("http://AM-CONFIG/am-config/config/bankCallFundTransQuery", accountWithdraw,
						BankCardBeanResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户ID查询用户银行卡信息
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public BankCardVO selectBankCardByUserId(int userId) {
		BankCardResponse response = restTemplate
				.getForEntity("http://AM-USER/bankCard/selectBankCardByUserId/" + userId, BankCardResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public int getAccountlistCntByOrdId(String orderId, String cashSuccess) {
		String url = "http://AM-TRADE/am-trade/account/getAccountlistCntByOrdId/" + orderId + "/" + cashSuccess;
		return restTemplate.getForEntity(url, Integer.class).getBody();
	}

	/**
	 * 更新账户提现 add by jijun 20180616
	 */
	@Override
	public boolean updateAccountwithdraw(AccountWithdrawVO accountWithdraw) {
		String url = "http://AM-TRADE/am-trade/accountWithdraw/updateAccountwithdraw";
		return restTemplate.postForEntity(url, accountWithdraw, Boolean.class).getBody();
	}

	/**
	 * 更新银行提现 add by jijun 20180616
	 */
	@Override
	public boolean updateBankWithdraw(AccountVO newAccount) {
		String url = "http://AM-TRADE/am-trade/bankWithdraw/updateBankWithdraw";
		return restTemplate.postForEntity(url, newAccount, Boolean.class).getBody();
	}

	/**
	 * 获取账户
	 * add by jijun 20180616
	 */
	@Override
	public AccountVO getAccount(int userId) {
		AccountResponse response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/account/getAccountByUserId/" + userId, AccountResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 保存accountList
	 * add by jijun 20180616
	 */
	@Override
	public boolean addAccountList(AccountListVO accountList) {
		String url = "http://AM-TRADE/am-trade/accountList/addAccountList";
		return restTemplate.postForEntity(url,accountList,Boolean.class).getBody();
	}

	@Override
	public void selectAndUpdateAccountWithdraw(JSONObject paraMap) {
		restTemplate
				.postForEntity("http://AM-TRADE/am-trade/accountWithdraw/selectAndUpdateAccountWithdraw",paraMap, AccountwithdrawResponse.class)
				.getBody();

	}

	/**
	 * 提现费率
	 * @param userId
	 * @param bankId
	 * @param transAmt
	 * @return
	 */
	@Override
	public List<FeeConfigVO> getFeeConfig(String bankCode) {
		String url = "http://AM-CONFIG/am-config/feeConfig/getFeeConfig/"+bankCode;
		FeeConfigResponse response = restTemplate.getForEntity(url, FeeConfigResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;

	}

	@Override
	public BankCardVO getBankInfo(Integer userId, String bankId) {
		// TODO Auto-generated method stub
		return null;
	}


}
