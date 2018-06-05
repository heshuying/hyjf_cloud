package com.hyjf.am.user.service;

import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.resquest.user.UsersContractRequest;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ServiceException;

/**
 * @author xiasq
 * @version UserService, v0.1 2018/1/21 22:42
 */
public interface UserService {
	/**
	 * 注册
	 * @param userRequest
	 * @return
	 * @throws ServiceException
	 */
	User register(RegisterUserRequest userRequest) throws MQException;

	/**
	 * 获取用户
	 * @param userId
	 * @return
	 */
	User findUserByUserId(int userId);

	/**
	 * 获取用户
	 * @param userId
	 * @return
	 */
	UserInfo findUsersInfo(int userId);

	/**
	 * 生成唯一用户id
	 * 
	 * @param mobile
	 * @return
	 */
	String generateUniqueUsername(String mobile);

	/**
	 * 根据手机号查找用户信息
	 * 
	 * @param mobile
	 * @return
	 */
	User findUserByMobile(String mobile);

	/**
	 * 根据username或者mobiel查询用户
	 * @param condition
	 * @return
	 */
	User findUserByUsernameOrMobile(String condition);

	/**
	 * 根据推荐人手机号或userId查询推荐人
	 * 
	 * @param reffer
	 * @return
	 */
	User findUserByRecommendName(String reffer);

	/**
	 * 组装user信息
	 * 
	 * @param userVO
	 * @return
	 */
	UserVO assembleUserVO(UserVO userVO);

	void updateLoginUser(int userId, String ip);


	HjhUserAuth getHjhUserAuthByUserId(Integer userId);

	void insertSelective(HjhUserAuthLog hjhUserAuthLog);

	HjhUserAuthLog selectByExample(String orderId);

	int updateByPrimaryKeySelective(HjhUserAuthLog record);

	int insertSelective(HjhUserAuth record);

	int updateByPrimaryKeySelective(HjhUserAuth record);

    void updateUserAuthInves(BankRequest bean);

	/**
	 * 修改用户表By主键
	 * @param record
	 * @return int
	 */
	public int updateUserById(User record);

	UserEvalationResult selectUserEvalationResultByUserId(Integer userId);

	AccountChinapnr getAccountChinapnr(Integer userId);

	int updateUserContact(UsersContractRequest record);

	UsersContact selectUserContact(Integer userId);
}
