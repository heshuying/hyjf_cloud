/**
 * Description:汇付天下认证用类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: GOGTZ-T
 * @version: 1.0
 * Created at: 2015年11月23日 上午11:48:46
 * Modification History:
 * Modified by :
 *//*

package com.hyjf.pay.lib.chinapnr.util;

import chinapnr.SecureLink;
import com.hyjf.common.util.MD5Util2;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class ChinaPnrSignUtils implements Serializable {
    private static Logger log = LoggerFactory.getLogger(ChinaPnrSignUtils.class);
    */
/** serialVersionUID *//*

    private static final long serialVersionUID = 3640874934537168392L;

    */
/** MD5签名类型 **//*

    public static final String SIGN_TYPE_MD5 = "M";

    */
/** RSA签名类型 **//*

    public static final String SIGN_TYPE_RSA = "R";

    */
/** RSA验证签名成功结果 **//*

    public static final int RAS_VERIFY_SIGN_SUCCESS = 0;

    //private static PaySystemConfig paySystemConfig = SpringUtils.getBean(PaySystemConfig.class);


    */
/**
     * RSA方式加签
     *
     * @param
     * @param forEncryptionStr
     * @param
     * @return
     * @throws Exception
     *//*

    public static String encryptByRSA(String forEncryptionStr) throws Exception {
        String methodName = "encryptByRSA";
        log.info("加签处理开始");
        if (Validator.isNull(forEncryptionStr)) {
            throw new Exception("加签内容不能为空!");
        }
        SecureLink sl = new SecureLink();
        log.debug("加签内容:" + forEncryptionStr);
        int result =sl.SignMsg("530022","/hyjfdata/data/testkey/MerPrK530022.key", forEncryptionStr.getBytes(StringPool.UTF8));
        //int result =sl.SignMsg(paySystemConfig.getChinapnrMerId(), paySystemConfig.getChinapnrPrikey(), forEncryptionStr.getBytes(StringPool.UTF8));

        if (result < 0) {
            // 打印日志
            throw new Exception("加签处理失败![result:" +result+"]");
        }
        log.debug("加签处理结束");
        return sl.getChkValue();
    }

    */
/**
     * RSA方式验签
     *
     * @param forEncryptionStr
     * @param chkValue
     * @return
     * @throws Exception
     *//*

    public static boolean verifyByRSA(String forEncryptionStr, String chkValue) throws Exception {
        log.info("检证处理开始");
        log.debug("检证内容:" + forEncryptionStr);
        int verifySignResult = -1;
        SecureLink sl = new SecureLink();
        try {
            verifySignResult =sl.VeriSignMsg("/hyjfdata/data/testkey/PgPubk.key", forEncryptionStr, chkValue);
            //verifySignResult =sl.VeriSignMsg(paySystemConfig.getChinapnrPubkey(), forEncryptionStr, chkValue);
        } catch (Exception e) {
            log.error(String.valueOf(e));
            // 打印日志
            throw new Exception("检证处理失败!");
        }
        log.debug("检证处理结束");
        return verifySignResult == RAS_VERIFY_SIGN_SUCCESS;
    }


    */
/**
     * 取得组合后的签名
     *
     * @param isEntrypt
     *            是否加签(true:加签,false:不加签)
     * @param keys
     * @return
     *//*

    public static String getChkValueMerged(Map<String,String> paramMap,boolean isEntrypt, String... keys) {
        String methodName = "getChkValueMerged";
        String chkValue = null;
        StringBuffer sb = new StringBuffer();
        if (paramMap != null && paramMap.size() > 0) {
            if (keys != null && keys.length > 0) {
                for (String key : keys) {
                    // 防止重复追加签名
                    if (ChinaPnrConstant.PARAM_CHKVALUE.equals(key) || !paramMap.containsKey(key)) {
                        continue;
                    }

                    // hbz ,RespExt要先decode解码后再拼接进去
                    if (!ChinaPnrConstant.PARAM_RESPEXT.equals(key)) {
                        sb.append(StringUtils.trimToEmpty(paramMap.get(key)));
                    } else {
                        try {
                            sb.append(StringUtils.trimToEmpty(URLDecoder.decode(paramMap.get(key), "UTF-8")));
                        } catch (UnsupportedEncodingException e) {
                            logger.error(e.getMessage());
                        }
                    }

                }
            } else {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    // 防止重复追加签名
                    if (ChinaPnrConstant.PARAM_CHKVALUE.equals(entry.getKey())) {
                        continue;
                    }
                    sb.append(StringUtils.trimToEmpty(entry.getValue()));
                }
            }

            try {
                // 加签
                if (isEntrypt) {
                    chkValue = ChinaPnrSignUtils.encryptByRSA(sb.toString());
                } else {
                    chkValue = sb.toString();
                }
            } catch (Exception e) {
                log.error(String.valueOf(e));
            }
        }
        return chkValue;
    }

    */
/**
     * 取得组合后的签名MD5
     *
     * @param isEntrypt
     *            是否加签(true:加签,false:不加签)
     * @param keys
     * @return
     *//*

    public static  String getChkValueMergedMD5(Map<String,String> paramMap, boolean isEntrypt, String... keys) {
        String methodName = "getChkValueMerged";
        String chkValue = null;
        StringBuffer sb = new StringBuffer();
        if (paramMap != null && paramMap.size() > 0) {
            if (keys != null && keys.length > 0) {
                for (String key : keys) {
                    // 防止重复追加签名
                    if (ChinaPnrConstant.PARAM_CHKVALUE.equals(key) || !paramMap.containsKey(key)) {
                        continue;
                    }

                    // hbz ,RespExt要先decode解码后再拼接进去
                    if (!ChinaPnrConstant.PARAM_RESPEXT.equals(key)) {
                        sb.append(StringUtils.trimToEmpty(paramMap.get(key)));
                    } else {
                        try {
                            sb.append(StringUtils.trimToEmpty(URLDecoder.decode(paramMap.get(key), "UTF-8")));
                        } catch (UnsupportedEncodingException e) {
                            logger.error(e.getMessage());
                        }
                    }

                }
            } else {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    // 防止重复追加签名
                    if (ChinaPnrConstant.PARAM_CHKVALUE.equals(entry.getKey())) {
                        continue;
                    }
                    sb.append(StringUtils.trimToEmpty(entry.getValue()));
                }
            }
            try {
                //进行MD5加密
                // 加签
                if (isEntrypt) {
                    chkValue = ChinaPnrSignUtils.encryptByRSA(MD5Util2.getMD5String(sb.toString()));
                } else {
                    chkValue = sb.toString();
                }
            } catch (Exception e) {
                log.error(String.valueOf(e));
            }
        }
        return chkValue;
    }
}
*/
