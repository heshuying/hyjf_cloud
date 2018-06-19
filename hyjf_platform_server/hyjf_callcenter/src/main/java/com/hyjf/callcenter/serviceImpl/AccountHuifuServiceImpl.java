package com.hyjf.callcenter.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.vo.callcenter.CallcenterAccountHuifuVO;
import com.hyjf.am.vo.callcenter.CallcenterBankConfigVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.client.AccountHuifuClient;
import com.hyjf.callcenter.service.AccountHuifuService;

/**
 * @author libin
 * @version AccountHuifuServiceImpl, v0.1 2018/6/6 13:38
 */
@Service
public class AccountHuifuServiceImpl implements AccountHuifuService {
	
	@Autowired
	private AccountHuifuClient accountHuifuClient;
	/**
	 * 按照用户名/手机号查询汇付绑卡关系
	 * @param user
	 * @return List<CallcenterAccountHuifuVO>
	 * @author libin
	 */
	@Override
	public List<CallcenterAccountHuifuVO> getRecordList(UserVO user, Integer limitStart, Integer limitEnd) {
		CallcenterAccountHuifuRequest callcenterAccountHuifuRequest = new CallcenterAccountHuifuRequest();
		callcenterAccountHuifuRequest.setLimitStart(limitStart);
		callcenterAccountHuifuRequest.setLimitSize(limitEnd);
		callcenterAccountHuifuRequest.setUserName(user.getUsername());
		List<CallcenterAccountHuifuVO> list = null;
		list = accountHuifuClient.selectBankCardList(callcenterAccountHuifuRequest);
		return list;
	}
	
	/**
	 * 查询BankConfig表
	 * @param user
	 * @return List<CallcenterAccountHuifuVO>
	 * @author libin
	 */
	@Override
	public List<CallcenterBankConfigVO> getBankConfigList() {
		CallcenterAccountHuifuRequest callcenterAccountHuifuRequest = new CallcenterAccountHuifuRequest();
		List<CallcenterBankConfigVO> list = accountHuifuClient.getBankConfigList(callcenterAccountHuifuRequest);;
		return list;
	}
}
