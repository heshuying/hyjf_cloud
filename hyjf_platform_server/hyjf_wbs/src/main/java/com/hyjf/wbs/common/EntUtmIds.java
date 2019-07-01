package com.hyjf.wbs.common;

import com.hyjf.common.spring.SpringUtils;
import com.hyjf.wbs.configs.EntidsProperties;

import java.util.Arrays;
import java.util.Map;

/**
 * @Auther: wxd
 * @Date: 2019-07-01 11:04
 * @Description:财富端id和平台utmid对应属性
 */
public class EntUtmIds {

    private final EntidsProperties entidsProperties;
    private Map<String, String> entids;
    private Map<String, String> flags;

    public EntUtmIds() {

        entidsProperties = SpringUtils.getBean(EntidsProperties.class);
        this.entids = entidsProperties.getEntids();
        this.flags = entidsProperties.getFlags();
    }

    /**
     * 根据utmId获取entid
     * @param utmid
     * @return
     */
    public String getEntId(String utmid) {
        for (Map.Entry<String, String> entry : entids.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            if (mapValue.indexOf(",") != -1) {

                String[] strarray = mapValue.split(",");
                if (Arrays.asList(strarray).contains(utmid)) {
                    return mapKey;
                }

            } else if (mapValue.equals(utmid)) {
                return mapKey;

            }
        }
        return null;
    }

    /**
     * 根据entid获取utmid
     * @param entid
     * @return
     */
    public String getUtmId(String entid) {
        for (Map.Entry<String, String> entry : entids.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            if (mapKey.equals(entid)) {
                return mapValue;
            }
        }
        return null;
    }
}
