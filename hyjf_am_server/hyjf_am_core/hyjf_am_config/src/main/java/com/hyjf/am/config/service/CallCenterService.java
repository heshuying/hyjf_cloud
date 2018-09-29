package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.customize.CallcenterBankConfigCustomize;
import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;

import java.util.List;

/**
 * @author libin
 * @version CallCenterService, v0.1 2018/4/18 20:34
 */
public interface CallCenterService {
	
	/**
	 * 单表查询bankconfig库
	 * @return List<CallcenterAccountHuifuVO>
	 * @author libin
	 */
	public List<CallcenterBankConfigCustomize> getBankConfigList(CallcenterAccountHuifuRequest callcenterAccountHuifuRequest);
}
