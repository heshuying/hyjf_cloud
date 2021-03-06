package com.hyjf.cs.user.service.account.impl;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.account.RdfAccountService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 融东风用户账户接口实现类
 */
@Service
public class RdfAccountServiceImpl extends BaseUserServiceImpl implements RdfAccountService{

	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private AmTradeClient amTradeClient;

	/**
	 * 通过手机号获取账户余额
	 * @param mobile
	 * @return
	 */
	@Override
	public String getBalance(String mobile) {
		
		UserVO user=amUserClient.findUserByMobile(mobile);
		if(Validator.isNotNull(user)) {
			Integer userId = user.getUserId();
			AccountVO account=amTradeClient.getAccount(userId);
			if(Validator.isNotNull(account)) {
				return account.getBankBalance().toString();
			}else {
				return "0";
			}
		}else {
			return "0";
		}
	}


	/**
	 * 通过手机号获取银行卡信息
	 * @param mobile
	 * @return
	 */
	@Override
	public BankCardVO getBankCard(String mobile) {
		UserVO user=amUserClient.findUserByMobile(mobile);
		if(Validator.isNull(user)) {
			return null;
		}
		Integer userId = user.getUserId();
		//Integer status
		List<BankCardVO> list = amUserClient.selectBankCardByUserIdAndStatus(userId,1);
		if (CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);

	}

	/**
	 * 同步余额
	 * @param ids
	 * @return
	 */
	@Override
	public List<Map<String, String>> balanceSync(List<Integer> ids) {
		List<AccountVO> accounts=amTradeClient.getAccountByUserIds(ids);
		List<Map<String, String>> results = new ArrayList<>();
		for (AccountVO account : accounts) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("hydId",account.getUserId().toString());
			map.put("balance", account.getBankBalance()==null?"0":account.getBankBalance()+"");
			results.add(map);
		}
		return results;
	}
}
