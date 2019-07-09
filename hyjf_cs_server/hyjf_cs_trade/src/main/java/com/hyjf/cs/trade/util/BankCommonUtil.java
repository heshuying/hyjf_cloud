/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.util;

import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.cs.trade.config.SystemConfig;

/**
 * @author sss
 * @version BankCommonUtil, v0.1 2019/5/21 11:54
 */
public class BankCommonUtil {
    /**
     * 获取前端的地址
     * @param sysConfig
     * @param platform
     * @return
     */
    public static String getFrontHost(SystemConfig sysConfig, String platform) {

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

    /**
     * 获取温金投前端的地址
     * @param sysConfig
     * @param platform
     * @return
     */
    public static String getWjtFrontHost(SystemConfig sysConfig, String platform) {
        Integer client = Integer.parseInt(platform);
        if (ClientConstants.WJT_PC_CLIENT == client) {
            return sysConfig.getWjtFrontHost();
        }else if (ClientConstants.WJT_WEI_CLIENT == client) {
            return sysConfig.getWjtWeiFrontHost();
        }
        return null;
    }

    /**
     * 获取忘记密码地址
     * @param platform
     * @param sign
     * @param sysConfig
     * @return
     */
    public static String getForgotPwdUrl(String platform, String sign, SystemConfig sysConfig) {

        Integer client = Integer.parseInt(platform);
        if (ClientConstants.WEB_CLIENT == client) {
            return sysConfig.getFrontHost()+"/user/setTradePassword";
        }
        if (ClientConstants.APP_CLIENT_IOS == client || ClientConstants.APP_CLIENT == client) {
            return sysConfig.getAppFrontHost()+"/public/formsubmit?sign=" + sign +
                    "&requestType="+ CommonConstant.APP_BANK_REQUEST_TYPE_RESET_PASSWORD +
                    "&platform="+platform;
        }
        if (ClientConstants.WECHAT_CLIENT == client) {
            return sysConfig.getWeiFrontHost()+"/submitForm?queryType=6";
        }
        return "";
    }

    /**
     * 获取温金投忘记密码地址
     * @param platform
     * @param sign
     * @param sysConfig
     * @return
     */
    public static String getWjtForgotPwdUrl(String platform, String sign, SystemConfig sysConfig) {

        Integer client = Integer.parseInt(platform);
        if (ClientConstants.WEB_CLIENT == client) {
            return sysConfig.getFrontHost()+"/user/setTradePassword";
        }
        if (ClientConstants.APP_CLIENT_IOS == client || ClientConstants.APP_CLIENT == client) {
            return sysConfig.getAppFrontHost()+"/public/formsubmit?sign=" + sign +
                    "&requestType="+CommonConstant.APP_BANK_REQUEST_TYPE_RESET_PASSWORD +
                    "&platform="+platform;
        }
        if (ClientConstants.WECHAT_CLIENT == client) {
            return sysConfig.getWeiFrontHost()+"/submitForm?queryType=6";
        }
        return "";
    }

}
