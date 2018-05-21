package com.hyjf.cs.user.client;

import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.vo.borrow.BankReturnCodeConfigVO;
import com.hyjf.am.vo.user.HjhUserAuthLogVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

/**
 * @author xiasq
 * @version AmUserClient, v0.1 2018/4/19 12:44
 */
public interface AmUserClient {

	UserVO findUserByMobile(String mobile);

	int countUserByRecommendName(String reffer);

	UserVO register(RegisterUserRequest request);

	UserVO findUserById(int userId);

	UserInfoVO findUserInfoById(int userId);

	int saveSmsCode(String mobile, String checkCode, String validCodeType, Integer status, String platform);

	int checkMobileCode(String mobile, String verificationCode, String verificationType, String platform,
						Integer searchStatus, Integer updateStatus);

    UserVO findUserByUserNameOrMobile(String loginUserName);

	void updateLoginUser(int userId, String ip);

	HjhUserAuthVO getHjhUserAuthByUserId(Integer userId);

	void insertUserAuthLog(HjhUserAuthLogVO hjhUserAuthLog);

    BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);

	HjhUserAuthLogVO selectByExample(String orderId);

	int updateByPrimaryKeySelective(HjhUserAuthLogVO record);

	int insertSelective(HjhUserAuthVO record);

	int updateByPrimaryKeySelective(HjhUserAuthVO record);
}
