/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.withdraw;

import com.hyjf.cs.user.bean.BaseDefine;

/**
 * @author: sunpeikai
 * @version: UserWithdrawDefine, v0.1 2018/7/23 15:16
 */
public class UserWithdrawDefine extends BaseDefine {
    /** @RequestMapping值 */
    public static final String REQUEST_MAPPING = "hyjf-app/user/withdraw";
    /** 获取我的银行卡  */
    public static final String GET_BANKCARD_MAPPING = "/getBankCardAction";
    /** @RequestMapping值 */
    public static final String GET_BANKCARD_REQUEST = REQUEST_HOME + REQUEST_MAPPING + GET_BANKCARD_MAPPING;
}
