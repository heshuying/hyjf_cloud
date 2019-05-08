package com.hyjf.cs.trade.exception;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.StringUtil;
import com.hyjf.cs.trade.bean.BaseResultBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
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


	/**
	 * 组合层Exception错误异常处理（共用）
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public BaseResultBean defaultErrorHandler(HttpServletRequest req, Exception e) {
		logger.error("请求路径:[{}]",req.getRequestURI());
		logger.error("system error", e);
		BaseResultBean response = new BaseResultBean();
		response.setStatus(SYSTEM_ERROR);
		response.setStatusDesc(SYSTEM_ERROR_MSG);
		return response;
	}

	@ExceptionHandler(value = ReturnMessageException.class)
	@ResponseBody
	public BaseResultBean defaultReturnErrorHandler(HttpServletRequest req, ReturnMessageException e) {
		logger.error("请求路径:[{}]",req.getRequestURI());
		BaseResultBean response = new BaseResultBean();
		response.setStatus(e.getError().getCode());
		response.setStatusDesc(e.getError().getMsg());
		return response;
	}




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
		return new BaseResult<>(MsgEnum.ERR_PARAM_TYPE.getCode(), StringUtil.getEnumMessage(MsgEnum.ERR_PARAM_TYPE));
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
		return new BaseResult<>(MsgEnum.ERR_JSON.getCode(), StringUtil.getEnumMessage(MsgEnum.ERR_JSON));
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
	public BaseResult<?> CheckExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		CheckException e = (CheckException)ex;
		BaseResult<?> result = new BaseResult<>(e.getData());
		result.setStatusInfo(StringUtils.isNotEmpty(e.getCode()) ? e.getCode() : "404", ex.getLocalizedMessage());
		return result;
	}



}
