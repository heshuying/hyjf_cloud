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

    private static final  EntidsProperties entidsProperties;
    private static Map<String, String> entids;
    private static Map<String, String> flags;

    static{

        entidsProperties = SpringUtils.getBean(EntidsProperties.class);
        entids = entidsProperties.getEntids();
        flags = entidsProperties.getFlags();
    }

    /**
     * 根据utmId获取entid
     *
     * @param utmid
     * @return
     */
    public static String getEntId(String utmid) {
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
    public static String getUtmId(String entid) {
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
    public static List<Integer> getAllUtmId() {
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
