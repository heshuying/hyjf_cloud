package com.hyjf.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.util.SignValue;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author xiasq
 * @version AccessFilter, v0.1 2018/4/25 16:40 渠道过滤器，包含app pc api-web wechat
 *          功能：url增加前缀，区分渠道（按照域名）; 必要参数检查(渠道特有)
 */
public class AccessFilter extends ZuulFilter {
	private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

	@Value("${ignore.urls.app.user}")
	private String appIgnoreUrls;
	@Value("${ignore.urls.app.key}")
	private String appKeyIgnoreUrls;
	@Value("${ignore.urls.web}")
	private String webIgnoreUrls;
	@Value("${ignore.urls.wechat}")
	private String wechatIgnoreUrls;
	@Value("${ignore.urls.api}")
	private String apiIgnoreUrls;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
	}

	/**
	 * app的请求
	 *
	 * @return
	 */
	@Override
	public boolean shouldFilter() {
		return true;
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

		String fullRequestUrl = request.getRequestURL().toString();
		String requestUri = request.getRequestURI().toString();
		// 截取访问域名
		String requestUrl = fullRequestUrl.substring(0, fullRequestUrl.length() - requestUri.length() + 1);
		String prefix = "/api";
		if (requestUrl.contains("app")) {
			if (StringUtils.isNotBlank(appKeyIgnoreUrls) && !appKeyIgnoreUrls.contains(requestUri)) {
				String sign = request.getHeader("sign");
				if (sign == null) {
					logger.error("sign is empty");
					ctx.setResponseBody("sign is empty");
					return null;
				}
				SignValue signValue = RedisUtils.getObj(sign, SignValue.class);
				ctx.addZuulRequestHeader("key", signValue.getKey());
				ctx.addZuulRequestHeader("initKey", signValue.getInitKey());
			}
			if (StringUtils.isNotBlank(appIgnoreUrls) && !appIgnoreUrls.contains(requestUri)) {
				setUserIdByToken(request, ctx);
			}
			prefix = "/app";
		} else if (requestUrl.contains("web")) {
			if (StringUtils.isNotBlank(webIgnoreUrls) && !webIgnoreUrls.contains(requestUri)) {
				setUserIdByToken(request, ctx);
			}
			prefix = "/web";
		} else if (requestUrl.contains("wechat")) {
			if (StringUtils.isNotBlank(wechatIgnoreUrls) && !wechatIgnoreUrls.contains(requestUri)) {
				setUserIdByToken(request, ctx);
			}
			prefix = "/wechat";
		} else if (requestUrl.contains("api")) {
			// to do nothing;
		}

		// 增加请求前缀识别渠道
		Object originalRequestPath = ctx.get(FilterConstants.REQUEST_URI_KEY);
		String modifiedRequestPath = prefix + originalRequestPath;
		ctx.put(FilterConstants.REQUEST_URI_KEY, modifiedRequestPath);
		return null;
	}

	private void setUserIdByToken(HttpServletRequest request, RequestContext ctx) {
		String token = request.getHeader("token");
		if (token == null) {
			logger.error("token is empty...");
			ctx.setResponseBody("token is empty");
			return;
		}
		WebViewUser webViewUser = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS + token, WebViewUser.class);
		if (webViewUser == null) {
			logger.error("user is not exist...");
			ctx.setResponseBody("user is not exist");
			return;
		}
		ctx.addZuulRequestHeader("userId", webViewUser.getUserId() + "");
		logger.info(String.format("user token:%s userId:%s", token, webViewUser.getUserId()));
	}

}
