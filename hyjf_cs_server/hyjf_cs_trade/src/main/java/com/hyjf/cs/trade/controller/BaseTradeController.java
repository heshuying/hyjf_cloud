/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller;

import com.hyjf.common.constants.CommonConstant;
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
        }else if (ClientConstants.APP_CLIENT_IOS == client || ClientConstants.APP_CLIENT == client) {
            return sysConfig.getAppFrontHost();
        }else if (ClientConstants.WECHAT_CLIENT == client) {
            return sysConfig.getWeiFrontHost();
        }
        return null;
    }

    /**
     * 获取温金投前端的地址
     * @param sysConfig
     * @param platform
     * @return
     */
    public String getWjtFrontHost(SystemConfig sysConfig, String platform) {
        Integer client = Integer.parseInt(platform);
        if (ClientConstants.WJT_PC_CLIENT == client) {
            return sysConfig.getWjtFrontHost();
        }else if (ClientConstants.WJT_WEI_CLIENT == client) {
            return sysConfig.getWjtWeiFrontHost();
        }
        return null;
    }

    public String getForgotPwdUrl(String platform, HttpServletRequest request,SystemConfig sysConfig) {
        Integer client = Integer.parseInt(platform);
        if (ClientConstants.WEB_CLIENT == client) {
            String token=request.getHeader("token");
            return sysConfig.getFrontHost()+"/user/setTradePassword";
        }else if (ClientConstants.APP_CLIENT_IOS == client || ClientConstants.APP_CLIENT == client) {
            String sign=request.getParameter("sign");
            return sysConfig.getAppFrontHost()+"/public/formsubmit?sign=" + sign +
                    "&requestType="+CommonConstant.APP_BANK_REQUEST_TYPE_RESET_PASSWORD +
                    "&platform="+request.getParameter("platform");
        }else if (ClientConstants.WECHAT_CLIENT == client) {
            String sign=request.getParameter("sign");
            return sysConfig.getWeiFrontHost()+"/submitForm?queryType=6";
        }
        return "";
    }

    /**
     * 获取温金投前端的地址
     * @param sysConfig
     * @param platform
     * @return
     */
    public String getWjtForgotPwdUrl(String platform, HttpServletRequest request,SystemConfig sysConfig) {
        Integer client = Integer.parseInt(platform);
        if (ClientConstants.WJT_PC_CLIENT == client) {
            return sysConfig.getWjtFrontHost()+"/user/setTradePassword";
        }else if (ClientConstants.WJT_WEI_CLIENT == client) {
            return sysConfig.getWjtWeiFrontHost()+"/submitForm?queryType=6";
        }
        return "";
    }
}
