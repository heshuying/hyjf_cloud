package com.hyjf.cs.trade.client.impl;


import com.hyjf.am.response.trade.AccountResponse;
import com.hyjf.am.response.trade.BanksConfigResponse;
import com.hyjf.am.response.user.BankCardResponse;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.user.BankCardVO;

import com.hyjf.cs.trade.client.BindCardClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
@Service
public class BindCardClientImpl implements BindCardClient {
	private static Logger logger = LoggerFactory.getLogger(BindCardClient.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 查询用户已绑定的有效卡
	 * @param userId
	 * @param cardNo
	 * @return
	 */
	@Override
	public BankCardVO queryUserCardValid(String userId, String cardNo) {
		BankCardResponse response = restTemplate
				.getForEntity("http://AM-USER//am-user/card/queryUserCardValid/" + userId + "/" + cardNo, BankCardResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
	/**
	 * 获取银行卡配置信息
	 * @param bankId
	 * @return
	 */
	@Override
	public BanksConfigVO getBanksConfigByBankId(String bankId) {
		BanksConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/config/getBanksConfigByBankId/" + bankId, BanksConfigResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public int updateBankCardPayAllianceCode(BankCardVO updateBankCard) {
		// TODO  待实现
		int result = restTemplate
				.postForEntity("http://AM-USER/am-user/card/updateBankCardPayAllianceCode", updateBankCard, Integer.class).getBody();
		return result;
	}


	/**
	 * 获取用户account信息
	 * @param userId
	 * @return
	 */
	@Override
	public AccountVO getAccount(Integer userId) {
        AccountResponse response = restTemplate
                .getForEntity("http://AM-BORROW/am-borrow/recharge/getAccount/" + userId, AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}

	