/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.util;

import com.hyjf.common.util.ApiSignUtil;
import com.hyjf.cs.user.bean.BaseBean;
import com.hyjf.cs.user.bean.PaymentAuthPageRequestBean;
import org.apache.commons.lang3.StringUtils;

/**
 * @author fuqiang
 * @version SignUtil, v0.1 2018/6/11 14:06
 */
public class SignUtil {

    /**
     * 验证外部请求签名
     *
     * @param paramBean
     * @param methodName
     * @return
     */
    public static boolean verifyRequestSign(BaseBean paramBean, String methodName) {
        String sign = StringUtils.EMPTY;

        // 机构编号必须参数
        String instCode = paramBean.getInstCode();
        if (StringUtils.isEmpty(instCode)) {
            return false;
        }
        if(methodName.equals("/server/user/paymentAuthPage/page")){
            // 缴费授权
            PaymentAuthPageRequestBean bean = (PaymentAuthPageRequestBean) paramBean;
            sign = bean.getInstCode() + bean.getAccountId()+bean.getRetUrl()+bean.getNotifyUrl() + bean.getTimestamp();
        }
        return ApiSignUtil.verifyByRSA(instCode, paramBean.getChkValue(), sign);
    }
}
