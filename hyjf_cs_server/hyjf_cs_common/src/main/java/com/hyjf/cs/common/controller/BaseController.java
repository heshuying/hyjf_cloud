/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.common.controller;

import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.BaseResult;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 组合层共用Controller基类
 * @author liubin
 * @version BaseController, v0.1 2018/6/15 19:18
 */
public class BaseController {

    /**
     * 传入参数类型错误异常处理
     * @param request
     * @param response
     * @param ex
     * @return
     * @author liubin
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public BaseResult<?> bindExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return new BaseResult<>(BaseResult.FAIL, CheckUtil.getErrorMessage("param.error"));
    }

    /**
     * 传入JSON错误异常处理
     * @param request
     * @param response
     * @param ex
     * @return
     * @author liubin
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public BaseResult<?> httpMessageNotReadableExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return new BaseResult<>(BaseResult.FAIL, CheckUtil.getErrorMessage("json.error"));
    }


}
