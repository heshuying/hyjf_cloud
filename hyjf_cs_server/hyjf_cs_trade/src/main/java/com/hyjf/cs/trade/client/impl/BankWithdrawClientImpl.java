package com.hyjf.cs.trade.client.impl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankCardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.config.FeeConfigResponse;
import com.hyjf.am.response.trade.AccountResponse;
import com.hyjf.am.response.trade.AccountWithdrawResponse;
import com.hyjf.am.response.trade.BankCardBeanResponse;
import com.hyjf.am.response.trade.BankCardResponse;
import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.trade.BankCallBeanVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.cs.trade.client.BankWithdrawClient;

/**
 * 江西银行提现掉单异常处理Client实现类 create by jijun 20180614
 */
@Service
public class BankWithdrawClientImpl implements BankWithdrawClient {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<AccountWithdrawVO> selectBankWithdrawList() {
		AccountWithdrawResponse response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/bankException/selectBankWithdrawList",
						AccountWithdrawResponse.class)
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
	 * 提现费率
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

	/**
	 * 
	 */
	@Override
	public BankCardVO getBankInfo(Integer userId, String bankId) {
		String url = "http://AM-USER/am-user/bankCard/getBankCard/"+userId+"/"+bankId;
		BankCardResponse response = restTemplate.getForEntity(url, BankCardResponse.class).getBody();
		if(response!=null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 调用后平台操作
	 * @param bean
	 * @param accountwithdraw
	 * @param bankCard
	 */
	@Override
	public Boolean handlerAfterCash(BankCallBeanVO bean, AccountWithdrawVO accountwithdraw, BankCardVO bankCard,String withdrawFee) {
		JSONObject para = new JSONObject();
		para.put("bankCallBeanVO",bean);
		para.put("accountWithdrawVO",accountwithdraw);
		para.put("bankCardVO",bankCard);
		para.put("withdrawFee",withdrawFee);
		String url = "http://AM-TRADE/am-trade/bankException/handlerAfterCash";
		return restTemplate.postForEntity(url,para,Boolean.class).getBody();
	}


}
