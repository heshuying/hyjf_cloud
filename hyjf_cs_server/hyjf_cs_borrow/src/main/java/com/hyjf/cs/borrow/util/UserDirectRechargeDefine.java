package com.hyjf.cs.borrow.util;

import com.hyjf.cs.borrow.controller.RechargeController;

public class UserDirectRechargeDefine {
    
    /** 当前controller名称 */
    public static final String THIS_CLASS = RechargeController.class.getName();

    /** @RequestMapping值 */
    public static final String REQUEST_MAPPING = "/bank/user/directrecharge";
    /** 跳转到缴费页面 */
    public static final String USER_DIRECT_RECHARGE_PAGE_ACTION = "/page";
    /** 用户授权自动投资成功页面*/
    public static final String DIRECTRE_CHARGE_SUCCESS_PATH = "/bank/user/recharge/recharge_success";
    /** 用户授权自动投资失败页面*/
    public static final String DIRECTRE_CHARGE_ERROR_PATH = "/bank/user/recharge/recharge_error";
;
    
    /** 同步回调  */
    public static final String RETURL_SYN_ACTION = "/return";
    /** 异步回调  */
    public static final String RETURL_ASY_ACTION = "/bgreturn";
    
    

}
