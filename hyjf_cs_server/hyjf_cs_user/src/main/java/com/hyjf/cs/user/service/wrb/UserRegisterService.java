package com.hyjf.cs.user.service.wrb;

import com.hyjf.am.response.config.MessagePushTemplateResponse;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lisheng
 * @version UserRegisterService, v0.1 2018/9/19 15:44
 */

public interface UserRegisterService {

    /**
     *根据机构编号检索机构信息
     * @param instcode
     * @return
     */
    HjhInstConfigVO selectHjhInstConfig(String instcode);

    /**
     * 获取渠道信息
     * @param utmId
     * @return
     */
    UtmPlatVO selectUtmPlatByUtmId(String utmId);


    /**
     * 统计手机号
     * @param mobile
     * @return
     */
    UserVO findUserByMobile(String mobile);

    /**
     * 查询绑定用户
     * @param userId
     * @return
     */
    Integer selectByUserId(Integer userId, String instCode);

    /**
     * 根据用户ID检索用户信息
     *
     * @param userId
     * @return
     */
    UserVO checkUserByUserId(Integer userId);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    UserInfoVO getUserInfoByUserId(Integer userId);

    /**
     * 给第三方平台用户登陆授权
     * @param userId
     * @param bindUniqueId
     * @param pid
     * @return
     */
    boolean bindThirdUser(Integer userId, int bindUniqueId, Integer pid);

    /**
     *更新用户信息表
     * @param userInfoVO
     * @return
     */
    Integer updateUserInfoByUserInfo(UserInfoVO userInfoVO);

    /**
     * 根据手机号密码注册用户
     *
     * @param mobile
     * @param instCode
     * @param request
     * @param instType
     * @param utmPlat
     * @param platform
     * @return
     */
    Integer insertUserAction(String mobile, String instCode, HttpServletRequest request, Integer instType, UtmPlatVO utmPlat, String platform);

}
