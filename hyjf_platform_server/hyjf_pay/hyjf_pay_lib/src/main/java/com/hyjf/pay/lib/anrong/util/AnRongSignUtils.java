package com.hyjf.pay.lib.anrong.util;

import java.io.Serializable;

/**
 * 
 * 安融签名用类
 * @author hyjf
 * @version hyjf 1.0
 * @since hyjf 1.0 2017年10月10日
 * @see 上午9:58:57
 */
public class AnRongSignUtils implements Serializable {

    private static final long serialVersionUID = -8087919134826406706L;

    /** MD5签名类型 **/
    public static final String SIGN_TYPE_MD5 = "M";

    /** RSA签名类型 **/
    public static final String SIGN_TYPE_RSA = "R";

    /** RSA验证签名成功结果 **/
    public static final int RAS_VERIFY_SIGN_SUCCESS = 0;

}
