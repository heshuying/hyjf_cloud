package com.hyjf.zuul.contant;

/**
 * @author xiasq
 * @version GatewayConstant, v0.1 2018/9/29 10:47
 */
public interface GatewayConstant {

    /**
     * 1. 过滤器类型
     */
    String PRE = "pre";


    /**
     * 2. 不同客户端的请求包含不同的关键字（域名）
     */
    String APP_CHANNEL = "hyjf-app";
    String WEB_CHANNEL = "hyjf-web";
    String API_CHANNEL = "hyjf-api";
    String WECHAT_CHANNEL = "hyjf-wechat";
    String WBS_CHANNEL = "hyjf-wbs";

    /**
     * 3. api增加的前缀,其他渠道无需增加，前端访问自带
     */
    String API_VISIT_URL = "/hyjf-api";


    /**
     * 4. app共同字段
     */
    String VERSION = "version";
    String SIGN = "sign";
    String TOKEN = "token";
    String KEY = "key";
    String INITKEY = "initKey";
    /**
     * （0:pc 1:Android 2:IOS 3:微信）
     */
    String PLATFORM = "platform";
    String RANDOM_STRING = "randomString";
    /**
     * 安全码（经过appId和appKey算出来，然后连接上随机字符串，进行ASCII码生序排列）
     */
    String SECRET_KEY = "secretKey";
    String APP_ID = "appId";
    String NET_STATUS = "netStatus";
    /**
     * order生成规则：token+随机字符串连接在一起，然后经过ASCII码升序排序，最后经过key加密（当没有token时，规则是随机字符串连接在一起，然后经过ASCII码升序排序，最后经过key加密）
     */
    String ORDER = "order";
    String JUMP_COMMEND = "jumpcommend";



    /**
     * 5. app的设备所属渠道-马甲
     */
    String HYJF = "hyjf";
    String HYJF_YXB = "hyjfYXB";
    String HYJF_ZNB = "hyjfZNB";
    String HYJF_ZYB = "hyjfZYB";
    String HYJF_ZZB = "hyjfZZB";
    String HYJF_TEST = "hyjfTEST";
}
