package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.customize.CallcenterBankConfigCustomize;
import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;

import java.util.List;

/**
 * @author libin
 * @version CallcenterCustomizeMapper, v0.1 2018/4/18 20:34
 */
public interface CallcenterConfigCustomizeMapper {
	List<CallcenterBankConfigCustomize> getBankConfigList(CallcenterAccountHuifuRequest callcenterAccountHuifuRequest);
}
