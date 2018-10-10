package com.hyjf.zuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.GatewayApiConfigVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.bean.AccessToken;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.jwt.JwtHelper;
import com.hyjf.common.util.AppUserToken;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.util.SignValue;
import com.hyjf.zuul.contant.GatewayConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author xiasq
 * @version AccessFilter, v0.1 2018/4/25 16:40 渠道过滤器，包含app pc api-web wechat
 * 功能：url增加前缀，区分渠道（按照域名）; 必要参数检查(渠道特有)
 */
public class AccessFilter extends ZuulFilter {
    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Value("${ignore.urls.app.key}")
    private String appKeyIgnoreUrls;

    private static final int STATUS_SUCCESS = 200;

    @Override
    public String filterType() {
        return GatewayConstant.PRE;
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
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String originalRequestPath = ctx.get(FilterConstants.REQUEST_URI_KEY).toString();
        logger.info("原始请求地址originalRequestPath:" + originalRequestPath);

        // 访问url是不是需要判断登录
        boolean secureVisitFlag;
        Map<String, Object> map = RedisUtils.getObj(RedisConstants.ZUUL_ROUTER_CONFIG_KEY, Map.class);
        if (!CollectionUtils.isEmpty(map)) {
            secureVisitFlag = isSecureVisit(map, originalRequestPath);
        } else {
            // 不对其进行路由
            return this.buildErrorRequestContext(ctx, 502, "gateway inner error!");
        }

        // 执行不同渠道转发逻辑
        if (originalRequestPath.contains(GatewayConstant.APP_CHANNEL)) {

            // app 共同参数
            String version = request.getParameter(GatewayConstant.VERSION);
            String sign = request.getParameter(GatewayConstant.SIGN);

            Assert.hasText(appKeyIgnoreUrls, "appKeyIgnoreUrls must not be null....");
            if (!appKeyIgnoreUrls.contains(originalRequestPath)) {
                if (sign == null) {
                    logger.error("sign is empty");
                    // 不对其进行路由
                    return this.buildErrorRequestContext(ctx, STATUS_SUCCESS, this.buildNoneSignResponseResult());
                }
                this.appNomalRequestProcess(request, ctx, sign);

                this.appSetUserIdProcess(ctx, sign, secureVisitFlag);

            } else {
                // app打开初始化操作
                this.initServer(request, ctx);
            }

            this.addCommonResponse(ctx, version);
        } else if (originalRequestPath.contains(GatewayConstant.WECHAT_CHANNEL)) {
            this.wechatSetUserIdProcess(request, ctx, secureVisitFlag);
        } else if (originalRequestPath.contains(GatewayConstant.WEB_CHANNEL)) {
            this.setUserIdByToken(request, ctx, secureVisitFlag, GatewayConstant.WEB_CHANNEL);
        } else if (originalRequestPath.contains(GatewayConstant.API_CHANNEL)) {
        	//do nothing api使用签名，没有用户认证
        } else {
            logger.error("error channel...");
            // 不对其进行路由
            this.buildErrorRequestContext(ctx, 502, "illegal visit!");
        }
        return null;
    }

    /**
     * app普通请求处理
     *
     * @param request
     * @param ctx
     * @param sign
     * @return
     */
    private Object appNomalRequestProcess(HttpServletRequest request, RequestContext ctx, String sign) {
        SignValue signValue = RedisUtils.getObj(RedisConstants.SIGN + sign, SignValue.class);
        if (signValue == null) {
            logger.error("sign is invalid");
            // 不对其进行路由
            return this.buildErrorRequestContext(ctx, STATUS_SUCCESS, this.buildNoneSignResponseResult());
        }
        ctx.addZuulRequestHeader(GatewayConstant.TOKEN, signValue.getToken());
        ctx.addZuulRequestHeader(GatewayConstant.SIGN, sign);
        ctx.addZuulRequestHeader(GatewayConstant.KEY, signValue.getKey());
        ctx.addZuulRequestHeader(GatewayConstant.INITKEY, signValue.getInitKey());
        ctx.addZuulRequestHeader(GatewayConstant.VERSION, signValue.getVersion());
        ctx.addZuulRequestHeader(GatewayConstant.PLATFORM, request.getParameter(GatewayConstant.PLATFORM));
        ctx.addZuulRequestHeader(GatewayConstant.RANDOM_STRING, request.getParameter(GatewayConstant.RANDOM_STRING));
        ctx.addZuulRequestHeader(GatewayConstant.NET_STATUS, request.getParameter(GatewayConstant.NET_STATUS));
        ctx.addZuulRequestHeader(GatewayConstant.ORDER, request.getParameter(GatewayConstant.ORDER));
        return ctx;
    }

