package com.hyjf.am.user;

import javax.servlet.http.HttpServletRequest;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.BaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyjf.common.exception.ReturnMessageException;

/**
 * @author xiasq
 * @version GlobalExceptionHandler, v0.1 2018/1/21 22:15
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String SYSTEM_ERROR = "99";
    private static final String SYSTEM_ERROR_MSG = "系统异常";

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response<BaseVO> defaultErrorHandler(HttpServletRequest req, Exception e) {
        logger.error("system error", e);
        Response<BaseVO> response = new Response<BaseVO>();
        response.setRtn(SYSTEM_ERROR);
        response.setMessage(SYSTEM_ERROR_MSG);
        return response;
    }

    @ExceptionHandler(value = ReturnMessageException.class)
    @ResponseBody
    public Response<BaseVO> defaultReturnErrorHandler(HttpServletRequest req, ReturnMessageException e) {
        Response<BaseVO> response = new Response<BaseVO>();
        response.setRtn(e.getError().getErrCode());
        response.setMessage(e.getError().getMessage());
        return response;
    }

}
