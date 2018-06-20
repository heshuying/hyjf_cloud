/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.authquery.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.user.bean.AutoStateQueryRequest;
import com.hyjf.cs.user.bean.AutoStateQueryResultBean;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.client.BankOpenClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.constants.AutoStateError;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.authquery.AutoStateQueryService;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.cs.user.util.ErrorCodeConstant;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingqing
 * @version AutoStateQueryServiceImpl, v0.1 2018/6/12 14:00
 */
@Service
public class AutoStateQueryServiceImpl extends BaseUserServiceImpl implements AutoStateQueryService {
    private static final Logger logger = LoggerFactory.getLogger(AutoStateQueryServiceImpl.class);

    @Autowired
    BankOpenClient bankOpenClient;

    @Autowired
    AmUserClient amUserClient;

    @Autowired
    AutoPlusService autoPlusService;

    @Override
    public AutoStateQueryResultBean queryStatus(AutoStateQueryRequest autoStateQuery) {
        AutoStateQueryResultBean resultBean = new AutoStateQueryResultBean();
        String channel = BankCallConstant.CHANNEL_PC;
        // 电子账户号
        String accountId = autoStateQuery.getAccountId();

        // 验证请求参数
        // 机构编号
        if (autoStateQuery.checkParmIsNull()) {
            logger.info("请求参数非法");
            throw new ReturnMessageException(AutoStateError.PARAM_ERROR);
        }
        // 验签  accountId
        if (!this.verifyRequestSign(autoStateQuery, BaseDefine.METHOD_BORROW_AUTH_STATE)) {
            logger.info("----验签失败----");
            throw new ReturnMessageException(AutoStateError.CHECK_ERROR);
        }
        // 用户ID
        // 根据电子账户号查询用户ID
        BankOpenAccountVO bankOpenAccount = bankOpenClient.selectByAccountId(accountId);
        if (bankOpenAccount == null) {
            logger.info("查询用户开户信息失败,用户电子账户号:[" + accountId + "]");
            throw new ReturnMessageException(AutoStateError.CHECK_USER_INFO_ERROR);
        }
        Integer userId = bankOpenAccount.getUserId();
        UserVO user = amUserClient.findUserById(userId);
        if (user == null) {
            logger.info("查询用户失败:[" + userId + "].");
            throw new ReturnMessageException(AutoStateError.GET_USER_ERROR);
        }
        Integer passwordFlag = user.getIsSetPassword();
        // 未设置交易密码
        if (passwordFlag != 1) {
            logger.info("-------------------未设置交易密码！"+autoStateQuery.getAccountId()+"！--------------------status"+user.getIsSetPassword());
            throw new ReturnMessageException(AutoStateError.NOT_PASSWD_ERROR);
        }
        BankCallBean retBean=autoPlusService.getTermsAuthQuery(userId,channel);
        logger.info("调用江西银行授权状态查询接口:"+(retBean==null?"空":retBean.getPaymentAuth()));
        if(retBean==null){
            logger.info("银行返回为空,accountId:["+accountId+"]");
            throw new ReturnMessageException(AutoStateError.AUTH_ERROR);
        }
        String retCode = retBean.getRetCode();
        if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
            logger.info("授权状态查询接口失败,accountId:["+accountId+"]返回码["+retCode+"]！");
            throw new ReturnMessageException(AutoStateError.AUTH_ERROR);
        }
        resultBean = getResultJosn(resultBean,retBean);
        logger.info("授权状态查询第三方返回参数："+ JSONObject.toJSONString(resultBean));
        resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
        resultBean.setStatusDesc("授权状态查询成功");
        return resultBean;
    }


    /**
     * @Author: zhangqingqing
     * @Desc : 拼接返回参数
     * @Param: * @param resultBean
     * @param retBean
     * @Date: 18:31 2018/6/12
     * @Return: AutoStateQueryResultBean
     */
    private AutoStateQueryResultBean getResultJosn(AutoStateQueryResultBean resultBean, BankCallBean retBean) {
        resultBean.setAccountId(retBean.getAccountId());
        resultBean.setAgreeWithdrawStatus(retBean.getAgreeWithdraw());
        resultBean.setAutoBidDeadline(retBean.getAutoBidDeadline());
        resultBean.setAutoBidStatus(retBean.getAutoBid());
        resultBean.setAutoTransferStatus(retBean.getAutoTransfer());
        resultBean.setPaymentAuthStatus(retBean.getPaymentAuth());
        resultBean.setPaymentDeadline(retBean.getPaymentDeadline());
        resultBean.setRepayAuthStatus(retBean.getRepayAuth());
        resultBean.setRepayDeadline(retBean.getRepayDeadline());
        return resultBean;
    }
}
