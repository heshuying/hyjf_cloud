package com.hyjf.wbs.common;

import com.hyjf.common.spring.SpringUtils;
import com.hyjf.wbs.configs.EntidsProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
     *
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
     *
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

    /**
     * 获取所有启用的utmid list
     *
     * @return
     */
    public List<Integer> getAllUtmId() {
        List<Integer> utmId = new ArrayList<Integer>();
        for (Map.Entry<String, String> entry : flags.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            if ("1".equals(mapValue)) {
                if (entids.get(mapKey) != null&&!"".equals(entids.get(mapKey))) {
                    if (entids.get(mapKey).indexOf(",") != -1) {
                        String[] strarray = entids.get(mapKey).split(",");
                        for (String str : strarray) {
                            utmId.add(Integer.valueOf(str));
                        }

                    } else {
                        utmId.add(Integer.valueOf(entids.get(mapKey)));
                    }
                }


            }
        }

        return utmId;
    }
}
