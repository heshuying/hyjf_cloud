/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.admin.HjhCreditTenderResponse;
import com.hyjf.am.resquest.admin.HjhCreditTenderRequest;

/**
 * @author libin
 * @version HjhCreditTenderClient.java, v0.1 2018年7月11日 下午3:04:13
 */
public interface HjhCreditTenderClient {
	/**
	 * 获取详细列表
	 * 
	 * @param DebtCreditCustomize
	 * @return
	 */
	HjhCreditTenderResponse getHjhCreditTenderListByParam(HjhCreditTenderRequest form);

}
