package com.hyjf.pay.lib.bank.util;

import com.hyjf.common.util.PropUtils;
import com.hyjf.common.util.RSAHelper;
import com.hyjf.common.util.RSAKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by cuigq on 2018/3/6.
 */
public class BankCallSignUtils_ {

    private static Logger logger = LoggerFactory.getLogger(BankCallSignUtils_.class.getName());

    /**
     * 商户公钥文件地址
     **/
    public static final String BANK_PUB_KEY_PATH = PropUtils.getSystem(BankCallConstant.BANK_PUB_KEY_PATH);

    /**
     * 商户私钥文件地址
     **/
    public static final String BANK_PRI_KEY_PATH = PropUtils.getSystem(BankCallConstant.BANK_PRI_KEY_PATH);

    /**
     * 商户私钥文件地址
     **/
    public static final String BANK_PRI_KEY_PASS = PropUtils.getSystem(BankCallConstant.BANK_PRI_KEY_PASS);


    /**
     * 组建报文的待签名字符串或者待验签字符串
     *
     * @param map
     * @return
     */
    public static String mergeMap(Map<String, String> map) {
        // 字典序排序后生成待签名字符串
        Map<String, Object> reqMap = new TreeMap<String, Object>(map);
        logger.debug("待签名对象信息=【" + reqMap + "】");

        StringBuffer buff = new StringBuffer();

        Iterator<Map.Entry<String, Object>> iter = reqMap.entrySet().iterator();
        Map.Entry<String, Object> entry;
        while (iter.hasNext()) {
            entry = iter.next();
            if (!"sign".equals(entry.getKey())) {
                if (entry.getValue() == null) {
                    entry.setValue("");
                    buff.append("");
                } else {
                    buff.append(entry.getValue());
                }
            }
        }

        String requestMerged = buff.toString();
        logger.debug("待签名字符串：" + requestMerged);
        return requestMerged;
    }

    /**
     * 对请求报文的待签名字符串进行签名
     *
     * @param signStr 待签名字符串
     * @return
     * @throws Exception
     */
    public static String sign(String signStr) {

        logger.debug((new StringBuilder()).append("待签名字符串：signStr=")
                .append(signStr).toString());
        String sign = null;
        try {
            RSAKeyUtil rsaKey = new RSAKeyUtil(new File(BANK_PRI_KEY_PATH), BANK_PRI_KEY_PASS);
            RSAHelper signer = new RSAHelper(rsaKey.getPrivateKey());

            sign = signer.sign(signStr);
        } catch (Exception e) {
            logger.error("获取签名异常", e.getMessage());
            e.printStackTrace();
        }
        logger.debug((new StringBuilder()).append("签名：sign=").append(sign)
                .toString());
        return sign;
    }

    /**
     * 对响应报文组成的待验签字符串进行验证签名
     *
     * @param signText 待验的签名串
     * @param dataText 待签名字符串
     * @return
     */
    public static boolean verify(String signText, String dataText) {
        signText = signText.replaceAll("[\\t\\n\\r]", "");
        logger.debug((new StringBuilder()).append("待签名字符串：signStr=")
                .append(dataText).toString());
        logger.debug((new StringBuilder()).append("签名：sign=").append(signText)
                .toString());

        boolean b = false;
        try {
            RSAKeyUtil ru = new RSAKeyUtil(new File(BANK_PUB_KEY_PATH));
            RSAHelper signHelper = new RSAHelper(ru.getPublicKey());
            b = signHelper.verify(dataText, signText);
        } catch (Exception e) {
            logger.error("签名校验异常", e.getMessage());
        }
        logger.debug((new StringBuilder()).append("验证平台签名是否成功").append(b)
                .toString());
        return b;
    }


}
