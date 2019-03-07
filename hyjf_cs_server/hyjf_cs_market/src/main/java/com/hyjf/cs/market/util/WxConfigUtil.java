package com.hyjf.cs.market.util;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.exception.ServiceException;
import com.hyjf.common.util.GetCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiasq
 * @version WxConfigUtil, v0.1 2018/5/10 9:12 公众平台通用接口工具类
 */
public class WxConfigUtil {
    private static final Logger logger = LoggerFactory.getLogger(WxConfigUtil.class);

    /**
     * 获取access_token的接口地址（GET） 限2000（次/天）
     */
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /**
     * 获取jsapi_ticket的接口地址（GET） 限2000（次/天）
     */
    public final static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
    /**
     * 缓存添加的时间
     */
    public static String cacheAddTime = null;
    /**
     * token,ticket缓存
     */
    public static volatile Map<String, Token> TOKEN_TICKET_CACHE = new HashMap<String, Token>();
    /**
     * token对应的key
     */
    private static final String TOKEN = "token";
    /**
     * ticket对应的key
     */
    private static final String TICKET = "ticket";

    /**
     * 外部获取签名入口类
     *
     * @param url
     * @param appId
     * @param secret
     * @param userId
     * @return
     */
    public static TicketBean getSignature(String url, String appId, String secret, Integer userId) {
        // 生成签名的随机串
        String noncestr = GetCode.getRandomCode(4);
        if (StringUtils.isBlank(url)) {
            logger.error("获取url失败...");
            return null;
        }
        String signature = null;
        Token accessTocken = getToken(appId, secret, System.currentTimeMillis() / 1000);
        if (accessTocken == null || StringUtils.isBlank(accessTocken.getToken())) {
            throw new ServiceException("微信接口调用失败, accessTocken is null...");
        }
        Token accessTicket = getTicket(accessTocken.getToken(), System.currentTimeMillis() / 1000);
        if ( accessTicket == null || StringUtils.isBlank(accessTicket.getTicket())){
            throw new ServiceException("微信接口调用失败, accessTicket is null...");
        }
        String jsapi_ticket = accessTicket.getTicket();
        signature = signature(jsapi_ticket, cacheAddTime, noncestr, url);
        logger.info("-=-=-=-=-=-=-=-=url:" + url);
        logger.info("-=-=-=-=-=-=-=-=token:" + accessTocken.getToken());
        logger.info("-=-=-=-=-=-=-=-=jsapi_ticket:" + jsapi_ticket);
        logger.info("-=-=-=-=-=-=-=-=timestamp:" + cacheAddTime);
        logger.info("-=-=-=-=-=-=-=-=noncestr:" + noncestr);
        logger.info("-=-=-=-=-=-=-=-=signature:" + signature);

        TicketBean ticketBean = new TicketBean();
        ticketBean.setTimestamp(cacheAddTime);
        ticketBean.setNonceStr(noncestr);
        ticketBean.setJsapi_ticket(jsapi_ticket);
        ticketBean.setSignature(signature);
        return ticketBean;
    }

