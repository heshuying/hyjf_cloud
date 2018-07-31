package com.hyjf.zuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.GatewayApiConfigVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.util.SignValue;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
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

	private static final String APP_CHANNEL = "app";
	private static final String WEB_CHANNEL = "web";
	private static final String WEB_VISIT_URL = "/hyjf-web";
	private static final String WECHAT_CHANNEL = "wechat";
	private static final String WECHAT_VISIT_URL = "/hyjf-wechat";
	private static final String API_CHANNEL = "api";
	private static final String API_VISIT_URL = "/hyjf-api";

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
			return this.buildErrorRequestContext(ctx, 502, "gateway inner error!");
		}

		// 截取访问域名
		String requestUrl = fullRequestUrl.substring(0, fullRequestUrl.length() - requestUri.length() + 1);
		String prefix;
		if (requestUrl.contains(APP_CHANNEL)) {
			Assert.hasText(appKeyIgnoreUrls, "appKeyIgnoreUrls must not be null....");
			if (!appKeyIgnoreUrls.contains(requestUri)) {
				String sign = request.getParameter("sign");
				if (sign == null) {
					logger.error("sign is empty");
					// 不对其进行路由
					return this.buildErrorRequestContext(ctx, 400, "sign is empty!");
				}
				SignValue signValue = RedisUtils.getObj(RedisConstants.SIGN+sign, SignValue.class);
				ctx.addZuulRequestHeader("key", signValue.getKey());
				ctx.addZuulRequestHeader("initKey", signValue.getInitKey());
				ctx.addZuulRequestHeader("version", signValue.getVersion());
				ctx.addZuulRequestHeader("token", signValue.getToken());
				ctx.addZuulRequestHeader("sign", sign);

				if (secureVisitFlag) {
					setUserIdByToken(request, ctx, secureVisitFlag,APP_CHANNEL);
				}
			} else {
				// 获取最优服务器
				ctx.addZuulRequestHeader("platform", request.getParameter("platform"));
				ctx.addZuulRequestHeader("randomString", request.getParameter("randomString"));
				ctx.addZuulRequestHeader("secretKey", request.getParameter("secretKey"));
				ctx.addZuulRequestHeader("appId", request.getParameter("appId"));
				ctx.addZuulRequestHeader("version", request.getParameter("version"));
			}
			// app自带hyjf-app 直接返回即可
			return null;
		} else if (requestUrl.contains(WEB_CHANNEL)) {
			if (secureVisitFlag) {
				ctx = setUserIdByToken(request, ctx, secureVisitFlag,WEB_CHANNEL);
			}
			prefix = WEB_VISIT_URL;
		} else if (requestUrl.contains(WECHAT_CHANNEL)) {
			if (secureVisitFlag) {
				ctx = setUserIdByToken(request, ctx, secureVisitFlag,WECHAT_CHANNEL);
			}
			prefix = WECHAT_VISIT_URL;
		} else if (requestUrl.contains(API_CHANNEL)) {
			prefix = API_VISIT_URL;
		} else {
			// 不对其进行路由
			return this.buildErrorRequestContext(ctx, 502, "illegal visit!");
		}
		// 增加请求前缀识别渠道
		String modifiedRequestPath = prefix + originalRequestPath;
		ctx.put(FilterConstants.REQUEST_URI_KEY, modifiedRequestPath);
		return null;
	}

	/**
	 * zuul拦截, 不对其进行路由
	 * @param ctx
	 * @param gatewayCode
	 * @param errorMessage
	 * @return
	 */
	private Object buildErrorRequestContext(RequestContext ctx, int gatewayCode, String errorMessage){
		ctx.setSendZuulResponse(false);
		ctx.setResponseStatusCode(gatewayCode);
		ctx.setResponseBody(errorMessage);
		return null;
	}

	/**
	 * token查找用户
	 * 
	 * @param request
	 * @param ctx
	 * @param isNecessary
	 *            true 登录才能访问 false 登录不登录均可访问
	 * @return
	 */
	private RequestContext setUserIdByToken(HttpServletRequest request, RequestContext ctx, boolean isNecessary,String channel) {
		String token = "";
		if (APP_CHANNEL.equals(channel)){
			token = request.getParameter("token");
		}else if(WECHAT_CHANNEL.equals(channel)){
			String sign = request.getParameter("sign");
			token = SecretUtil.getToken(sign);
		}else {
			token = request.getHeader("token");
		}
		if (StringUtils.isBlank(token) && isNecessary) {
			logger.error("token is empty...");
			// 不对其进行路由
			this.buildErrorRequestContext(ctx, 400, "token is empty!");
			return ctx;
		}
		WebViewUserVO webViewUserVO = RedisUtils.getObj(RedisConstants.USER_TOKEN_REDIS + token, WebViewUserVO.class);
		if (webViewUserVO == null) {
			if (isNecessary) {
				logger.error("user is not exist...");
				// 不对其进行路由
				this.buildErrorRequestContext(ctx, 400, "user is not exist!");
				return ctx;
			} else {
				return ctx;
			}
		}
		ctx.addZuulRequestHeader("userId", webViewUserVO.getUserId() + "");
		logger.info(String.format("user token:%s userId:%s", token, webViewUserVO.getUserId()));
		return ctx;
	}

	/**
	 * 判断是否需要登陆访问
	 * 
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
				GatewayApiConfigVO vo = JSONObject.parseObject(map.get(key).toString(), GatewayApiConfigVO.class);
				if (vo.getSecureVisitFlag() == 1) {
					secureVisitFlag = true;
				}
			}
		}
		return secureVisitFlag;
	}

}
