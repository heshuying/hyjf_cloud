package com.hyjf.callcenter.service;

import com.hyjf.am.vo.callcenter.CallCenterBankAccountManageVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

/**
 * Description:按照用户名/手机号查询账户余额
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: LIBIN
 * @version: 1.0
 *           Created at: 2017年7月15日 下午1:50:02
 *           Modification History:
 *           Modified by :
 */
public interface SrchBalanceInfoService {
	/**
	 * 银行账户管理 （列表）
	 * 
	 * @param accountManageBean
	 * @return
	 */
	List<CallCenterBankAccountManageVO> queryBankAccountInfos(UserVO user, Integer limitStart, Integer limitSize);
}
