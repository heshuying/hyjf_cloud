/**
web * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: lb
 * @version: 1.0
 * Created at: 2017年9月11日 下午5:16:43
 * Modification History:
 * Modified by :
 */

package com.hyjf.cs.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 日志用AOP
 * @author liubin
 */
@Aspect
//@Order(1)
@Component
public class AOPHandler {

	private	static final Logger logger = LoggerFactory.getLogger(AOPHandler.class);

//	@Pointcut("execution(public * com.hyjf.cs..*Controller.*(..)")
//	public void controllerLog(){}


	/**
	 * 组合层对外接口通用AOP处理
	 * @param pjp
	 * @return
	 */
//	@Pointcut("controllerLog()")
//	@Around("(execution(public com.hyjf.am.bean.result.BaseResult+ com.hyjf.cs..*Controller.*(..))) && !(@annotation(org.springframework.web.bind.annotation.ExceptionHandler))")
//	public Object controllerAOPHandler(ProceedingJoinPoint pjp) {
//
//		long startTime = System.currentTimeMillis();
//		Object result;
//		try {
//			result = pjp.proceed();
//			logger.info(pjp.getSignature() + " 执行时间:" + (System.currentTimeMillis() - startTime));
//		}catch(Throwable e) {
//			logger.error("组合层对外接口通用AOP处理异常！", e);
//			return null;
//		}
//		return result;
//	}
//
//	/**
//	 * api用错误处理
//	 * @param pjp
//	 * @param e
//	 * @return
//	 */
//	private BaseResult<?> apiExceptionHandler(ProceedingJoinPoint pjp, Throwable e) {
//
//		BaseResult<?> result = new BaseResult<>();
//
//		// 已知异常
//		if (e instanceof CheckException) {
//			result.setStatus(BaseResult.FAIL);
//			result.setStatusDesc(e.getLocalizedMessage());
//		} else {
//			logger.error(pjp.getSignature() + " 发生异常\r\n" + AOPUtil.getMethodArgs(pjp), e);
//			result.setStatus(BaseResult.ERROR);
//			result.setStatusDesc("接口调用发生异常，请联系服务方。");
//			//  发送邮件或者写到异常文件中
//		}
//		return result;
//	}
//
//	/**
//	 * api用日志处理（返回值加签）
//	 * @param pjp
//	 * @return
//	 */
//
//	@Pointcut("execution(public com.hyjf.cs.common.bean.result.ApiResult *(..))")
//	public Object apiResSignLogAOPHandler(ProceedingJoinPoint pjp) {
//
//		long startTime = System.currentTimeMillis();
//		ApiResult<?> result;
//		try {
//			result = (ApiResult<?>) pjp.proceed();
//			logger.info(pjp.getSignature() + " 执行时间:" + (System.currentTimeMillis() - startTime));
//		}catch(Throwable e) {
//			result = apiResSignExceptionHandler(pjp, e);
//		}
//		return result;
//	}
//
//	/**
//	 * api用错误处理（返回值加签）
//	 * @param pjp
//	 * @param e
//	 * @return
//	 */
//	private ApiResult<?> apiResSignExceptionHandler(ProceedingJoinPoint pjp, Throwable e) {
//
//		ApiResult<?> result = new ApiResult<>();
//
//		// 已知异常
//		if (e instanceof CheckException) {
//			result.setStatus(((CheckException) e).getCode());
//			result.setStatusDesc(e.getLocalizedMessage());
//		} else {
//			logger.error(pjp.getSignature() + " 发生异常\r\n" + AOPUtil.getMethodArgs(pjp), e);
//			result.setStatus(BaseResult.ERROR);
//			result.setStatusDesc("接口调用发生异常，请联系服务方。");
//			//  发送邮件或者写到异常文件中
//		}
//		return result;
//	}
//
//	/**
//	 * webApi用日志处理
//	 * @param pjp
//	 * @return
//	 * @throws Throwable
//	 */
//	@Pointcut("execution(public com.hyjf.cs.common.bean.result.WebResult *(..))")
//	public Object webApiLogAOPHandler(ProceedingJoinPoint pjp) throws Throwable {
//
//		long startTime = System.currentTimeMillis();
//		ModelAndView result = null;
//		try {
//			result = (ModelAndView) pjp.proceed();
//			logger.info(pjp.getSignature() + " 执行时间:" + (System.currentTimeMillis() - startTime));
//		}catch(Throwable e) {
//			result = webApiExceptionHandler(pjp, e);
//		}
//		return result;
//	}
//
//	/**
//	 * webApi用错误日志处理
//	 * @param pjp
//	 * @param e
//	 * @return
//	 * @throws Throwable
//	 */
//	private ModelAndView webApiExceptionHandler(ProceedingJoinPoint pjp, Throwable e) throws Throwable {
//
////		ModelAndView result = new ModelAndView();
//
//		// 已知异常
//		if (e instanceof CheckException) {
//			logger.info(pjp.getSignature() + " " + e.getLocalizedMessage() + "\r\n" + AOPUtil.getMethodArgs(pjp));
//			throw e;
//		} else {
//			logger.error(pjp.getSignature() + " 发生异常\r\n" + AOPUtil.getMethodArgs(pjp), e);
//			throw e;
//			//  发送邮件或者写到异常文件中
//		}
////		return result;
//	}

	/**
	 * 原子层返回99时，往组合层抛出异常
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	/*@Around("execution(public * org.springframework.web.client.RestTemplate.*(..))")
	public Object amExceptionHandler(ProceedingJoinPoint pjp) throws Throwable {

		Object result = null;
		try {
			result = pjp.proceed();
			Response response = (Response)((ResponseEntity)result).getBody();
			if (response.getRtn().equals(Response.ERROR)){
				throw new AMException(response.getMessage());
			}
		}catch(Throwable e) {
			throw e;
		}
		return result;
	}*/
}