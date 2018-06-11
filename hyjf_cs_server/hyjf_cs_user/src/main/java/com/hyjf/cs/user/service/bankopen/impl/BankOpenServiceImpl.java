package com.hyjf.cs.user.service.bankopen.impl;

import com.hyjf.am.resquest.user.BankOpenRequest;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.client.AmBankOpenClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiasq
 * @version UserServiceImpl, v0.1 2018/4/11 9:34
 */

@Service
public class BankOpenServiceImpl implements BankOpenService {
	
	private static final Logger logger = LoggerFactory.getLogger(BankOpenServiceImpl.class);

	@Autowired
	private AmUserClient amUserClient;

	@Autowired
	private AmBankOpenClient amBankOpenClient;

	/**
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public UserVO getUsers(Integer userId){
		
		UserVO userVO = amUserClient.findUserById(userId);

		return userVO;
	}

	@Override
	public boolean existUser(String mobile) {
		UserVO userVO = amUserClient.findUserByMobile(mobile);
		return userVO == null ? false : true;
	}

	@Override
	public boolean checkIdNo(String idNo) {
		
		UserInfoVO userInfo = amBankOpenClient.findUserInfoByCardNo(idNo);
		
		if(userInfo != null) {
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public int updateUserAccountLog(int userId, String userName, String mobile, String logOrderId, String clientPc,String name,String idno,String cardNo) {

		BankOpenRequest bankOpenRequest = new BankOpenRequest();
		
		bankOpenRequest.setUserId(userId);
		bankOpenRequest.setUsername(userName);
		bankOpenRequest.setMobile(mobile);
		bankOpenRequest.setOrderId(logOrderId);
		bankOpenRequest.setChannel(clientPc);
		bankOpenRequest.setTrueName(name);
		bankOpenRequest.setIdNo(idno);
		bankOpenRequest.setCardNo(cardNo);
				
		return amBankOpenClient.updateUserAccountLog(bankOpenRequest);
	}
	
}
