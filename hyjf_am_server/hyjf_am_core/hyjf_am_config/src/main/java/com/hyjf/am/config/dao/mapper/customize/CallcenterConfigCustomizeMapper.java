package com.hyjf.am.config.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.config.dao.model.customize.CallcenterBankConfigCustomize;
import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;

/**
 * @author libin
 * @version CallcenterCustomizeMapper, v0.1 2018/4/18 20:34
 */
public interface CallcenterConfigCustomizeMapper {
	List<CallcenterBankConfigCustomize> getBankConfigList(CallcenterAccountHuifuRequest callcenterAccountHuifuRequest);
}
