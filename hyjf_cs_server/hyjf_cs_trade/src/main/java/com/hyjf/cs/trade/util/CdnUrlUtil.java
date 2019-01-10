package com.hyjf.cs.trade.util;

import com.hyjf.common.spring.SpringUtils;
import com.hyjf.cs.trade.config.SystemConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据配置获取不同的cdn链接
 * @author zhangyk
 * @date 2019/1/10 13:50
 */
public class CdnUrlUtil {

    private static Logger logger = LoggerFactory.getLogger(CdnUrlUtil.class);

    private static SystemConfig systemConfig = SpringUtils.getBean(SystemConfig.class);

    private static final String ONLINE_TYPE_1 = "1";  // 预生产环境
    private static final String ONLINE_TYPE_99 = "99";// 生产环境

    /**
     * 获取cdn域名链接
     * @author zhangyk
     * @date 2019/1/10 14:06
     */
    public static String getCdnUrl(){
        String cdnDomainUrl = "";
        String onlineType = systemConfig.getHyjfOnlineType();
        // 只有预生产环境和生产环境才会取cdn链接
        if (ONLINE_TYPE_99.equals(onlineType) || ONLINE_TYPE_1.equals(onlineType)){
            String cdnUrl = systemConfig.getHyjfCdnDomainUrl();
            cdnDomainUrl = StringUtils.isNotBlank(cdnUrl) ? (cdnUrl.substring(cdnUrl.length()-1).equals("/") ? cdnUrl.substring(0,cdnUrl.length()-1) : cdnUrl)  : "";
        }
        return cdnDomainUrl;
    }
}
