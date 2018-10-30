package com.hyjf.admin.exception;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.ReturnMessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		logger.error("请求路径:[{}]",req.getRequestURI());
		logger.error("system error", e);
		BaseResult response = new BaseResult();
		response.setStatus(SYSTEM_ERROR);
		response.setStatusDesc( SYSTEM_ERROR_MSG);
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
	public com.hyjf.admin.common.result.BaseResult<?> CheckExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		CheckException e = (CheckException)ex;
		com.hyjf.admin.common.result.BaseResult<?> result = new com.hyjf.admin.common.result.BaseResult<>(e.getData());
		result.setStatusInfo(e.getCode(), ex.getLocalizedMessage());
		return result;
	}

}
