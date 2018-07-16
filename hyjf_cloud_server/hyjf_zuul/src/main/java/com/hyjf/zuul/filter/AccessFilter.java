package com.hyjf.zuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.GatewayApiConfigVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.util.SignValue;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version AccessFilter, v0.1 2018/4/25 16:40 渠道过滤器，包含app pc api-web wechat
 *          功能：url增加前缀，区分渠道（按照域名）; 必要参数检查(渠道特有)
 */
public class AccessFilter extends ZuulFilter {
	private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

	@Value("${ignore.urls.app.key}")
	private String appKeyIgnoreUrls;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
	}

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
		// 请求uri
		String originalRequestPath = ctx.get(FilterConstants.REQUEST_URI_KEY).toString();
		String fullRequestUrl = request.getRequestURL().toString();
		String requestUri = request.getRequestURI().toString();

		boolean secureVisitFlag;
		Map<String, Object> map = RedisUtils.getObj(RedisConstants.ZUUL_ROUTER_CONFIG_KEY, Map.class);
		if (!CollectionUtils.isEmpty(map)) {
			secureVisitFlag = isSecureVisit(map, originalRequestPath);
		} else {
			// 不对其进行路由
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(502);
			ctx.setResponseBody("网关内部错误!");
			return null;
		}

		// 截取访问域名
		String requestUrl = fullRequestUrl.substring(0, fullRequestUrl.length() - requestUri.length() + 1);
		String prefix;
		if (requestUrl.contains("app")) {
			if (StringUtils.isNotBlank(appKeyIgnoreUrls) && !appKeyIgnoreUrls.contains(requestUri)) {
				String sign = request.getParameter("sign");
				if (sign == null) {
					logger.error("sign is empty");
					// 不对其进行路由
					ctx.setSendZuulResponse(false);
					ctx.setResponseStatusCode(400);
					ctx.setResponseBody("sign is empty");
					return null;
				}
				SignValue signValue = RedisUtils.getObj(sign, SignValue.class);
				ctx.addZuulRequestHeader("key", signValue.getKey());
				ctx.addZuulRequestHeader("initKey", signValue.getInitKey());
				ctx.addZuulRequestHeader("version", signValue.getVersion());
				ctx.addZuulRequestHeader("token", signValue.getToken());
				ctx.addZuulRequestHeader("sign", sign);
			}
			if (secureVisitFlag) {
				ctx = setUserIdByToken(request, ctx);
			}
			prefix = "/app";
		} else if (requestUrl.contains("web")) {
			if (secureVisitFlag) {
				ctx = setUserIdByToken(request, ctx);
			}
			prefix = "/web";
		} else if (requestUrl.contains("wechat")) {
			if (secureVisitFlag) {
				ctx = setUserIdByToken(request, ctx);
			}
			prefix = "/wechat";
		} else if (requestUrl.contains("api")) {
			prefix = "/api";
		}else {
			// 不对其进行路由
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(502);
			ctx.setResponseBody("非法域名访问!");
			return null;
		}

		// 增加请求前缀识别渠道
		String modifiedRequestPath = prefix + originalRequestPath;
		ctx.put(FilterConstants.REQUEST_URI_KEY, modifiedRequestPath);
		return null;
	}

	/**
	 * token查找用户
	 * @param request
	 * @param ctx
	 * @return
	 */
	private RequestContext setUserIdByToken(HttpServletRequest request, RequestContext ctx) {
		String token = request.getHeader("token");
		if (token == null) {
			logger.error("token is empty...");
			// 不对其进行路由
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(400);
			ctx.setResponseBody("token is empty");
			return ctx;
		}
		WebViewUserVO webViewUserVO = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS + token, WebViewUserVO.class);
		if (webViewUserVO == null) {
			logger.error("user is not exist...");
			// 不对其进行路由
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(400);
			ctx.setResponseBody("user is not exist");
			return ctx;
		}
		ctx.addZuulRequestHeader("userId", webViewUserVO.getUserId() + "");
		logger.info(String.format("user token:%s userId:%s", token, webViewUserVO.getUserId()));
		return ctx;
	}

	/**
	 * 判断是否需要登陆访问
	 * @param map
	 * @param originalRequestPath
	 * @return
	 */
	private boolean isSecureVisit(Map<String, Object> map, String originalRequestPath) {
		boolean secureVisitFlag = false;
		for (String key : map.keySet()) {
			if (key.contains("?")) {
				key = key.substring(0, key.indexOf("?") - 1);
			}
			if (key.contains("*")) {
				key = key.substring(0, key.indexOf("*") - 1);
			}
			// 路径匹配
			if (originalRequestPath.startsWith(key)) {
				// 判断是否是安全访问
				GatewayApiConfigVO vo =  JSONObject.parseObject(map.get(key).toString(), GatewayApiConfigVO.class) ;
				if (vo.getSecureVisitFlag() == 1) {
					secureVisitFlag = true;
				}
			}
		}
		return secureVisitFlag;
	}

}
