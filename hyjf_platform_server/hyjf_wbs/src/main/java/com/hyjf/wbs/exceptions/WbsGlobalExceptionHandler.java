package com.hyjf.wbs.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.ReturnMessageException;

/**
 * @author cuigq
 * @version WbsGlobalExceptionHandler,  2018/1/21 22:15
 */

@ControllerAdvice
public class WbsGlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(WbsGlobalExceptionHandler.class);
	private static final String SYSTEM_ERROR = "99";
	private static final String SYSTEM_ERROR_MSG = "系统异常";

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public BaseResult defaultErrorHandler(HttpServletRequest req, Exception e) {
		logger.error("请求路径:[{}]",req.getRequestURI());
		logger.error("system error", e);
		BaseResult response = new BaseResult();
		response.setStatus(SYSTEM_ERROR);
		response.setStatusDesc(SYSTEM_ERROR_MSG);
		return response;
	}

	@ExceptionHandler(value = ReturnMessageException.class)
	@ResponseBody
	public BaseResult defaultReturnErrorHandler(HttpServletRequest req, ReturnMessageException e) {
		logger.error("请求路径:[{}]",req.getRequestURI());
		BaseResult response = new BaseResult();
		response.setStatus(e.getError().getCode());
		response.setStatusDesc(e.getError().getMsg());
		return response;
	}

	@ExceptionHandler(CheckException.class)
	@ResponseBody
	public BaseResult<?> CheckExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		CheckException e = (CheckException)ex;
		BaseResult<?> result = new BaseResult<>(e.getData());
		result.setStatusInfo(e.getCode(), ex.getLocalizedMessage());
		logger.warn("catch chech exception, result is: {}", result);
		return result;
	}

}
