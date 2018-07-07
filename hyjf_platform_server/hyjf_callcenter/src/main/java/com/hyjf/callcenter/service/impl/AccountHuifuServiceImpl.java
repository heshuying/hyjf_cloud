package com.hyjf.callcenter.service.impl;

import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.vo.callcenter.CallcenterAccountHuifuVO;
import com.hyjf.am.vo.callcenter.CallcenterBankConfigVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.client.AmConfigClient;
import com.hyjf.callcenter.client.AmUserClient;
import com.hyjf.callcenter.service.AccountHuifuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author libin
 * @version AccountHuifuServiceImpl, v0.1 2018/6/6 13:38
 */
@Service
public class AccountHuifuServiceImpl implements AccountHuifuService {
	
	@Autowired
	private AmUserClient amUserClient;

	@Autowired
	private AmConfigClient amConfigClient;
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
		list = amUserClient.selectBankCardList(callcenterAccountHuifuRequest);
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
		List<CallcenterBankConfigVO> list = amConfigClient.getBankConfigList(callcenterAccountHuifuRequest);;
		return list;
	}
}
