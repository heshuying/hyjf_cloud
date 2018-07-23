package com.hyjf.cs.trade.util;

import com.hyjf.common.util.RSAHelper;
import com.hyjf.common.util.RSAKeyUtil;
import com.hyjf.cs.trade.config.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class CheckSignUtil {
    private static Logger logger = LoggerFactory.getLogger(CheckSignUtil.class);

    @Autowired
    private SystemConfig systemConfig;


    /**
     * 请求数据加签
     *
     * @param mapText
     */
    public String encryptByRSA(Map<String, Object> mapText, String instCode) {
        try {
            String signText = getSignText(mapText);
            logger.info("待加签数据【" + signText + "】");

            RSAKeyUtil rsaKey = new RSAKeyUtil(new File(systemConfig.getHyjfReqPrimaryKeyPath() + instCode + ".p12"), systemConfig.getHyjfReqPasswordPath());
            RSAHelper signer = new RSAHelper(rsaKey.getPrivateKey());
            String sign = signer.sign(signText);
            return sign;
        } catch (Exception e) {
            logger.error("加签失败！" + e.getMessage(), e);
        }
        throw new IllegalArgumentException("加签失败！");

    }

    private static String getSignText(Map<String, Object> generalSignInfo) throws Exception {
        TreeMap<String, Object> treeMap = new TreeMap<>(generalSignInfo);

        StringBuffer buff = new StringBuffer();
        Iterator<Map.Entry<String, Object>> iter = treeMap.entrySet().iterator();
        Map.Entry<String, Object> entry;
        while (iter.hasNext()) {
            entry = iter.next();
            if (entry.getValue() == null) {
                entry.setValue("");
                buff.append("");
            } else {
                buff.append(String.valueOf(entry.getValue()));
            }
        }
        String requestMerged = buff.toString();
        return requestMerged.replaceAll("[\\t\\n\\r]", "");
    }
}
