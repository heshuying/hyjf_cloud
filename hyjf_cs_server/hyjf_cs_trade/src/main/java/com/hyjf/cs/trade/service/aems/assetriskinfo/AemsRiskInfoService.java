package com.hyjf.cs.trade.service.aems.assetriskinfo;


import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.trade.bean.aems.AemsInfoBean;

import java.util.List;

public interface AemsRiskInfoService extends BaseService {

	/**
	 * 插入资产表
	 *
	 * @param infobeans
	 * @return
	 */
	void insertRiskInfo(List<AemsInfoBean> infobeans);
}
