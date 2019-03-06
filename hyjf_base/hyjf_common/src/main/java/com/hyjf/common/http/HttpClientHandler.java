package com.hyjf.common.http;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientHandler {

    /**
     * MAP类型数组转换成NameValuePair类型
     * 
     */
    public static List<NameValuePair> buildNameValuePair(Map<String, String> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        return nvps;
    }

//    public static String getBase64Encode(String str) {
//        if (str == null || "".equals(str)) {
//            return "";
//        }
//        try {
//            byte[] bt = str.getBytes("UTF-8");
//            str = String.valueOf(Base64.encode(bt));
//        } catch (UnsupportedEncodingException e) {
//            logger.error(e.getMessage());
//        }
//        return str;
//    }
//
//    public static String getBase64Decode(String str) {
//        if (str == null || "".equals(str)) {
//            return "";
//        }
//        char[] ch = str.toCharArray();
//        byte[] bt = Base64.decode(ch);
//        try {
//            str = new String(bt, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            logger.error(e.getMessage());
//        }
//        return str;
//    }

}
