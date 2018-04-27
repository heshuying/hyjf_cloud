package com.hyjf.pay.lib.anrong.bean;

import java.io.Serializable;

/**
 * 
 * 安融相关常量
 * @author sss
 * @version hyjf 1.0
 * @since hyjf 1.0 2017年10月9日
 * @see 下午2:17:45
 */
public class AnRongConstant implements Serializable {

    private static final long serialVersionUID = -5608269557949177556L;
    // 配置文件的key值
    /** 安融会员号 */
    public static final String PARM_MEMBER_CODE = "hyjf.anrong.member";
    /** 会员秘钥 */
    public static final String PARM_SIGN_CODE = "hyjf.anrong.sign";
    /** 证书存在路径*/
    public static final String PARM_KEY_PATH_CODE = "hyjf.anrong.key.path";
    /** 安融用户查询接口请求路径*/
    public static final String PARM_REQ_QUERY_URL = "hyjf.anrong.req.query.url";
    /** 安融pay工程路径*/
    public static final String PARM_PAY_URL = "hyjf.anrong.pay.url";
    /** 安融用户共享接口请求路径*/
    public static final String PARM_REQ_SEND_URL = "hyjf.anrong.req.send.url";
    
}
