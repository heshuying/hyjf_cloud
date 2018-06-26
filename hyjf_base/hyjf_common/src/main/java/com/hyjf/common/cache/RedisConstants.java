/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.common.cache;

/**
 * redis专用常量
 * @author fp
 * @version RedisConstants, v0.1 2018/3/27 15:43
 */
public class RedisConstants {
    /**
     * 记录密码错误次数Redis前缀web端
     */
    public static final String PASSWORD_ERR_COUNT_APP = "password_err_count_app_";

    /**
     * 记录密码错误次数Redis前缀web端
     */
    public static final String PASSWORD_ERR_COUNT_WEB = "password_err_count_web_";

    /**
     * 用户一秒内的登录次数(ip)
     */
    public static final String LOGIN_ONE_COUNT_WEB = "login_one_count_web_";

    /**
     * 用户一秒内的登录次数(ip)
     */
    public static final String LOGIN_ONE_COUNT_APP = "login_one_count_app_";

    /**
     * 恶意攻击登录ip黑名单
     */
    public static final String LOGIN_BLACK_LIST_WEB = "login_black_list_web";

    /**
     * 恶意攻击登录ip黑名单
     */
    public static final String LOGIN_BLACK_LIST_APP = "login_black_list_app";


    /**
     * 风险保证金前缀
     */
    public static final String CAPITAL_TOPLIMIT_ = "CAPITAL_TOPLIMIT_";

    /**
     * 汇计划发标redis key
     */
    public static final String GEN_HJH_BORROW_NID = "gen_hjh_borrow_nid";

    /**
     * 加入计划防重校验
     */
    public static final String HJH_TENDER_REPEAT = "HJH_TENDER_REPEAT";

    /**
     * 汇计划可投余额前缀
     */
    public static final String HJH_PLAN = "HJHBAL_";

    /**
     * 汇计划进入锁定期处理中队列
     */
    public static final String HJH_LOCK_REPEAT = "hjh_lockisrepeat";
}