    /**
     * 签名
     *
     * @param jsapi_ticket
     * @param timestamp
     * @param noncestr
     * @param url
     * @return
     */
    private static String signature(String jsapi_ticket, String timestamp, String noncestr, String url) {
        jsapi_ticket = "jsapi_ticket=" + jsapi_ticket;
        timestamp = "timestamp=" + timestamp;
        noncestr = "noncestr=" + noncestr;
        url = "url=" + url;
        String[] arr = new String[]{jsapi_ticket, noncestr, timestamp, url};
        // 将token、timestamp、nonce,url参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
            if (i != arr.length - 1) {
                content.append("&");
            }
        }
        logger.info("signature加密前content is : {}", content.toString());
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }
        return tmpStr;
    }

    /**
     * 获取access_token
     *
     * @param appId
     * @param appsecret
     * @param currentTime
     * @return
     */
    public static Token getToken(String appId, String appsecret, long currentTime) {
        logger.info("appId : is {}!", appId);
        logger.info("appsecret : is {}!", appsecret);
        Token tockenTicketCache = getTokenTicket(TOKEN);
        Token token = null;
        // 缓存存在并且没过期
        if (tockenTicketCache != null
                && (currentTime - tockenTicketCache.getAddTime() <= tockenTicketCache.getExpiresIn())) {
            logger.info("==========缓存中token已获取时长为：" + (currentTime - tockenTicketCache.getAddTime()) + "毫秒，可以重新使用");
            return tockenTicketCache;
        }
        logger.info("==========缓存中token不存在或已过期===============");
        String requestUrl = access_token_url.replace("APPID", appId).replace("APPSECRET", appsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            logger.info("调用微信接口返回结果: {}", jsonObject.toString());
            token = new Token();
            // {"errcode":40164,"errmsg":"invalid ip 61.170.240.23, not in whitelist hint: [pRf6xa07743064]"}
            String accessTocken = jsonObject.getString("access_token");
            if(StringUtils.isBlank(accessTocken)){
                throw new ServiceException(jsonObject.getString("errmsg"));
            }
            token.setToken(accessTocken);
            // 正常过期时间是7200秒，此处设置3600秒读取一次
            token.setExpiresIn(jsonObject.getIntValue("expires_in") / 2);
            logger.info("==========tocket缓存过期时间为:" + token.getExpiresIn() + "毫秒");
            token.setAddTime(currentTime);
            updateToken(TOKEN, token);
        } else {
            logger.error("微信接口调用失败...");
            throw new ServiceException("微信接口调用失败...");
        }
        return token;
    }

    /**
     * 获取ticket
     *
     * @param token
     * @param currentTime
     * @return
     */
    private static Token getTicket(String token, long currentTime) {
        Token tockenTicketCache = getTokenTicket(TICKET);
        Token Token = null;
        if (tockenTicketCache != null
                && (currentTime - tockenTicketCache.getAddTime() <= tockenTicketCache.getExpiresIn())) {
            // 缓存中有ticket
            logger.info("==========缓存中ticket已获取时长为：" + (currentTime - tockenTicketCache.getAddTime()) + "毫秒，可以重新使用");
            return tockenTicketCache;
        }
        logger.info("==========缓存中ticket不存在或已过期===============");
        String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            Token = new Token();
            Token.setTicket(jsonObject.getString("ticket"));
            // 正常过期时间是7200秒，此处设置3600秒读取一次
            Token.setExpiresIn(jsonObject.getIntValue("expires_in") / 2);
            logger.info("==========ticket缓存过期时间为:" + Token.getExpiresIn() + "毫秒");
            Token.setAddTime(currentTime);
            updateToken(TICKET, Token);
        }
        return Token;
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    private static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        logger.info("requestUrl: {}", requestUrl);
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }
            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            logger.error("Weixin server connection timed out.");
        } catch (Exception e) {
            logger.error("https request error:{}" + e.getMessage());
        }
        return jsonObject;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {

        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    /**
     * 从缓存中读取token或者ticket
     *
     * @return
     */
    private static Token getTokenTicket(String key) {
        if (TOKEN_TICKET_CACHE != null && TOKEN_TICKET_CACHE.get(key) != null) {
            logger.info("==========从缓存中获取到了" + key + "成功===============");
            return TOKEN_TICKET_CACHE.get(key);
        }
        return null;
    }

    /**
     * 更新缓存中token或者ticket
     *
     * @return
     */
    private static void updateToken(String key, Token accessTocken) {
        if (TOKEN_TICKET_CACHE != null && TOKEN_TICKET_CACHE.get(key) != null) {
            TOKEN_TICKET_CACHE.remove(key);
            logger.info("==========从缓存中删除" + key + "成功===============");
        }
        TOKEN_TICKET_CACHE.put(key, accessTocken);
        // 更新缓存修改的时间
        cacheAddTime = String.valueOf(accessTocken.getAddTime());
        logger.info("==========更新缓存中" + key + "成功===============");
    }

    public static class Token {

        private String token;
        private String ticket;
        private int expiresIn;
        private long addTime;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
        }

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }
    }

    public static class TicketBean {
        private String timestamp;
        private String nonceStr;
        private String jsapi_ticket;
        private String signature;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getJsapi_ticket() {
            return jsapi_ticket;
        }

        public void setJsapi_ticket(String jsapi_ticket) {
            this.jsapi_ticket = jsapi_ticket;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }
}
