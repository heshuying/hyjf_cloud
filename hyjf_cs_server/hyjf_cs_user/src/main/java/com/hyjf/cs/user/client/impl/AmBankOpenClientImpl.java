package com.hyjf.cs.user.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.response.user.UserEvalationResultResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.resquest.user.BankOpenRequest;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.user.client.AmBankOpenClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * 
 * @author Administrator
 *
 */
@Service
public class AmBankOpenClientImpl implements AmBankOpenClient {
	private static Logger logger = LoggerFactory.getLogger(AmUserClient.class);

	@Autowired
	private RestTemplate restTemplate;

	
	@Override
	public UserInfoVO findUserInfoByCardNo(String cradNo) {
		UserInfoResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/bankopen/findByCardId/" + cradNo, UserInfoResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	
	@Override
	public int updateUserAccountLog(BankOpenRequest request) {
		Integer result = restTemplate
				.postForEntity("http://AM-USER/am-user/bankopen/updateUserAccountLog", request, Integer.class).getBody();
		if (result != null ) {
			return result;
		}
		return 0;
	}

	@Override
	public BankOpenAccountVO selectById(int userId) {
		BankOpenAccountResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/bankopen/selectById/" + userId, BankOpenAccountResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public BankOpenAccountVO selectByAccountId(String accountId) {
		BankOpenAccountResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/bankopen/selectByAccountId/" + accountId, BankOpenAccountResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserEvalationResultVO selectUserEvalationResultByUserId(Integer userId) {
		UserEvalationResultResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/selectUserEvalationResultByUserId/" + userId, UserEvalationResultResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 修改开户日志表的状态
	 * @param userId
	 * @param logOrderId
	 * @param state
	 */
	@Override
	public Integer updateUserAccountLogState(int userId, String logOrderId, int state) {
		BankOpenRequest request = new BankOpenRequest();
		request.setOrderId(logOrderId);
		request.setUserId(userId);
		request.setStatus(state);
		Integer result = restTemplate
				.postForEntity("http://AM-USER/am-user/bankopen/updateUserAccountLogStatus", request, Integer.class).getBody();
		if (result != null) {
			return result;
		}
		return 0;
	}

	/**
	 * 保存用户的开户信息
	 * @param bean
	 * @return
	 */
	@Override
	public Integer saveUserAccount(BankCallBean bean) {
		BankOpenRequest request = new BankOpenRequest();
		request.setOrderId(bean.getLogOrderId());
		request.setUserId(Integer.parseInt(bean.getLogUserId()));
		request.setAccountId(bean.getAccountId());
		request.setBankAccountEsb(bean.getLogClient());
		request.setTrueName(bean.getName());
		request.setIdNo(bean.getIdNo());
		request.setMobile(bean.getMobile());

		Integer result = restTemplate
				.postForEntity("http://AM-USER/am-user/bankopen/updateUserAccount", request, Integer.class).getBody();
		if (result != null) {
			return result;
		}
		return 0;
	}

	/**
	 * 开户成功保存银行卡信息
	 * @param request
	 * @return
	 */
	@Override
	public Integer saveCardNoToBank(BankCardRequest request) {
		Integer result = restTemplate
				.postForEntity("http://AM-USER/am-user/bankopen/saveCardNoToBank", request, Integer.class).getBody();
		if (result != null) {
			return result;
		}
		return 0;
	}

}
