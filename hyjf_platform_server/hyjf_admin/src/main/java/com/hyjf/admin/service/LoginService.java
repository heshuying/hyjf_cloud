package com.hyjf.admin.service;

import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.resquest.config.AdminSystemRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.TreeVO;

import java.util.List;


/**
 * @author dongzeshan
 * @version LoginService, v0.1 2018/6/25 14:47
 */
public interface LoginService {

    /**
     * 查询用户权限
     *
     * @return
     */
	public List<AdminSystemVO> getUserPermission(String userName);

    /**
     * 查询用户信息
     *
     * @return
     */
	public AdminSystemResponse getUserInfo(AdminSystemRequest adminSystemRequest);
    /**
     * 查询菜单权限
     *
     * @return
     */
	public List<TreeVO> selectLeftMenuTree2(String id);

	public AdminSystemResponse updatePasswordAction(AdminSystemRequest map);

    /**
     * 根据手机号查询用户
     * @param adminSystemRequest
     * @return
     */
    AdminSystemResponse getUserInfoByMobile(AdminSystemRequest adminSystemRequest);

    /**
     * 检查是否能够发送短信
     * @param verificationType
     * @param mobile
     * @param id
     * @param ipAddr
     * @return
     */
    String adminSendSmsCodeCheckParam(String verificationType, String mobile, String id, String ipAddr);

    /**
     * 发送短信
     * @param verificationType
     * @param mobile
     * @param ipAddr
     */
    void sendSmsCode(String verificationType, String mobile,String ipAddr);

    /**
     * 检查短信验证码

     * @return
     */
    int updateCheckMobileCode(String mobile, String verificationCode, String verificationType, String platform,
                                     Integer searchStatus, Integer updateStatus,boolean isUpdate) ;
}
