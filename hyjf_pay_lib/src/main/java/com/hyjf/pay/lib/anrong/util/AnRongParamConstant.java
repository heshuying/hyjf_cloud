package com.hyjf.pay.lib.anrong.util;

import java.io.Serializable;

/**
 * 
 * 安融接口相关常量
 * @author sss
 * @version hyjf 1.0
 * @since hyjf 1.0 2017年10月10日
 * @see 上午10:01:35
 */
public class AnRongParamConstant extends AnRongMethodConstant implements Serializable {

    private static final long serialVersionUID = 5138688304863608453L;

    // 参数
    public static final String PARAM_TXCODE = "txCode";
    // log参数
    /** 银行存管调用url */
    public static final String BANK_LOGBANKDETAIL_URL = "logBankDetailUrl";

    /** 订单号 */
    public static final String PARAM_LOGORDERID = "logOrderId";

    /** 登陆用户id */
    public static final String PARAM_LOGUSERID = "logUserId";

    /** 订单号 */
    public static final String PARAM_LOGNOTIFYURLTYPE = "logNotifyType";

    /** 异步回调地址1 */
    public static final String PARAM_LOGNOTIFYURL = "logNotifyURL";

    /** 异步回调地址2 */
    public static final String PARAM_LOGRETNOTIFYURL = "logRetNotifyURL";

    /** 订单日期 */
    public static final String PARAM_LOGORDERDATE = "logOrderDate";

    /** 商户私有域对象 */
    public static final String PARAM_LOGACQRESBEAN = "logAcqResBean";

    /** 响应描述 */
    public static final String PARAM_LOGRESPTYPE = "logRespType";

    /** MsgType */
    public static final String PARAM_LOGMSGTYPE = "logMsgType";

    /** MsgData */
    public static final String PARAM_LOGMSGDATA = "logMsgData";

    /** 平台 */
    public static final String PARAM_LOGCLIENT = "logClient";

    /** 备注 */
    public static final String PARAM_LOGREMARK = "logRemark";

    public static final String TXCODE_SEND_MESS = "sendMess";

}
