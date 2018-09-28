/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.util;

import com.hyjf.common.util.ApiSignUtil;
import com.hyjf.cs.trade.bean.BaseBean;
import com.hyjf.cs.trade.bean.TransactionDetailsResultBean;
import com.hyjf.cs.trade.bean.api.AutoTenderRequestBean;
import com.hyjf.cs.trade.bean.assetpush.PushRequestBean;
import com.hyjf.cs.trade.bean.assetpush.SynBalanceRequestBean;
import com.hyjf.cs.trade.bean.assetpush.UserWithdrawRequestBean;
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
            // 个人资产推送--校验接口
            PushRequestBean requestBean = (PushRequestBean) paramBean;
            int assetType = requestBean.getAssetType();
            Long timestamp = requestBean.getTimestamp();
            sign = timestamp + instCode + assetType;
        } else if(Objects.equals("/synbalance", methodName)){
            SynBalanceRequestBean bean = (SynBalanceRequestBean) paramBean;
            sign =  bean.getAccountId()+bean.getInstCode() + bean.getTimestamp();
        } else if (Objects.equals("/pushcompany", methodName)) {
            // 企业资产推送--校验接口
            PushRequestBean bean = (PushRequestBean) paramBean;
            Long timestamp = bean.getTimestamp();
            Integer assetType = bean.getAssetType();
            sign = timestamp + instCode + assetType;
        } else if(Objects.equals("/tender", methodName)){
        	//自动投资
        	AutoTenderRequestBean bean = (AutoTenderRequestBean) paramBean;
        	sign = bean.getInstCode() + bean.getAccountId() + bean.getBorrowNid() + bean.getTimestamp();
        }else if (Objects.equals("/tradelist", methodName)){
            //同步交易明细
            TransactionDetailsResultBean bean = (TransactionDetailsResultBean) paramBean;
            //暂定四个参数
            sign = bean.getInstCode() + bean.getTimestamp() + bean.getPhone() + bean.getAccountId();
        }else if("/withdraw".equals(methodName)){
            // 用户提现
            UserWithdrawRequestBean bean = (UserWithdrawRequestBean)paramBean;
            sign = bean.getChannel() + bean.getAccountId() + bean.getAccount() + bean.getCardNo() + bean.getRetUrl() + bean.getBgRetUrl() + bean.getTimestamp();
        }
        return ApiSignUtil.verifyByRSA(instCode, paramBean.getChkValue(), sign);
    }
}