    /**
     * 获取最优服务器，获取请求密钥处理
     *
     * @param request
     * @param ctx
     * @return
     */
    private Object initServer(HttpServletRequest request, RequestContext ctx) {
        ctx.addZuulRequestHeader(GatewayConstant.VERSION, request.getParameter(GatewayConstant.VERSION));
        ctx.addZuulRequestHeader(GatewayConstant.SIGN, request.getParameter(GatewayConstant.SIGN));
        ctx.addZuulRequestHeader(GatewayConstant.PLATFORM, request.getParameter(GatewayConstant.PLATFORM));
        ctx.addZuulRequestHeader(GatewayConstant.RANDOM_STRING, request.getParameter(GatewayConstant.RANDOM_STRING));
        ctx.addZuulRequestHeader(GatewayConstant.SECRET_KEY, request.getParameter(GatewayConstant.SECRET_KEY));
        ctx.addZuulRequestHeader(GatewayConstant.APP_ID, request.getParameter(GatewayConstant.APP_ID));
        return ctx;
    }

    /**
     * 返回app,h5访问必须的jumpcommend
     *
     * @param ctx
     * @return
     */
    private Object addCommonResponse(RequestContext ctx, String version) {
        HttpServletResponse response = ctx.getResponse();
        response.setHeader(GatewayConstant.JUMP_COMMEND, getLinkJumpPrefix(version));
        response.setHeader(GatewayConstant.VERSION, version);
        return response;
    }

