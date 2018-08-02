/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.regist;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.user.controller.wechat.regist.UserRegistResultVO;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.cs.user.vo.RegisterRequest;
import com.hyjf.soa.apiweb.CommonParamBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version RegistService, v0.1 2018/6/11 15:09
 */
public interface RegistService extends BaseUserService {

    /**
     * 注册参数检查
     * @param registerRequest
     */
    void checkParam(RegisterRequest registerRequest);

    /**
     * api渠道注册参数校验
     *
     * @param
     */
    void apiCheckParam(RegisterRequest registerRequest);

    JSONObject appCheckParam(RegisterRequest registerRequest);

    /**
     * 通用注册功能实现
     * @param registerRequest
     * @param ip
     * @return
     * @throws ReturnMessageException
     */
    WebViewUserVO register(RegisterRequest registerRequest, String ip)
            throws ReturnMessageException;

    /**
     * api端注册
     * @param registerRequest
     * @param ipAddr
     * @return
     */
    UserVO apiRegister(RegisterRequest registerRequest, String ipAddr);

    /**
     * 融东风注册
     * @param register
     * @param ipAddr
     * @param platform
     * @return
     */
    UserVO surongRegister(RegisterRequest register, String ipAddr, String platform);

    /**
     * 检查活动是否有效
     * @param activityId 活动id
     * @return
     */
    boolean checkActivityIfAvailable(Integer activityId);

    /**
     * 判断推荐人是否存在
     * @param recommend
     * @return  1-存在 0-不存在
     */
    int countUserByRecommendName(String recommend);

    /**
     * app查询banner
     * @param adsRequest
     * @return
     */
    AppAdsCustomizeVO searchBanner(AdsRequest adsRequest);

    /**
     * 微信验证参数
     * @param mobile
     * @param password
     * @param reffer
     * @param verificationCode
     * @return
     */
    UserRegistResultVO wechatCheckParam(String mobile, String password, String reffer, String verificationCode);

    UserVO insertUserActionUtm(String mobile, String password, String verificationCode, String reffer, String loginIp, String platform, String utm_id, String utm_source);
    /**
     * 登录操作
     * @auth sunpeikai
     * @param
     * @return
     */
    int updateLoginInAction(String userName,String password,String ipAddr);
}
