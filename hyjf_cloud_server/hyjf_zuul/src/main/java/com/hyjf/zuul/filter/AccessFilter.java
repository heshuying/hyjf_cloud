package com.hyjf.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.zuul.redis.RedisUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author xiasq
 * @version PCAccessFilter, v0.1 2018/4/24 9:29
 */
public class AccessFilter extends ZuulFilter {
	private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	/**
	 * 定义哪些请求需要检查token 请求路径包含secure,需要检查token
	 * 
	 * @return
	 */
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		String requestUri = ctx.getRequest().getRequestURI();
		boolean shoudFilter = requestUri.contains("secure");
		logger.info("uri:" + requestUri + " " + shoudFilter);
		return shoudFilter;
	}

	/**
	 * 过滤
	 * 
	 * @return
	 * @throws ZuulException
	 */
	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		logger.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

		String token = request.getHeader("token");
		if (token == null) {
			logger.error("token is empty");
			ctx.setResponseBody("token is empty");
			return null;
		}

		UserVO userVO = (UserVO) redisUtil.get(RedisKey.USER_TOKEN_REDIS + token);
		if (userVO == null) {
			ctx.setResponseBody("user is not exist");
		}
		ctx.addZuulRequestHeader("userId", userVO.getUserId() + "");
		logger.info(String.format("user token:%s userId:%s", token, userVO.getUserId()));
		return null;
	}
}
