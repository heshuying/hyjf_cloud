/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;

/**
 * 组合层Market用Controller基类
 * @author lb
 * @version BaseMarketController, v0.1 2018/6/1:36
 */
public class BaseUserController extends BaseController {
    /**
     * 组成返回信息
     *
     * @param message
     * @param status
     * @return
     */
    public JSONObject jsonMessage(String message, String status) {
        JSONObject jo = null;
        if (Validator.isNotNull(message)) {
            jo = new JSONObject();
            jo.put(CustomConstants.APP_STATUS_DESC, message);
            jo.put(CustomConstants.APP_STATUS, status);
        }
        return jo;
    }
}
