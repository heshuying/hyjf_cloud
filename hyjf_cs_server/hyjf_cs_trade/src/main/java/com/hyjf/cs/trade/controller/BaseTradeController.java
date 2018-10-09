/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller;

import com.hyjf.common.util.ClientConstants;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.config.SystemConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * 组合层Market用Controller基类
 * @author lb
 * @version BaseMarketController, v0.1 2018/6/1:36
 */
public class BaseTradeController extends BaseController {
    /**
     * 获取前端的地址
     * @param sysConfig
     * @param platform
     * @return
     */
    public String getFrontHost(SystemConfig sysConfig, String platform) {

        Integer client = Integer.parseInt(platform);
        if (ClientConstants.WEB_CLIENT == client) {
            return sysConfig.getFrontHost();
        }
        if (ClientConstants.APP_CLIENT_IOS == client || ClientConstants.APP_CLIENT == client) {
            return sysConfig.getAppFrontHost();
        }
        if (ClientConstants.WECHAT_CLIENT == client) {
            return sysConfig.getWeiFrontHost();
        }
        return null;
    }
    public String getForgotPwdUrl(String platform, HttpServletRequest request) {


        Integer client = Integer.parseInt(platform);
        if (ClientConstants.WEB_CLIENT == client) {
            String token=request.getHeader("token");
            return "http://CS-USER/hyjf-web/user/password/resetTeaderPassword?token="+token;
        }
        if (ClientConstants.APP_CLIENT_IOS == client || ClientConstants.APP_CLIENT == client) {
            String sign=request.getParameter("sign");
            return "http://CS-USER/hyjf-app/bank/user/transpassword/resetPassword?sign="+sign;
        }
        if (ClientConstants.WECHAT_CLIENT == client) {
            String sign=request.getParameter("sign");
            return "http://CS-USER/hyjf-wechat/wx/transpassword/resetPassword.page?sign="+sign;
        }
        return "";
    }
}
