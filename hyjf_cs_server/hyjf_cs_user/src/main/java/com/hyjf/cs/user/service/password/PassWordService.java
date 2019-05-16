/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.password;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.bean.ThirdPartyTransPasswordRequestBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.cs.user.vo.SendSmsVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.Map;

/**
 * @author wangc
 * 密码设置相关
 */
public interface PassWordService extends BaseUserService {

    /**
     * 修改登陆密码
     * @param newPW
     * @return
     */
    boolean updatePassWd(UserVO user, String newPW);

    /**
     * 更新修改密码状态
     * @param userId
     */
    void updateUserIsSetPassword(Integer userId);

    /**
     * 设置交易密码
     * @param user
     * @return
     */
    Map<String,Object> setPassword(UserVO user);

    /**
     * 重置交易密码
     * @param user
     * @return
     */
    Map<String,Object> resetPassword(UserVO user,String wjtClient);

    /**
     * web端检查参数
     * @param userVO
     * @param oldPW
     * @param newPW
     * @param pwSure
     */
    void checkParam(UserVO userVO, String oldPW, String newPW, String pwSure);


    /**
     * 微信发送验证码
     * @param sendSmsVo
     * @return
     */
    JSONObject sendCode(SendSmsVO sendSmsVo);

    /**
     * 微信端检验参数
     * @param newPassword
     * @param oldPassword
     */
    JSONObject weChatCheckParam(UserVO userVO, String newPassword, String oldPassword);

    void backCheck(SendSmsVO sendSmsVo);

    /**
     * api参数检查
     * @param transPasswordRequestBean
     * @param user
     * @param bankOpenAccount
     */
    BankCallBean apiSetPassword(ThirdPartyTransPasswordRequestBean transPasswordRequestBean, String bankDetailUrl, String txCode, UserVO user, BankOpenAccountVO bankOpenAccount);

    /**
     * 检查密码格式
     * @param password
     */
    void checkPassword(String password);

    /**
     * 修改登录密码
     * @param userVO
     * @param newPW
     * @return
     */
    int updatePassword(UserVO userVO, String newPW);

    boolean existPhone(String mobile);

    boolean validPassword(Integer userId, String pw);

    /**
     * app端 检查参数
     * @param key
     * @param version
     * @param netStatus
     * @param platform
     * @param sign
     * @param token
     * @param randomString
     * @param order
     * @param newPassword
     * @param oldPassword
     */
    void appCheckParam(String key,UserVO userVO, String version, String netStatus, String platform, String sign, String token, String randomString, String order, String newPassword,String oldPassword);

    /**
     * app设置交易密码参数检查
     * @param token
     * @param sign
     * @return
     */
    UserVO checkStatus(String token, String sign);

    /**
     * 微信验证短信验证码
     * @param sendSmsVo
     * @param b
     * @return
     */
    JSONObject validateVerificationCoden(SendSmsVO sendSmsVo, boolean b);

    /**
     * 组装参数调用银行
     * @param bean
     * @param user
     * @param usersInfo
     * @param bankOpenAccount
     * @return
     */
    Map<String,Object> setAppPassword(BankCallBean bean, UserVO user, UserInfoVO usersInfo, BankOpenAccountVO bankOpenAccount);

    /**
     * app重置交易密码
     * @param bean
     * @param user
     * @param usersInfo
     * @param bankOpenAccount
     * @return
     */
    Map<String,Object> resetAppPassword(BankCallBean bean, UserVO user, UserInfoVO usersInfo, BankOpenAccountVO bankOpenAccount);

    /**
     * 微信设置交易密码
     * @param bean
     * @param user
     * @param usersInfo
     * @param bankOpenAccount
     * @return
     */
    Map<String,Object> setWeChatPassword(BankCallBean bean, UserVO user, UserInfoVO usersInfo, BankOpenAccountVO bankOpenAccount);

    /**
     * 微信设置交易密码检查参数
     * @param userId
     * @return
     */
    UserVO weChatCheck(Integer userId);

    /**
     * 微信重置交易密码
     * @param bean
     * @param user
     * @param usersInfo
     * @param bankOpenAccount
     * @return
     */
    Map<String,Object> resetWeChatPassword(BankCallBean bean, UserVO user, UserInfoVO usersInfo, BankOpenAccountVO bankOpenAccount);

    /**
     * api端校验参数
     * @param transPasswordRequestBean
     * @param type
     * @return
     */
    Map<String,Object> apiCheack(ThirdPartyTransPasswordRequestBean transPasswordRequestBean, String type,String verifyValue);
}
