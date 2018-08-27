package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.SpreadsUser;
import com.hyjf.am.user.dao.model.auto.SpreadsUserExample;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UserInfoExample;
import com.hyjf.am.user.dao.model.customize.EmployeeCustomize;
import com.hyjf.am.user.dao.model.customize.UserCrmInfoCustomize;
import com.hyjf.am.user.dao.model.customize.UserInfoCustomize;
import com.hyjf.am.user.service.front.user.UserInfoService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.AdminMsgPushCommonCustomizeVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author xiasq
 * @version UserInfoServiceImpl, v0.1 2018/4/23 9:56
 */

@Service
public class UserInfoServiceImpl extends BaseServiceImpl implements UserInfoService {

	@Override
	public UserInfo findUserInfoById(int userId) {
		UserInfoExample UserInfoExample = new UserInfoExample();
		UserInfoExample.createCriteria().andUserIdEqualTo(userId);
		List<UserInfo> usersList = userInfoMapper.selectByExample(UserInfoExample);
		if (!CollectionUtils.isEmpty(usersList)) {
			return usersList.get(0);
		}
		return null;
	}

	/**
	 * @param idNo
	 * @Description 根据身份证号查询用户信息
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/6 11:43
	 */
	@Override
	public UserInfo findUserInfoByIdNo(String idNo) {
		UserInfoExample UserInfoExample = new UserInfoExample();
		UserInfoExample.createCriteria().andIdcardEqualTo(idNo);
		List<UserInfo> usersList = userInfoMapper.selectByExample(UserInfoExample);
		if (!CollectionUtils.isEmpty(usersList)) {
			return usersList.get(0);
		}
		return null;
	}

	@Override
	public UserInfo selectUserInfoByNameAndCard(String trueName, String idCrad) {
		UserInfoExample example = new UserInfoExample();
		UserInfoExample.Criteria crt = example.createCriteria();
		crt.andTruenameEqualTo(trueName);
		crt.andIdcardEqualTo(idCrad);
		List<UserInfo> list = userInfoMapper.selectByExample(example);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 根据用户ID查询crm信息
	 * @param userId
	 * @return
	 */
	@Override
	public UserCrmInfoCustomize findUserCrmInfoByUserId(Integer userId) {
		List<UserCrmInfoCustomize> list = userCrmInfoCustomizeMapper.findUserCrmInfoByUserId(userId);
		return null;
	}

	/**
	 * 获取用户详情
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfoCustomize queryUserInfoCustomizeByUserId(Integer userId) {
		return userInfoCustomizeMapper.queryUserInfoByUserId(userId);
	}

	/**
	 * 根据用户名查询用户详情
	 * @auth sunpeikai
	 * @param userName 用户名
	 * @return
	 */
	@Override
	public UserInfoCustomize queryUserInfoCustomizeByUserName(String userName) {
		return userInfoCustomizeMapper.queryUserInfoByEmployeeName(userName);
	}

	@Override
	public List<SpreadsUser> querySpreadsUsersByUserId(Integer userId) {
		SpreadsUserExample example = new SpreadsUserExample();
		example.createCriteria().andUserIdEqualTo(userId);
		return spreadsUserMapper.selectByExample(example);
	}

	/**
	 * 获取员工信息
	 * @param userId
	 * @return
	 */
	@Override
	public EmployeeCustomize selectEmployeeByUserId(Integer userId) {
		return employeeCustomizeMapper.selectEmployeeByUserId(userId);
	}

	/**
	 * 通过用户id获得用户真实姓名和身份证号
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfo selectUserInfoByUserId(Integer userId) {
		UserInfoExample example = new UserInfoExample();
		UserInfoExample.Criteria crt = example.createCriteria();
		crt.andUserIdEqualTo(userId);
		List<UserInfo> list = userInfoMapper.selectByExample(example);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 获取部门信息
	 * @param userId
	 * @return
	 */
	@Override
	public List<UserInfoCustomize> queryDepartmentInfoByUserId(Integer userId){
		return userInfoCustomizeMapper.queryDepartmentInfoByUserId(userId);
	}

	/**
	 * 通过手机号获取设备标识码
	 *
	 * @param mobile
	 * @return
	 */
	@Override
	public AdminMsgPushCommonCustomizeVO getMobileCodeByNumber(String mobile) {
		return userInfoMapper.getMobileCodeByNumber(mobile);
	}

}
