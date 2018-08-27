package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.dao.model.auto.SpreadsUser;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.customize.EmployeeCustomize;
import com.hyjf.am.user.dao.model.customize.UserCrmInfoCustomize;
import com.hyjf.am.user.dao.model.customize.UserInfoCustomize;
import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.admin.AdminMsgPushCommonCustomizeVO;

import java.util.List;

/**
 * @author xiasq
 * @version UserInfoService, v0.1 2018/4/23 9:56
 */
public interface UserInfoService extends BaseService{
    UserInfo findUserInfoById(int userId);

    /**
     * @Description 根据身份证号查询用户信息
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/6 11:43
     */
    UserInfo findUserInfoByIdNo(String idNo);

    /**
     * 获取用户信息
     * @param truename
     * @param idcard
     * @return
     */
    UserInfo selectUserInfoByNameAndCard(String truename, String idcard);

    /**
     * 根据用户ID查询crm信息
     * @param userId
     * @return
     */
    UserCrmInfoCustomize findUserCrmInfoByUserId(Integer userId);

    /**
     * 查询用户详情
     * @param userId
     * @return
     */
	UserInfoCustomize queryUserInfoCustomizeByUserId(Integer userId);

	/**
	 * 根据用户名查询用户详情
	 * @auth sunpeikai
	 * @param userName 用户名
	 * @return
	 */
	UserInfoCustomize queryUserInfoCustomizeByUserName(String userName);

    /**
     *
     * @param userId
     * @return
     */
    List<SpreadsUser> querySpreadsUsersByUserId(Integer userId);

    /**
     * 获取员工信息
     * @param userId
     * @return
     */
    EmployeeCustomize selectEmployeeByUserId(Integer userId);

    /**
     * 通过用户id获得用户真实姓名和身份证号
     *
     * @param userId
     * @return
     */
    UserInfo selectUserInfoByUserId(Integer userId);

    /**
     * 获取部门信息
     * @param userId
     * @return
     */
    List<UserInfoCustomize> queryDepartmentInfoByUserId(Integer userId);

    /**
     * 通过手机号获取设备标识码
     *
     * @param mobile
     * @return
     */
    AdminMsgPushCommonCustomizeVO getMobileCodeByNumber(String mobile);
}
