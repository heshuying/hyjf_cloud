package com.hyjf.cs.user;

import javax.servlet.http.HttpServletRequest;

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
	public BaseResult defaultErrorHandler(HttpServletRequest req, Exception e) {
		logger.error("system error", e);
		BaseResult response = new BaseResult();
		response.setStatus(SYSTEM_ERROR);
		response.setStatusDesc(e.getMessage() == null ? SYSTEM_ERROR_MSG : e.getMessage());
		return response;
	}

	@ExceptionHandler(value = ReturnMessageException.class)
	@ResponseBody
	public BaseResult defaultReturnErrorHandler(HttpServletRequest req, ReturnMessageException e) {
		BaseResult response = new BaseResult();
		response.setStatus(e.getError().getCode());
		response.setStatusDesc(e.getError().getMsg());
		return response;
	}

}
