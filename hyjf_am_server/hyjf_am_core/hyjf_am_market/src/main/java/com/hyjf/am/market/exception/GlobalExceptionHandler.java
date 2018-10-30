package com.hyjf.am.market.exception;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.BaseVO;
import com.hyjf.common.exception.ReturnMessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiasq
 * @version GlobalExceptionHandler, v0.1 2018/1/21 22:15
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response<BaseVO> defaultErrorHandler(HttpServletRequest req, Exception e) {
        logger.error("请求路径:[{}]",req.getRequestURI());
        logger.error("system error", e);
        Response<BaseVO> response = new Response<BaseVO>();
        response.setRtn(Response.ERROR);
        response.setMessage(e.getMessage() == null ? Response.ERROR_MSG : e.getMessage());
        return response;
    }

    @ExceptionHandler(value = ReturnMessageException.class)
    @ResponseBody
    public Response<BaseVO> defaultReturnErrorHandler(HttpServletRequest req, ReturnMessageException e) {
        logger.error("请求路径:[{}]",req.getRequestURI());
        Response<BaseVO> response = new Response<BaseVO>();
        response.setRtn(e.getError().getCode());
        response.setMessage(e.getError().getMsg());
        return response;
    }

}
