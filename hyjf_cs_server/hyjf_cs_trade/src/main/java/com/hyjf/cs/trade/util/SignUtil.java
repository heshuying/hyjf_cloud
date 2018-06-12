/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.util;

import com.hyjf.common.util.ApiSignUtil;
import com.hyjf.cs.trade.bean.BaseBean;
import com.hyjf.cs.trade.bean.assetpush.PushRequestBean;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

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

        if (Objects.equals("/push", methodName)) {
            // 资产推送--校验接口
            PushRequestBean requestBean = (PushRequestBean) paramBean;
            int assetType = requestBean.getAssetType();
            Long timestamp = requestBean.getTimestamp();
            sign = timestamp + instCode + assetType;
        }

        return ApiSignUtil.verifyByRSA(instCode, paramBean.getChkValue(), sign);
    }
}
