package com.hyjf.admin.common.util;

import cn.emay.sdk.client.api.Client;
import com.hyjf.common.util.CustomConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

public class SmsUtil {

    @Value("${hyjf.env.test}")
    private static String EnvTest;

    private static Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    public static final boolean ENV_TEST = "true".equals(EnvTest) ? true : false;

    private static final String TITLE = "【汇盈金服】";

    private static final String SUFFIX = " 回复TD退订";

    /** 软件序列号 */
    private static final String SOFT_SERIALNO = "9SDK-EMY-0999-JBVLP";

    /** 自定义关键字(key值) */
    private static final String KEY = "286141";

    /** 软件序列号 */
    private static final String SOFT_SERIALNO_MAKETING = "6SDK-EMY-6688-JCQTL";

    /** 自定义关键字(key值) */
    private static final String KEY_MAKETING = "756526";

    private static final String URL = "http://sh999.eucp.b2m.cn:8080/sdkproxy/sendsms.action";

    private static final String URL_MAKETING = "http://sdk4rptws.eucp.b2m.cn:8080/sdkproxy/sendsms.action";

    /**
     * http请求参数集合
     */
    private static Map<String, String> parmMap = null;

    /**
     * http请求参数集合
     */
    private static Map<String, String> parmMapMaketing = null;

    /**
     * Client,单例
     */
    private static Client client = null;

    private SmsUtil() {
    }

    /**
     * 获取Client
     *
     * @return
     * @throws Exception
     */
    public static Client getClient() throws Exception {
        if (client == null) {
            syncInit();
        }
        return client;
    }

    private static synchronized void syncInit() {
        if (client == null) {
            try {
                client = new Client(SOFT_SERIALNO, KEY);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * 获取参数集合
     *
     * @return
     * @throws Exception
     */
    public static Map<String, String> getParmMap(String channelType) throws Exception {
        if (parmMap == null || parmMapMaketing == null) {
            syncInitParmMap();
        }
        if (CustomConstants.CHANNEL_TYPE_NORMAL.equals(channelType)) {
            return parmMap;
        } else {
            return parmMapMaketing;
        }
    }

    private static synchronized void syncInitParmMap() {
        if (parmMap == null || parmMapMaketing == null) {
            try {
                parmMap = new HashMap<String, String>();
                parmMap.put("cdkey", SOFT_SERIALNO);
                parmMap.put("password", KEY);
                parmMapMaketing = new HashMap<String, String>();
                parmMapMaketing.put("cdkey", SOFT_SERIALNO_MAKETING);
                parmMapMaketing.put("password", KEY_MAKETING);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

}