    /**
     * 获取app h5 需要的JUMP_COMMEND
     *
     * @param version
     * @return
     */
    private String getLinkJumpPrefix(String version) {

        if (!StringUtils.isEmpty(version)) {
            // 取渠道号
            String vers[] = version.split("\\.");
            if (vers != null && vers.length > 3) {
                String pcode = vers[3];

                if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_39)) {
                    return GatewayConstant.HYJF;
                } else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_YXB)) {
                    return GatewayConstant.HYJF_YXB;
                } else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZNB)) {
                    return GatewayConstant.HYJF_ZNB;
                } else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZYB)) {
                    return GatewayConstant.HYJF_ZYB;
                } else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZZB)) {
                    return GatewayConstant.HYJF_ZZB;
                } else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_TEST)) {
                    return GatewayConstant.HYJF_TEST;
                }
            }

        }
        return GatewayConstant.HYJF;
    }

    /**
     * zuul拦截, 不对其进行路由
     *
     * @param ctx
     * @param gatewayCode
     * @param errorMessage
     * @return
     */
    private Object buildErrorRequestContext(RequestContext ctx, int gatewayCode, String errorMessage) {
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(gatewayCode);
        ctx.setResponseBody(errorMessage);
        return ctx;
    }

    /**
     * 重定向登录页面，返回指定错误，前端完成跳转
     *
     * @param ctx
     * @param
     * @param
     * @return
     */
    private Object redirectLoginPage(RequestContext ctx, String channel) {
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(STATUS_SUCCESS);
        JSONObject result = new JSONObject();

        if (GatewayConstant.APP_CHANNEL.equals(channel)) {
            result.put("status", "708");
            result.put("statusDesc", "need login");
        } else {
            result.put("status", "999");
            result.put("statusDesc", "need login");
        }
        ctx.setResponseBody(result.toJSONString());
        return ctx;
    }

    private String buildNoneSignResponseResult() {
        JSONObject result = new JSONObject();
        result.put("status", "707");
        result.put("statusDesc", "sign is invalid!");
        return result.toJSONString();
    }

    /**
     * web查找用户
     *
     * @param request
     * @param ctx
     * @param isNecessary true 登录才能访问 false 登录不登录均可访问
     * @return
     */
    private RequestContext setUserIdByToken(HttpServletRequest request, RequestContext ctx, boolean isNecessary,
                                            String channel) {
        String token = "";
        if (GatewayConstant.APP_CHANNEL.equals(channel)) {
            token = request.getParameter(GatewayConstant.TOKEN);
        } else {
            token = request.getHeader(GatewayConstant.TOKEN);
        }

        // 需要安全访问的请求，token空不路由，否则直接返回
        if (StringUtils.isBlank(token)) {
            logger.error("token is empty...");
            return executeResultOfTokenInvalid(ctx, isNecessary, GatewayConstant.WEB_CHANNEL);
        }

        // jwt解析token
        AccessToken accessToken = JwtHelper.parseToken(token);
        if (accessToken == null) {
            logger.error("user is not exist...");
            return executeResultOfTokenInvalid(ctx, isNecessary, GatewayConstant.WEB_CHANNEL);
        } else {
            Integer userId = accessToken.getUserId();
            WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY + userId, WebViewUserVO.class);
            if (user == null) {
                // 登陆过期
                logger.error("login is invalid...");
                return executeResultOfTokenInvalid(ctx, isNecessary, GatewayConstant.WEB_CHANNEL);
            }

            // 页面不活动30分钟过期
            Integer value = RedisUtils.getObj(RedisConstants.USER_TOEKN_KEY + token, Integer.class);
            if (value == null) {
                // 登陆过期
                logger.error("accessToken is timeout...");
                return executeResultOfTokenInvalid(ctx, isNecessary, GatewayConstant.WEB_CHANNEL);
            }
            // 每次操作，延长超时时间
            RedisUtils.setObjEx(RedisConstants.USER_TOEKN_KEY + token, user.getUserId(), 30 * 60);

            ctx.addZuulRequestHeader("userId", accessToken.getUserId() + "");
            logger.info(String.format("user token:%s userId:%s", token, accessToken.getUserId()));
        }
        return ctx;
    }


    /**
     * app查找用户
     *
     * @param ctx
     * @param sign
     * @param isNecessary
     * @return
     */
    private Object appSetUserIdProcess(RequestContext ctx, String sign, boolean isNecessary) {
        Integer userId = SecretUtil.getUserId(sign);
        if (userId == null) {
            logger.error("sign invalid...");
            return executeResultOfTokenInvalid(ctx, isNecessary, GatewayConstant.APP_CHANNEL);
        }
        ctx.addZuulRequestHeader("userId", userId + "");
        return ctx;
    }

    /**
     * wechat查找用户
     *
     * @param request
     * @param ctx
     * @return
     */
    private RequestContext wechatSetUserIdProcess(HttpServletRequest request, RequestContext ctx, boolean isNecessary) {
        String sign = request.getParameter(GatewayConstant.SIGN);
        if (StringUtils.isBlank(sign)) {
            sign = (String) request.getAttribute(GatewayConstant.SIGN);
        }
        if (StringUtils.isNotBlank(sign)) {
            logger.info("sign:" + sign);
            // 获取用户ID
            AppUserToken appUserToken = SecretUtil.getAppUserToken(sign);
            if (appUserToken == null || appUserToken.getUserId() == null) {
                logger.error("token invalid...");
                return executeResultOfTokenInvalid(ctx, isNecessary, GatewayConstant.WECHAT_CHANNEL);
            }
            logger.info("appUserToken:" + appUserToken.getUserId());
            Integer userId = appUserToken.getUserId();
            String accountId = appUserToken.getAccountId();
            // 需要刷新 sign
            SecretUtil.refreshSign(sign);
            ctx.addZuulRequestHeader("userId", userId + "");
            ctx.addZuulRequestHeader("accountId", accountId);
            ctx.addZuulRequestHeader("sign", sign);
        } else {
            return executeResultOfTokenInvalid(ctx, isNecessary, GatewayConstant.WECHAT_CHANNEL);
        }
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
                logger.info("vo: {}", vo);
                if (vo.getSecureVisitFlag() != null && vo.getSecureVisitFlag().intValue() == 1) {
                    secureVisitFlag = true;
                }
            }
        }
        logger.info(originalRequestPath + " : secureVisitFlag: " + secureVisitFlag);
        return secureVisitFlag;
    }


    /**
     * token无效情况下判断是否安全访问执行不同策略
     *
     * @param ctx
     * @param isNecessary
     * @return
     */
    private RequestContext executeResultOfTokenInvalid(RequestContext ctx, boolean isNecessary, String channel) {
        if (isNecessary) {
            // 不对其进行路由
            this.redirectLoginPage(ctx, channel);
        }
        return ctx;
    }

}
