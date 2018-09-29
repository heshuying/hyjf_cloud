package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.customize.CallcenterConfigCustomizeMapper;
import com.hyjf.am.config.dao.model.customize.CallcenterBankConfigCustomize;
import com.hyjf.am.config.service.CallCenterService;
import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author libin
 * @version CallCenterServiceImpl, v0.1 2018/4/18 20:34
 */
@Service
public class CallCenterServiceImpl implements CallCenterService {
	
	@Autowired
	protected CallcenterConfigCustomizeMapper callcenterConfigCustomizeMapper;
	
	@Override
	public List<CallcenterBankConfigCustomize> getBankConfigList(
			CallcenterAccountHuifuRequest callcenterAccountHuifuRequest) {
		List<CallcenterBankConfigCustomize> list = callcenterConfigCustomizeMapper.getBankConfigList(callcenterAccountHuifuRequest);
		return list;
	}
}
