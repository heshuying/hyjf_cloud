/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.common.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author PC-LIUSHOUYI
 * @version SHA256Util, v0.1 2018/12/7 14:59
 */
public class SHA256Util {
    /**
     * 字符串sha256加密
     *
     * @param input
     * @return
     */
    public static String getSHA256(String input) {
        StringBuffer sb = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes(Charset.forName("UTF-8")));
            byte byteData[] = md.digest();
            sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
