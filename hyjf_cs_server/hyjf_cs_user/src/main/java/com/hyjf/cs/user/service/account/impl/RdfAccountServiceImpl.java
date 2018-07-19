package com.hyjf.cs.user.service.account.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.account.RdfAccountService;

@Service
public class RdfAccountServiceImpl extends BaseUserServiceImpl implements RdfAccountService{

	@Autowired
	private AmUserClient amUserClient;
	
	private AmTradeClient amTradeClient;
	
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
