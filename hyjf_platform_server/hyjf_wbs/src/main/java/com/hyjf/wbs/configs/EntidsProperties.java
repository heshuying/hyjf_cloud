package com.hyjf.wbs.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: wxd
 * @Date: 2019-07-01 10:22
 * @Description:
 */
@ConfigurationProperties(prefix = "hyjf.qudao")
@Component
public class EntidsProperties {
    private Map<String, String> entids = new HashMap<>();
    private Map<String, String> flags = new HashMap<>();
    public Map<String, String> getEntids() {
        return entids;
    }

    public void setEntids(Map<String, String> entids) {
        this.entids = entids;
    }

    public Map<String, String> getFlags() {
        return flags;
    }

    public void setFlags(Map<String, String> flags) {
        this.flags = flags;
    }
}
