package com.hyjf.cs.user.service.bankopen;

import com.hyjf.am.vo.user.UserVO;

/**
 * 
 * @author Administrator
 *
 */

public interface BankOpenService {

	boolean existUser(String mobile);

	UserVO getUsers(Integer userId);

	boolean checkIdNo(String idNo);

	int updateUserAccountLog(int userId, String userName, String mobile, String logOrderId, String clientPc, String name,
			String idno, String cardNo);

}
