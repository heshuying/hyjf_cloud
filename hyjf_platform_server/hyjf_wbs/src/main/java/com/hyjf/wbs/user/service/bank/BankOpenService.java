package com.hyjf.wbs.user.service.bank;

import com.hyjf.wbs.user.dao.model.auto.UserInfo;
import com.hyjf.wbs.user.service.BaseService;

public interface BankOpenService extends BaseService {
	
	/**
	 * 保存用户的初始开户记录
	 * @param userId
	 * @param userName
	 * @param
	 * @param clientPc
	 * @param
	 * @return
	 */
	boolean updateUserAccountLog(int userId, String userName, String mobile, String logOrderId, String clientPc, String name, String idno, String cardNo);


	UserInfo findUserInfoByCradId(String cardNo);

}
