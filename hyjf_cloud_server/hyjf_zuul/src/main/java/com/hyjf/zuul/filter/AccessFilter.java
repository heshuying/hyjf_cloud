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

    /**
     * 不同客户端的请求包含不同的关键字（域名）
     */
    private static final String APP_CHANNEL = "app";
    private static final String WEB_CHANNEL = "web";
    private static final String API_CHANNEL = "api";
    private static final String WECHAT_CHANNEL = "wechat";

    /**
     * 各客户端增加的前缀 微信和app自带hyjf-xx请求，不用处理
     */
    private static final String WEB_VISIT_URL = "/hyjf-web";
    private static final String API_VISIT_URL = "/hyjf-api";

    /**
     * app共同字段
     */
    private static final String VERSION = "version";
    private static final String SIGN = "sign";
    private static final String TOKEN = "token";
    private static final String KEY = "key";
    private static final String INITKEY = "initKey";
    /**
     * （0:pc 1:Android 2:IOS 3:微信）
     */
    private static final String PLATFORM = "platform";
    private static final String RANDOM_STRING = "randomString";
    /**
     * 安全码（经过appId和appKey算出来，然后连接上随机字符串，进行ASCII码生序排列）
     */
    private static final String SECRET_KEY = "secretKey";
    private static final String APP_ID = "appId";
    private static final String NET_STATUS = "netStatus";
    /**
     * order生成规则：token+随机字符串连接在一起，然后经过ASCII码升序排序，最后经过key加密（当没有token时，规则是随机字符串连接在一起，然后经过ASCII码升序排序，最后经过key加密）
     */
    private static final String ORDER = "order";
    private static final String JUMP_COMMEND = "jumpcommend";

    /**
     * app的设备所属渠道-马甲
     */
    private static final String HYJF = "hyjf";
    private static final String HYJF_YXB = "hyjfYXB";
    private static final String HYJF_ZNB = "hyjfZNB";
    private static final String HYJF_ZYB = "hyjfZYB";
    private static final String HYJF_ZZB = "hyjfZZB";
    private static final String HYJF_TEST = "hyjfTEST";

    /**
     * 过滤器类型
     */
    private static final String PRE = "pre";

    @Override
    public String filterType() {
        return PRE;
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
        // 请求uri
        String originalRequestPath = ctx.get(FilterConstants.REQUEST_URI_KEY).toString();
        String fullRequestUrl = request.getRequestURL().toString();
        String requestUri = request.getRequestURI().toString();
        logger.info("原始请求地址fullRequestUrl:" + fullRequestUrl);
        // 访问url是不是需要判断登录
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
        String prefix = null;

        // 执行不同渠道转发逻辑
        if (requestUrl.contains(APP_CHANNEL)) {

            // app 共同参数
            String version = request.getParameter(VERSION);
            String sign = request.getParameter(SIGN);

            Assert.hasText(appKeyIgnoreUrls, "appKeyIgnoreUrls must not be null....");
            if (!appKeyIgnoreUrls.contains(requestUri)) {
                if (sign == null) {
                    logger.error("sign is empty");
                    // 不对其进行路由
                    return this.buildErrorRequestContext(ctx, 400, this.buildNoneSignResponseResult());
                }
                this.appNomalRequestProcess(request, ctx, sign);

                this.appSetUserIdProcess(ctx, sign, secureVisitFlag);

            } else {
                // app打开初始化操作
                this.initServer(request, ctx);
            }

            this.addCommonResponse(ctx, version);
            // app自带hyjf-app 直接返回即可
            return null;
        } else if (requestUrl.contains(WECHAT_CHANNEL)) {
            this.wechatSetUserIdProcess(request, ctx, secureVisitFlag);
            // wechat自带hyjf-wechat 直接返回即可
            return null;
        }

        if (requestUrl.contains(WEB_CHANNEL)) {
            ctx = this.setUserIdByToken(request, ctx, secureVisitFlag, WEB_CHANNEL);
            prefix = WEB_VISIT_URL;
        } else if (requestUrl.contains(API_CHANNEL)) {
            prefix = API_VISIT_URL;
        } else {
            logger.error("error channel...");
            // 不对其进行路由
            this.buildErrorRequestContext(ctx, 502, "illegal visit!");
            return null;
        }

        // 增加请求前缀识别渠道
        String modifiedRequestPath = prefix + originalRequestPath;
        ctx.put(FilterConstants.REQUEST_URI_KEY, modifiedRequestPath);
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
            return this.buildErrorRequestContext(ctx, 400, this.buildNoneSignResponseResult());
        }
        ctx.addZuulRequestHeader(TOKEN, signValue.getToken());
        ctx.addZuulRequestHeader(SIGN, sign);
        ctx.addZuulRequestHeader(KEY, signValue.getKey());
        ctx.addZuulRequestHeader(INITKEY, signValue.getInitKey());
        ctx.addZuulRequestHeader(VERSION, signValue.getVersion());
        ctx.addZuulRequestHeader(PLATFORM, request.getParameter(PLATFORM));
        ctx.addZuulRequestHeader(RANDOM_STRING, request.getParameter(RANDOM_STRING));
        ctx.addZuulRequestHeader(NET_STATUS, request.getParameter(NET_STATUS));
        ctx.addZuulRequestHeader(ORDER, request.getParameter(ORDER));
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
        ctx.addZuulRequestHeader(VERSION, request.getParameter(VERSION));
        ctx.addZuulRequestHeader(SIGN, request.getParameter(SIGN));
        ctx.addZuulRequestHeader(PLATFORM, request.getParameter(PLATFORM));
        ctx.addZuulRequestHeader(RANDOM_STRING, request.getParameter(RANDOM_STRING));
        ctx.addZuulRequestHeader(SECRET_KEY, request.getParameter(SECRET_KEY));
        ctx.addZuulRequestHeader(APP_ID, request.getParameter(APP_ID));
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
        response.setHeader(JUMP_COMMEND, getLinkJumpPrefix(version));
        response.setHeader(VERSION, version);

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
                    return HYJF;
                } else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_YXB)) {
                    return HYJF_YXB;
                } else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZNB)) {
                    return HYJF_ZNB;
                } else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZYB)) {
                    return HYJF_ZYB;
                } else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZZB)) {
                    return HYJF_ZZB;
                } else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_TEST)) {
                    return HYJF_TEST;
                }
            }

        }
        return HYJF;
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
        ctx.setResponseStatusCode(200);
        JSONObject result = new JSONObject();

        if (APP_CHANNEL.equals(channel)) {
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
     * token查找用户
     *
     * @param request
     * @param ctx
     * @param isNecessary true 登录才能访问 false 登录不登录均可访问
     * @return
     */
    private RequestContext setUserIdByToken(HttpServletRequest request, RequestContext ctx, boolean isNecessary,
                                            String channel) {
        String token = "";
        if (APP_CHANNEL.equals(channel)) {
            token = request.getParameter(TOKEN);
        } else {
            token = request.getHeader(TOKEN);
        }

        // 需要安全访问的请求，token空不路由，否则直接返回
        if (StringUtils.isBlank(token)) {
            logger.error("token is empty...");
            return executeResultOfTokenInvalid(ctx, isNecessary, WEB_CHANNEL);
        }

        // jwt解析token
        AccessToken accessToken = JwtHelper.parseToken(token);
        if (accessToken == null) {
            logger.error("user is not exist...");
            return executeResultOfTokenInvalid(ctx, isNecessary, WEB_CHANNEL);
        } else {
            Integer userId = accessToken.getUserId();
            WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY + userId, WebViewUserVO.class);
            if (user == null) {
                // 登陆过期
                logger.error("login is invalid...");
                return executeResultOfTokenInvalid(ctx, isNecessary, WEB_CHANNEL);
            }

            // 页面不活动30分钟过期
            Integer value = RedisUtils.getObj(RedisConstants.USER_TOEKN_KEY + token, Integer.class);
            if (value == null) {
                // 登陆过期
                logger.error("accessToken is timeout...");
                return executeResultOfTokenInvalid(ctx, isNecessary, WEB_CHANNEL);
            }
            // 每次操作，延长超时时间
            RedisUtils.setObjEx(RedisConstants.USER_TOEKN_KEY + token, user.getUserId(), 30 * 60);

            ctx.addZuulRequestHeader("userId", accessToken.getUserId() + "");
            logger.info(String.format("user token:%s userId:%s", token, accessToken.getUserId()));
        }
        return ctx;
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

    /**
     * app处理
     *
     * @param ctx
     * @param sign
     * @param isNecessary
     * @return
     */
    private Object appSetUserIdProcess(RequestContext ctx, String sign, boolean isNecessary) {
        Integer userId = SecretUtil.getUserId(sign);
        if (userId == null) {
            logger.error("token invalid...");
            return executeResultOfTokenInvalid(ctx, isNecessary, APP_CHANNEL);
        }
        ctx.addZuulRequestHeader("userId", userId + "");
        return ctx;
    }

    /**
     * 微信处理
     *
     * @param request
     * @param ctx
     * @return
     */
    private RequestContext wechatSetUserIdProcess(HttpServletRequest request, RequestContext ctx, boolean isNecessary) {
        String sign = request.getParameter(SIGN);
        if (StringUtils.isBlank(sign)) {
            sign = (String) request.getAttribute(SIGN);
        }
        if (StringUtils.isNotBlank(sign)) {
            logger.info("sign:"+sign);
            // 获取用户ID
            AppUserToken appUserToken = SecretUtil.getAppUserToken(sign);
            logger.info("appUserToken:"+appUserToken.getUserId());
            if (appUserToken == null || appUserToken.getUserId() == null) {
                logger.error("token invalid...");
                return executeResultOfTokenInvalid(ctx, isNecessary, WECHAT_CHANNEL);
            }

            Integer userId = appUserToken.getUserId();
            String accountId = appUserToken.getAccountId();
            // 需要刷新 sign
            SecretUtil.refreshSign(sign);
            ctx.addZuulRequestHeader("userId", userId + "");
            ctx.addZuulRequestHeader("accountId", accountId);
            ctx.addZuulRequestHeader("sign", sign);
        } else {
            return executeResultOfTokenInvalid(ctx, isNecessary, WECHAT_CHANNEL);
        }
        logger.info(ctx.getResponseBody());
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

}
