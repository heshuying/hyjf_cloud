package com.hyjf.cs.user.client.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.trade.AccountResponse;
import com.hyjf.am.response.trade.BanksConfigResponse;
import com.hyjf.am.response.user.BankCardResponse;
import com.hyjf.am.resquest.user.BankCardLogRequest;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.vo.trade.AccountVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.cs.user.client.AmBindCardClient;

/**
 * 绑卡原子服务请求类
 * @author hesy
 */
@Service
public class AmBindCardClientImpl implements AmBindCardClient{
	private static Logger logger = LoggerFactory.getLogger(AmBindCardClientImpl.class);
	
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
	 * 统计用户绑定的有效银行卡个数
	 * @param userId
	 * @return
	 */
	@Override
	public int countUserCardValid(String userId) {
		int count = restTemplate
				.getForEntity("http://AM-USER//am-user/card/countUserCardValid/" + userId, Integer.class).getBody();
		return count;
	}
	
	/**
	 * 根据userId删除银行卡信息
	 * @param userId
	 * @return
	 */
	@Override
	public int deleteUserCardByUserId(String userId) {
		int result = restTemplate
				.getForEntity("http://AM-USER//am-user/card/deleteUserCardByUserId/" + userId, Integer.class).getBody();
		return result;
	}
	
	/**
	 * 根据cardNo删除银行卡
	 * @param cardNo
	 * @return
	 */
	@Override
	public int deleteUserCardByCardNo(String cardNo) {
		int result = restTemplate
				.getForEntity("http://AM-USER//am-user/card/deleteUserCardByCardNo/" + cardNo, Integer.class).getBody();
		return result;
	}

	/**
	 * 保存用户绑定的银行卡
	 * @param request
	 * @return
	 */
	@Override
	public int insertUserCard(BankCardRequest request) {
		int result = restTemplate
				.postForEntity("http://AM-USER/am-user/card/insertUserCard", request, Integer.class).getBody();
		return result;
	}

	/**
	 * 更新用户绑定的银行卡
	 * @param request
	 * @return
	 */
	@Override
	public int updateUserCard(BankCardRequest request) {
		int result = restTemplate
				.postForEntity("http://AM-USER/am-user/card/updateUserCard", request, Integer.class).getBody();
		return result;
	}
	
	/**
	 * 根据银行卡号获取bankId
	 * @param cardNo
	 * @return
	 */
	@Override
	public String queryBankIdByCardNo(String cardNo) {
		String result = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/config/queryBankIdByCardNo/" + cardNo, String.class).getBody();
		return result;
	}
	
	/**
	 * 保存绑卡日志
	 */
	@Override
	public int insertBindCardLog(BankCardLogRequest request) {
		int result = restTemplate
				.postForEntity("http://AM-USER/am-user/card/insertBindCardLog", request, Integer.class).getBody();
		return result;
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
	
	/**
	 * 获取用户account信息
	 * @param userId
	 * @return
	 */
	@Override
	public AccountVO getAccount(Integer userId) {
        AccountResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/trade/recharge/getAccount/" + userId, AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
	

}

	