/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.register;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.am.vo.wbs.WbsRegisterMqVO;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.user.bean.UserRegisterRequestBean;
import com.hyjf.cs.user.result.UserRegistResult;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.cs.user.vo.RegisterRequest;

/**
 * @author zhangqingqing
 * @version RegistService, v0.1 2018/6/11 15:09
 */
public interface RegisterService extends BaseUserService {

    /**
     * 注册参数检查
     *
     * @param registerRequest
     */
    void checkParam(RegisterRequest registerRequest);

    /**
     * api渠道注册参数校验
     *
     * @param
     * @param registerRequest
     */
    void apiCheckParam(RegisterRequest registerRequest);

    /**
     * app参数检查
     *
     * @param registerRequest
     * @return
     */
    JSONObject appCheckParam(RegisterRequest registerRequest);

    /**
     * 通用注册功能实现
     * @param mobile
     * @param verificationCode
     * @param password
     * @param reffer
     * @param instCode
     * @param utmId
     * @param platform
     * @param ip
     * @param userType 0:普通用户;1:企业用户;
     * @return
     * @throws ReturnMessageException
     */
    WebViewUserVO register(String mobile, String verificationCode, String password, String reffer, String instCode, String utmId, String platform, String ip, Integer userType,String isWjt)
            throws ReturnMessageException;

    /**
     * api端注册
     *
     * @param userRegisterRequestBean
     * @param registerRequest
     * @param ipAddr
     * @return
     */
    UserVO apiRegister(UserRegisterRequestBean userRegisterRequestBean, RegisterRequest registerRequest, String ipAddr);

    /**
     * 融东风注册
     *
     * @param register
     * @param ipAddr
     * @param platform
     * @return
     */
    UserVO surongRegister(RegisterRequest register, String ipAddr, String platform);

    /**
     * 检查活动是否有效
     *
     * @param activityId 活动id
     * @return
     */
    boolean checkActivityIfAvailable(Integer activityId);

    /**
     * 判断推荐人是否存在
     *
     * @param recommend
     * @return 1-存在 0-不存在
     */
    int countUserByRecommendName(String recommend);

    /**
     * app查询banner
     *
     * @param adsRequest
     * @return
     */
    AppAdsCustomizeVO searchBanner(AdsRequest adsRequest);

    /**
     * 微信验证参数
     *
     * @param mobile
     * @param password
     * @param reffer
     * @param verificationCode
     * @return
     */
    UserRegistResult wechatCheckParam(String mobile, String password, String reffer, String verificationCode);

    void checkReffer(String reffer);

    /**
     * 登录操作
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    int updateLoginInAction(String userName, String password, String ipAddr);

    /**
     * 获取账户id
     *
     * @param userId
     * @return
     */
    String getAccountId(Integer userId);

    /**
     * 获取授权状态
     *
     * @param userId
     * @return
     */
    String getAutoInvesStatus(Integer userId);

    /**
     * 发送mq同步app推广渠道表
     * @param version
     * @param webViewUserVO
     */
    void sendMqToSaveAppChannel(String version, WebViewUserVO webViewUserVO,RegisterRequest register);

    /**
     * 注册成功后,发送神策统计MQ
     *
     * @param sensorsDataBean
     * @throws MQException
     */
    void sendSensorsDataMQ(SensorsDataBean sensorsDataBean) throws MQException;

    /**
     * 注册成功后，发送Wbs MQ
     * @param wbsRegisterMqVO
     * @throws MQException
     */
    void sendWbsMQ(WbsRegisterMqVO wbsRegisterMqVO) throws MQException;
}
