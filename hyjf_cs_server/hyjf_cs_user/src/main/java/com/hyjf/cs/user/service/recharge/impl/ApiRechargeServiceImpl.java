/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.recharge.impl;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.resquest.user.BankSmsLogRequest;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.ApiUserRechargeRequestBean;
import com.hyjf.cs.user.bean.ApiUserRechargeResultBean;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.recharge.ApiRechargeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: sunpeikai
 * @version: ApiRechargeServiceImpl, v0.1 2018/8/28 10:40
 */
@Service
public class ApiRechargeServiceImpl extends BaseUserServiceImpl implements ApiRechargeService {

    @Autowired
    private SystemConfig systemConfig;

    /**
     * 短信充值发送短信验证码
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public ApiUserRechargeResultBean sendSms(ApiUserRechargeRequestBean requestBean) {
        ApiUserRechargeResultBean resultBean = new ApiUserRechargeResultBean();
        // 参数校验
        this.checkParams(requestBean);
        try {
            // 银行预留手机号
            String mobile = requestBean.getMobile();
            // 银行卡号
            String cardNo = requestBean.getCardNo();
            // 银行电子账户号
            String accountId = requestBean.getAccountId();
            // 渠道
            String channel = requestBean.getChannel();

            // 根据用户电子账户号查询用户信息
            BankOpenAccountVO bankOpenAccount = amUserClient.selectByAccountId(accountId);
            if (bankOpenAccount == null) {
                logger.info("查询用户开户信息失败,用户电子账户号:[" + accountId + "]");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("短信验证码发送失败");
                return resultBean;
            }
            // 用户ID
            Integer userId = bankOpenAccount.getUserId();
            // 根据用户ID查询用户信息
            UserVO user = amUserClient.findUserById(userId);
            if (user == null) {
                logger.info("根据用户ID查询用户信息失败,用户电子账户号:[" + accountId + "],用户ID:[" + userId + "].");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("短信验证码发送失败");
                return resultBean;
            }
            // 根据用户ID查询用户平台银行卡信息
            BankCardVO bankCard = amUserClient.getBankCardByUserId(userId);
            if (bankCard == null) {
                logger.info("根据用户ID查询用户银行卡信息失败,用户电子账户号:[" + accountId + "],用户ID:[" + userId + "].");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("短信验证码发送失败");
                return resultBean;
            }
            // 用户汇盈平台的银行卡卡号
            String localCardNo = bankCard.getCardNo() == null ? "" : bankCard.getCardNo();
            // 如果两边银行卡号不一致
            if (!cardNo.equals(localCardNo)) {
                logger.info("用户银行卡信息不一致,用户电子账户号:[" + accountId + "],请求银行卡号:[" + cardNo + "],平台保存的银行卡号:[" + localCardNo + "].");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("短信验证码发送失败");
                return resultBean;
            }
            // 调用江西银行发送短信接口
            BankCallBean bankResultBean = this.sendRechargeOnlineSms(userId, cardNo, mobile, channel);
            if (Validator.isNull(bankResultBean)) {
                logger.info("调用短信发送接口失败,用户电子账户号:[" + accountId + "],请求银行卡号:[" + cardNo + "],平台保存的银行卡号:[" + localCardNo + "].");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("短信验证码发送失败");
                return resultBean;
            }
            // 短信发送返回结果码
            String retCode = bankResultBean.getRetCode();
            if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode) && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
                logger.info("调用短信发送接口失败,用户电子账户号:[" + accountId + "],请求银行卡号:[" + cardNo + "],平台保存的银行卡号:[" + localCardNo + "],银行返回结果码:[" + retCode + "]");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("短信验证码发送失败");
                return resultBean;
            }
            if (Validator.isNull(bankResultBean.getSrvAuthCode()) && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
                logger.info("调用短信发送接口失败,前导业务授权码为空,用户电子账户号:[" + accountId + "],用户ID:[" + userId + "].");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("短信验证码发送失败");
                return resultBean;
            }
            logger.info("短信验证码发送成功");
            resultBean.setStatus(BaseResult.SUCCESS);
            resultBean.setStatusDesc("短信验证码发送成功");
            return resultBean;
        } catch (Exception e) {
            logger.info("调用短信发送接口发生异常");
            resultBean.setStatus(BaseResult.FAIL);
            resultBean.setStatusDesc("短信验证码发送失败");
            return resultBean;
        }
    }

    private void checkParams(ApiUserRechargeRequestBean requestBean){
        // 银行预留手机号
        String mobile = requestBean.getMobile();
        // 银行卡号
        String cardNo = requestBean.getCardNo();
        // 银行电子账户号
        String accountId = requestBean.getAccountId();
        // 渠道
        String channel = requestBean.getChannel();
        // 机构编号
        String instCode = requestBean.getInstCode();
        // 验证请求参数
        // 手机号
        CheckUtil.check(Validator.isNull(mobile),MsgEnum.ERR_OBJECT_REQUIRED,"银行预留手机号");
        // 银行卡号
        CheckUtil.check(Validator.isNull(cardNo),MsgEnum.ERR_OBJECT_REQUIRED,"银行卡号");
        // 银行电子账户号
        CheckUtil.check(Validator.isNull(accountId),MsgEnum.ERR_OBJECT_REQUIRED,"银行电子账户号");
        // 渠道
        CheckUtil.check(Validator.isNull(channel),MsgEnum.ERR_OBJECT_REQUIRED,"渠道");
        // 机构编号
        CheckUtil.check(Validator.isNull(instCode),MsgEnum.ERR_OBJECT_REQUIRED,"机构编号");
        // 验签
        CheckUtil.check(!this.verifyRequestSign(requestBean, BaseDefine.METHOD_SERVER_SEND_RECHARGE_SMS),MsgEnum.ERR_SIGN);
        // 手机号合法性校验
        CheckUtil.check(!Validator.isMobile(mobile),MsgEnum.ERR_MOBILE_IS_NOT_REAL);
    }

    /**
     * 调用银行发送短信接口
     *
     * @param userId
     * @param cardNo
     * @param mobile
     * @param channel
     * @return
     */
    private BankCallBean sendRechargeOnlineSms(Integer userId, String cardNo, String mobile, String channel) {
        // 调用存管接口发送验证码
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallMethodConstant.TXCODE_SMSCODE_APPLY);// 交易代码cardBind
        bean.setInstCode(systemConfig.bankInstcode);// 机构代码
        bean.setBankCode(systemConfig.bankCode);// 银行代码
        bean.setTxDate(GetOrderIdUtils.getOrderDate());// 交易日期
        bean.setTxTime(GetOrderIdUtils.getOrderTime());// 交易时间
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
        bean.setChannel(channel);// 交易渠道000001手机APP 000002网页
        bean.setMobile(mobile);
        // 当reqType=2时必填
        String srvTxCode = "directRechargeOnline";
        bean.setReqType("2");
        bean.setSrvTxCode(srvTxCode); // directRechargeOnline
        bean.setCardNo(cardNo); // 绑定的银行卡号
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));// 订单号
        bean.setLogUserId(String.valueOf(userId));// 请求用户名
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        bean.setLogRemark("短信充值发送短信验证码");
        try {
            BankCallBean mobileBean = BankCallUtils.callApiBg(bean);
            if (Validator.isNull(mobileBean)) {
                return null;
            }
            // 短信发送返回结果码
            String retCode = mobileBean.getRetCode();
            if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode) && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
                return null;
            }
            if (Validator.isNull(mobileBean.getSrvAuthCode()) && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
                return null;
            }
            // 业务授权码
            String srvAuthCode = mobileBean.getSrvAuthCode();
            String smsSeq = mobileBean.getSmsSeq();
            // 组装请求参数
            BankSmsLogRequest request = new BankSmsLogRequest();
            request.setUserId(userId);
            request.setSrvTxCode(srvTxCode);
            request.setSrvAuthCode(srvAuthCode);
            request.setSmsSeq(smsSeq);

            if (Validator.isNotNull(mobileBean.getSrvAuthCode())) {
                boolean smsFlag = amUserClient.updateBankSmsLog(request);
                if (smsFlag) {
                    return mobileBean;
                } else {
                    return null;
                }
            } else {
                // 保存用户开户日志
                srvAuthCode = amUserClient.selectBankSmsLog(request);
                if (Validator.isNull(srvAuthCode)) {
                    return null;
                } else {
                    mobileBean.setSrvAuthCode(srvAuthCode);
                    mobileBean.setSmsSeq(smsSeq);
                    return mobileBean;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
