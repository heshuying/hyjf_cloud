/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.util;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.common.util.ApiSignUtil;
import com.hyjf.cs.trade.bean.*;
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
     * AEMS验证外部请求签名
     * @author Zha Daojian
     * @date 2018/12/5 10:20
     * @param paramBean, methodName
     * @return boolean
     **/
    public static boolean AEMSVerifyRequestSign(BaseBean paramBean, String methodName) {
        String sign = org.apache.commons.lang.StringUtils.EMPTY;
        // 机构编号必须参数
        String instCode = paramBean.getInstCode();
        if (org.apache.commons.lang.StringUtils.isEmpty(instCode)) {
            return false;
        }
        if (("/aems/asset/status").equals(methodName)) {
            //aems资产查询接口
            AemsAssetStatusRequestBean bean = (AemsAssetStatusRequestBean) paramBean;
            sign = bean.getAssetId() + bean.getInstCode() + bean.getTimestamp();
        }else if (("/aems/authState/recharge").equals(methodName)) {
            //aems充值页面
            UserDirectRechargeRequestBean bean = (UserDirectRechargeRequestBean) paramBean;
            sign = bean.getInstCode() + bean.getAccountId() + bean.getMobile() + bean.getIdNo() + bean.getCardNo()
                    + bean.getTxAmount() + bean.getName() + bean.getRetUrl() + bean.getBgRetUrl() + bean.getTimestamp();
        } else if (("/aems/trusteePay/pay").equals(methodName)) {
            //aems借款人受托支付申请
            AemsTrusteePayRequestBean bean = (AemsTrusteePayRequestBean) paramBean;
            sign = bean.getChannel() + bean.getAccountId() + bean.getProductId() + bean.getIdType() + bean.getIdNo()
                    + bean.getReceiptAccountId() + bean.getForgotPwdUrl() + bean.getRetUrl() + bean.getNotifyUrl() + bean.getTimestamp();
        } else if (("/aems/trusteePay/trusteePayQuery").equals(methodName)) {
            //aems借款人受托支付申请查询
            AemsTrusteePayRequestBean bean = (AemsTrusteePayRequestBean) paramBean;
            sign = bean.getChannel() + bean.getAccountId() + bean.getProductId() + bean.getTimestamp();
        }
        // TODO AEMS验签修改
        return ApiSignUtil.verifyByRSA("AEMS", paramBean.getChkValue(), sign);
    }
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
        }else if("/server/user/directRechargePage/recharge".equals(methodName)){
            // 页面充值
            UserDirectRechargeRequestBean bean = (UserDirectRechargeRequestBean) paramBean;
            sign = bean.getInstCode() + bean.getAccountId() + bean.getMobile() + bean.getIdNo() + bean.getCardNo()
                    + bean.getTxAmount() + bean.getName() + bean.getRetUrl() + bean.getBgRetUrl() + bean.getTimestamp();
        } else if("/server/repayment/repaymentInfoList".equals(methodName)){
            // 还款明细查询
            ApiBorrowRepaymentInfoRequestBean bean = (ApiBorrowRepaymentInfoRequestBean) paramBean;
            sign = bean.getInstCode() + bean.getAssetId()+ bean.getTimestamp();
        }else if("/server/invest/repayList".equals(methodName)){
            // 获取回款记录
            ApiRepayListRequestBean bean = (ApiRepayListRequestBean) paramBean;
            sign = bean.getInstCode()+bean.getStartTime()+bean.getEndTime()+ bean.getTimestamp();
        }else if("/server/asset/status".equals(methodName)){
            // 资产状态查询
            AssetStatusRequestBean bean = (AssetStatusRequestBean) paramBean;
            sign = bean.getAssetId()+ bean.getTimestamp();
        }else if("/server/user/repay/getrepayresult".equals(methodName)){
            // 还款批次处理
            RepayRequestBean bean = (RepayRequestBean) paramBean;
            sign = bean.getChannel()+bean.getAccountId()+bean.getBorrowNid()+bean.getInstCode()+ bean.getTimestamp();
        }
        return ApiSignUtil.verifyByRSA(instCode, paramBean.getChkValue(), sign);
    }
}
