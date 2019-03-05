/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.common.aspect;

import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.exception.CheckException;
import com.hyjf.cs.common.annotation.RequestLimit;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dxj
 * @version RequestLimitAspect.java, v0.1 2018年7月20日 下午5:17:48
 */

@Component
@Aspect
public class RequestLimitAspect {

	private final Logger logger = LoggerFactory.getLogger(getClass());
 
    @Before("execution(public * com.hyjf.cs.*.controller..*.*(..)) && @annotation(limit)")
    public void requestLimit(JoinPoint joinpoint, RequestLimit limit) {
    	
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
 
        String ip = request.getRemoteAddr();
//        String url = request.getRequestURL().toString();
        String uri = request.getServletPath();
        
        String userId = request.getHeader("userId");
        
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(uri)) {
        	 throw new CheckException("请求重复验证异常");
        }
        
        String key = RedisConstants.PRE_REQUEST_LIMIT.concat(userId).concat(":").concat(uri);
        
        boolean setResult = RedisUtils.setnx(key, ip, limit.seconds());
        // 成功则是第一次，否则的话就是重复
        if (!setResult) {
        	logger.info("用户[" + userId + "]访问地址[" + uri + "]请求重复[" + limit.seconds() + "]");
            throw new CheckException("");
        }
        
    }

}
