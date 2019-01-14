package com.hyjf.cs.market.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 17:06
 * @Description: SystemConfig
 */
@Component
public class SystemConfig {

    @Value("${hyjf.wechat.qrcode.url}")
    private String wechatQrcodeUrl;

    @Value("${hyjf.online.type}")
    private String hyjfOnlineType;

    @Value("${hyjf.cdn.domain.url}")
    private String hyjfCdnDomainUrl;

    public String getWechatQrcodeUrl() {
        return wechatQrcodeUrl;
    }

    public void setWechatQrcodeUrl(String wechatQrcodeUrl) {
        this.wechatQrcodeUrl = wechatQrcodeUrl;
    }

    public String getHyjfOnlineType() {
        return hyjfOnlineType;
    }

    public void setHyjfOnlineType(String hyjfOnlineType) {
        this.hyjfOnlineType = hyjfOnlineType;
    }

    public String getHyjfCdnDomainUrl() {
        return hyjfCdnDomainUrl;
    }

    public void setHyjfCdnDomainUrl(String hyjfCdnDomainUrl) {
        this.hyjfCdnDomainUrl = hyjfCdnDomainUrl;
    }
}
