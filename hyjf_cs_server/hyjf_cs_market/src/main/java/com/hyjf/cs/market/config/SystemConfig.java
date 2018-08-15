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

    public String getWechatQrcodeUrl() {
        return wechatQrcodeUrl;
    }

    public void setWechatQrcodeUrl(String wechatQrcodeUrl) {
        this.wechatQrcodeUrl = wechatQrcodeUrl;
    }
}
