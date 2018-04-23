package com.hyjf.common.util;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class UpdateNoticeUtils {
    /**
     * 
     * 校验app是否要提示更新
     * @author hsy
     * @param version
     * @param desc
     * @param requestUri
     * @param info
     * @return
     */
    public static boolean checkForAppUpdate(String version, String desc, String requestUri, JSONObject info){
        if(StringUtils.isEmpty(version)){
            info.put("status", "1");
            info.put("statusDesc", desc);
            info.put(CustomConstants.APP_REQUEST, requestUri);
            return true;
        }
        
        if(version.length()>=5){
            version = version.substring(0, 5);
        }
        
        if(version.compareTo("1.4.0")<=0){
            info.put("status", "1");
            info.put("statusDesc", desc);
            info.put(CustomConstants.APP_REQUEST, requestUri);
            return true;
        }

        return false;
    }
}
