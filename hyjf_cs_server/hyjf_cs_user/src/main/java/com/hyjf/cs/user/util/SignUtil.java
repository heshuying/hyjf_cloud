/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.util;

import com.hyjf.cs.common.util.ApiSignUtil;
import com.hyjf.cs.user.bean.*;
import org.apache.commons.lang3.StringUtils;

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
        if (StringUtils.isEmpty(instCode)) {
            return false;
        }
        if (("/aems/authState/status").equals(methodName)) {
            //aems授权状态查询
            AemsAuthStatusQueryRequestBean bean = (AemsAuthStatusQueryRequestBean) paramBean;
            sign = bean.getInstCode() + bean.getAccountId() + bean.getTimestamp();
        }else if (("/aems/bindcardpage/bind").equals(methodName)){
            //aems用户页面绑卡
            AemsBindCardPageRequestBean bean = (AemsBindCardPageRequestBean) paramBean;
            sign = bean.getInstCode() + bean.getAccountId() + bean.getTimestamp();
        }else if ("/aems/unbindCardPage/deleteCardPage".equals(methodName)) {
            //aems解卡(页面调用)合规
            AemsUnbindCardPageRequestBean bean = (AemsUnbindCardPageRequestBean) paramBean;
            sign = bean.getInstCode()+ bean.getAccountId() + bean.getMobile() + bean.getCardNo()+bean.getTimestamp();
        }else if (("/aems/evaluation/saveUserEvaluationResults").equals(methodName)) {
            //aems用户风险测评
            AemsEvaluationRequestBean bean = (AemsEvaluationRequestBean) paramBean;
            sign = bean.getInstCode() + bean.getTimestamp();
        }else if (("/aems/company/syncCompanyInfo").equals(methodName)) {
            //aems获取集团组织架构信息
            AemsOrganizationStructureBean bean = (AemsOrganizationStructureBean) paramBean;
            sign = bean.getInstCode() + bean.getTimestamp();
        }else if (("/aems/syncUserInfo").equals(methodName)) {
            //aems用户信息查询
            AemsSyncUserInfoRequest bean = (AemsSyncUserInfoRequest) paramBean;
            sign = bean.getInstCode() + bean.getTimestamp();
        }else if (("/aems/transpassword/setPassword").equals(methodName)) {
            //aems设置&重置交易密码
            AemsTransPasswordRequestBean bean = (AemsTransPasswordRequestBean) paramBean;
            sign = bean.getChannel() + bean.getAccountId() + bean.getInstCode() + bean.getTimestamp();
        }else if (("/aems/encryptpage/open").equals(methodName)){
            // AEMS 用户开户
            AemsBankOpenEncryptPageRequestBean bean = (AemsBankOpenEncryptPageRequestBean) paramBean;
            sign = bean.getInstCode() + bean.getMobile() + bean.getTrueName() + bean.getRetUrl() + bean.getBgRetUrl() + bean.getTimestamp();
        }else if ("/aems/register/register".equals(methodName)){
            // AEMS 用户注册
            AemsUserRegisterRequestBean bean = (AemsUserRegisterRequestBean) paramBean;
            sign = bean.getMobile() + bean.getInstCode() + bean.getTimestamp();
        }else if ("/aems/mergeauth/mergeAuth/mergeAuth".equals(methodName)){
            // AEMS多合一授权
            AemsMergeAuthPagePlusRequestBean bean = (AemsMergeAuthPagePlusRequestBean) paramBean;
            sign = bean.getInstCode() + bean.getAccountId() + bean.getAuthType() + bean.getRetUrl() + bean.getNotifyUrl() + bean.getTimestamp();
        }else if ("/aems/bindAems".equals(methodName)){
            // Aems页面授权
            AemsUserPostRequsettBean bean = (AemsUserPostRequsettBean) paramBean;
            sign = bean.getChkValue()+  bean.getBindUniqueIdScy()+ bean.getPid()+ bean.getRetUrl()+ bean.getTimestamp();
        }else if ("/api/aems/thirdLogin".equals(methodName)){
            // Aems用戶自动登录
            AemsUserPostRequsettBean bean = (AemsUserPostRequsettBean) paramBean;
            sign = bean.getChkValue()+ bean.getBindUniqueIdScy()+ bean.getPid()+ bean.getRetUrl()+ bean.getTimestamp();
        }else if ("/aems/synbalance/synbalance".equals(methodName)){
            // Aems用户余额查询
            AemsSynBalanceRequestBean bean = (AemsSynBalanceRequestBean) paramBean;
            sign = bean.getAccountId() + bean.getTimestamp();
        }

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
        if(methodName.equals("/server/user/paymentAuthPage/page")){
            // 缴费授权
            PaymentAuthPageRequestBean bean = (PaymentAuthPageRequestBean) paramBean;
            sign = bean.getInstCode() + bean.getAccountId()+bean.getRetUrl()+bean.getNotifyUrl() + bean.getTimestamp();
        } else if (BaseDefine.METHOD_SERVER_TRUSTEE_PAY.equals(methodName)) {
            // 借款人受托支付申请
            TrusteePayRequestBean bean = (TrusteePayRequestBean) paramBean;
            sign = bean.getChannel() + bean.getAccountId() + bean.getProductId() + bean.getIdType() + bean.getIdNo()
                    + bean.getReceiptAccountId() + bean.getForgotPwdUrl() + bean.getRetUrl() + bean.getNotifyUrl()
                    + bean.getTimestamp();
        } else if (BaseDefine.METHOD_SERVER_TRUSTEE_PAY_QUERY.equals(methodName)) {
            // 借款人受托支付申请查询
            TrusteePayRequestBean bean = (TrusteePayRequestBean) paramBean;
            sign = bean.getChannel() + bean.getAccountId() + bean.getProductId() + bean.getTimestamp();
        } else if (methodName.equals("/server/repayAuth/repayAuth")) {
            // 还款授权
            PaymentAuthPageRequestBean bean = (PaymentAuthPageRequestBean) paramBean;
            sign = bean.getInstCode() + bean.getAccountId()+bean.getRetUrl()+bean.getNotifyUrl() + bean.getTimestamp();
        }else if(BaseDefine.METHOD_SERVER_TRUSTEE_PAY.equals(methodName)){
            //借款人受托支付申请
            TrusteePayRequestBean bean = (TrusteePayRequestBean) paramBean;
            sign = bean.getChannel() + bean.getAccountId() + bean.getProductId() + bean.getIdType() + bean.getIdNo()
                    + bean.getReceiptAccountId() + bean.getForgotPwdUrl() + bean.getRetUrl() + bean.getNotifyUrl()
                    + bean.getTimestamp();
        }
        return ApiSignUtil.verifyByRSA(instCode, paramBean.getChkValue(), sign);
    }
}
