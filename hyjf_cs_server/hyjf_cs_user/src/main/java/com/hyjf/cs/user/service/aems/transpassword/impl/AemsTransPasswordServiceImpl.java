package com.hyjf.cs.user.service.aems.transpassword.impl;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AemsTransPasswordServiceImpl extends BaseUserServiceImpl implements AemsTransPasswordService {



	/**
	 * 更新是否设置交易密码标识位
	 * @param userId
	 * @return
	 * @author Michael
	 */
		
	@Override
	public void updateUserIsSetPassword(int userId) {
		UserVO iuser = new UserVO();
		iuser.setUserId(userId);
		iuser.setIsSetPassword(1);
		amUserClient.updateUserById(iuser);
			
	}
}
