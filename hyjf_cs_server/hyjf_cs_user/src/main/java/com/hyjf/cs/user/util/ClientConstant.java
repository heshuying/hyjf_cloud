/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqq
 * @version ClientConstant, v0.1 2018/5/22 10:51
 */
public class ClientConstant {

    /**
     * web端
     */
    public static final int WEB_CLIENT = 0;

    /**
     * 微信端
     */
    public static final int WECHAT_CLIENT = 1;

    /**
     * APP端
     */
    public static final int APP_CLIENT = 2;

    /**
     * 用户授权自动债转
     */
    public static final String CREDIT_AUTO_TYPE = "1";

    /**
     * 用户授权自动投资
     */
    public static final String INVES_AUTO_TYPE = "0";

    /**
     * 自动债转
     */
    public static final String CREDIT_URL_TYPE = "1";

    /**
     * 自动投资
     */
    public static final String INVES_URL_TYPE = "2";

    /** 存管渠道 app */
    public static final String CHANNEL_APP = "000001";

    /** 存管渠道 pc*/
    public static final String CHANNEL_PC = "000002";

    /** 存管渠道 wechat*/
    public static final String CHANNEL_WEI = "000003";

    /** 存管渠道柜面 */
    public static final String CHANNEL_OTHER = "000004";

    /**
     * url header获取
     */
    public static final Map<Integer,String> CLIENT_HEADER_MAP = new HashMap<Integer, String>(){{
        put(WEB_CLIENT,"web");
        put(WECHAT_CLIENT,"wechat");
        put(APP_CLIENT,"app");
    }};
}
