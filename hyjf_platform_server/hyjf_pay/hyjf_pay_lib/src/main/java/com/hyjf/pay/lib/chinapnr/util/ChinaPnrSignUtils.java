/**
 * Description:汇付天下认证用类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: GOGTZ-T
 * @version: 1.0
 * Created at: 2015年11月23日 上午11:48:46
 * Modification History:
 * Modified by :
 */
package com.hyjf.pay.lib.chinapnr.util;

import java.io.Serializable;

import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.chinapnr.ChinaPnrApiImpl;

import chinapnr.SecureLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChinaPnrSignUtils implements Serializable {
    private static Logger log = LoggerFactory.getLogger(ChinaPnrSignUtils.class);
    /** serialVersionUID */
    private static final long serialVersionUID = 3640874934537168392L;

    /** THIS_CLASS */
    private static final String THIS_CLASS = ChinaPnrApiImpl.class.getName();

    /** MD5签名类型 **/
    public static final String SIGN_TYPE_MD5 = "M";

    /** RSA签名类型 **/
    public static final String SIGN_TYPE_RSA = "R";

    /** RSA验证签名成功结果 **/
    public static final int RAS_VERIFY_SIGN_SUCCESS = 0;

    /** 商户客户号 **/
    //   todo xiashuqing 20180615
    //public static final String RECV_MER_ID = ChinaPnrPropUtils.getSystem(ChinaPnrConstant.PROP_MERID);
    public static final String RECV_MER_ID = "";

    /** 商户公钥文件地址 **/
    //   todo xiashuqing 20180615
    //public static final String MER_PUB_KEY_PATH = ChinaPnrPropUtils.getSystem(ChinaPnrConstant.PROP_MER_PUB_KEY_PATH);
    public static final String MER_PUB_KEY_PATH = "";

    /** 商户私钥文件地址 **/
    //   todo xiashuqing 20180615
    //public static final String MER_PRI_KEY_PATH = ChinaPnrPropUtils.getSystem(ChinaPnrConstant.PROP_MER_PRI_KEY_PATH);
    public static final String MER_PRI_KEY_PATH = "";

    /**
     * RSA方式加签
     *
     * @param custId
     * @param forEncryptionStr
     * @param charset
     * @return
     * @throws Exception
     */
    public static String encryptByRSA(String forEncryptionStr) throws Exception {
        String methodName = "encryptByRSA";
        log.info("加签处理开始");
        if (Validator.isNull(forEncryptionStr)) {
            throw new Exception("加签内容不能为空!");
        }
        SecureLink sl = new SecureLink();
        log.debug("加签内容:" + forEncryptionStr);
        int result = sl.SignMsg(RECV_MER_ID, MER_PRI_KEY_PATH, forEncryptionStr.getBytes(StringPool.UTF8));
        if (result < 0) {
            // 打印日志
            throw new Exception("加签处理失败![result:" +result+"]");
        }
        log.debug("加签处理结束");
        return sl.getChkValue();
    }

    /**
     * RSA方式验签
     *
     * @param forEncryptionStr
     * @param chkValue
     * @return
     * @throws Exception
     */
    public static boolean verifyByRSA(String forEncryptionStr, String chkValue) throws Exception {
        String methodName = "verifyByRSA";
        log.info("检证处理开始");

        log.debug("检证内容:" + forEncryptionStr);
        int verifySignResult = -1;
        SecureLink sl = new SecureLink();
        try {
            verifySignResult = sl.VeriSignMsg(MER_PUB_KEY_PATH, forEncryptionStr, chkValue);
        } catch (Exception e) {
            log.error(String.valueOf(e));
            // 打印日志
            throw new Exception("检证处理失败!");
        }
        log.debug("检证处理结束");
        return verifySignResult == RAS_VERIFY_SIGN_SUCCESS;
    }
}
