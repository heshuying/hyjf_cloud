package com.hyjf.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyjf.common.util.SignValue;
import com.hyjf.zuul.redis.RedisUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author xiasq
 * @version AppFilter, v0.1 2018/4/25 16:40
 */
public class AppFilter extends ZuulFilter {
	private static Logger logger = LoggerFactory.getLogger(AppFilter.class);

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	/**
	 * app的请求
	 *
	 * @return
	 */
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		String requestUrl = ctx.getRequest().getRequestURL().toString();
		// 最优服务器不校验
		boolean shoudFilter = requestUrl.contains("app") && !requestUrl.contains("server/getBestServerAction");
		logger.info("AppFilter url:" + requestUrl + " " + shoudFilter);
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

		String sign = request.getHeader("sign");
		if (sign == null) {
			logger.error("sign is empty");
			ctx.setResponseBody("sign is empty");
			return null;
		}
		SignValue signValue = (SignValue) redisUtil.get(sign);
		ctx.addZuulRequestHeader("key", signValue.getKey());
		ctx.addZuulRequestHeader("initKey", signValue.getInitKey());
		return null;
	}
}
