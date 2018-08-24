/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.login;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.user.vo.UserParameters;
import com.hyjf.cs.user.service.BaseUserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangqingqing
 * @version LoginService, v0.1 2018/6/11 15:31
 */
public interface LoginService extends BaseUserService {

    /**
     *
     * @param loginUserName
     *            可以是手机号或者用户名
     * @param loginPassword
     * @param ip
     */
    WebViewUserVO login(String loginUserName, String loginPassword, String ip,String channel);

    /**
     * app登录参数检查
     * @param version
     * @param platform
     * @param netStatus
     */
    void checkForApp(String version,String platform,String netStatus);

    /**
     * 清空手机信息
     * @param userId
     * @param sign
     */
    void clearMobileCode(Integer userId, String sign);

    /**
     * 获取各种用户属性
     * @param userId
     * @param platform
     * @param request
     * @return
     */
    UserParameters getUserParameters(Integer userId, String platform, HttpServletRequest request);

    /**
     * 上传用户头像
     * @param userId
     * @param iconUrl
     */
    void updateUserIconImg(Integer userId, String iconUrl);

    /**
     * 根据用户名获取用户信息
     * @param loginUserName
     * @return
     */
    UserVO getUser(String loginUserName);

    /**
     * 第三方用户已授权验证
     * @param bindUniqueId
     * @param bindPlatformId
     * @return
     */
	Integer getUserIdByBind(int bindUniqueId, int bindPlatformId);

    /**
     * 汇盈金服账号已绑定其他用户验证
     * @param userId
     * @param bindPlatformId
     * @return
     */
	String getBindUniqueIdByUserId(int userId, int bindPlatformId);

    /**
     * 授权
     * @param userId
     * @param bindUniqueId
     * @param pid
     * @return
     */
	Boolean bindThirdUser(Integer userId, int bindUniqueId, Integer pid);

	/**
	 * 根据身份证查询User
	 * @auth sunpeikai
	 * @param idCard 身份证号码
	 * @return
	 */
	UserVO getUserByIdCard(String idCard);
}
