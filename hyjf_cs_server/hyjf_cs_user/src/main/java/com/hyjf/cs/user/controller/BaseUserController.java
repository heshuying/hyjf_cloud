/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller;

import com.hyjf.common.exception.CheckException;
import com.hyjf.cs.common.bean.result.BaseResult;
import com.hyjf.cs.common.controller.BaseController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 组合层Market用Controller基类
 * @author lb
 * @version BaseMarketController, v0.1 2018/6/1:36
 */
public class BaseUserController extends BaseController {
    /**
     * 传入信息验证错误异常处理
     * @param request
     * @param response
     * @param ex
     * @return
     * @author liubin
     */
    @ExceptionHandler(CheckException.class)
    @ResponseBody
    public BaseResult<?> CheckExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        CheckException e = (CheckException)ex;
        BaseResult<?> result = new BaseResult<>(e.getData());
        // 设置接口结果页信息
        result = new BaseResult<>(e.getData());
        result.setStatusInfo(BaseResult.FAIL, ex.getLocalizedMessage());
        return result;
    }
}
