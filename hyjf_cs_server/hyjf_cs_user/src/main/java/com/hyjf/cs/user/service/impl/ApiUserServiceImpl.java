/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.impl;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthLogVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.user.beans.AutoPlusRequestBean;
import com.hyjf.cs.user.client.AmBankOpenClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.ApiUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangqingqing
 * @version ApiUserServiceImpl, v0.1 2018/5/30 17:45
 */
public class ApiUserServiceImpl implements ApiUserService {

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmBankOpenClient amBankOpenClient;

    @Autowired
    SystemConfig systemConfig;

    @Override
    public BankCallBean apiUserAuth(String type, String smsSeq, AutoPlusRequestBean payRequestBean) {
        BankOpenAccountVO bankOpenAccount = this.amBankOpenClient.selectByAccountId(payRequestBean.getAccountId());
        UserVO user= amUserClient.findUserById(bankOpenAccount.getUserId());
        Integer userId = user.getUserId();
        // 同步调用路径
        String retUrl = systemConfig.getWebHost()
                + "/server/autoPlus/userAuthInvesReturn?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getRetUrl().replace("#", "*-*-*");
        // 异步调用路
        String bgRetUrl =systemConfig.getWebHost()
                + "/server/autoPlus/userAuthInvesBgreturn?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getNotifyUrl().replace("#", "*-*-*");
        // 组装发往江西银行参数
        BankCallBean bean = getCommonBankCallBean(payRequestBean.getAccountId(),userId,type,payRequestBean.getChannel(),smsSeq,payRequestBean.getSmsCode());
        bean.setRetUrl(retUrl);
        bean.setNotifyUrl(bgRetUrl);
        // 插入日志
        this.insertUserAuthLog(user, bean,1, BankCallConstant.QUERY_TYPE_1);
        return bean;
    }

    /**
     * 组装发往江西银行参数
     * @param accountId
     * @param userid
     * @param type
     * @param channel
     * @param lastSrvAuthCode
     * @param smsCode
     * @return
     */
    private BankCallBean getCommonBankCallBean(String accountId, Integer userid, String type, String channel, String lastSrvAuthCode, String smsCode) {
        String remark = "";
        String txcode = "";
        // 构造函数已经设置
        // 版本号  交易代码  机构代码  银行代码  交易日期  交易时间  交易流水号   交易渠道
        BankCallBean bean = new BankCallBean(BankCallConstant.VERSION_10,txcode,userid,channel);
        if(BankCallConstant.QUERY_TYPE_1.equals(type)){
            remark = "投资人自动投标签约增强";
            bean.setTxCode(BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS);
            bean.setDeadline(GetDate.date2Str(GetDate.countDate(1,5),new SimpleDateFormat("yyyyMMdd")));
            bean.setTxAmount("1000000");
            bean.setTotAmount("1000000000");
        } else if(BankCallConstant.QUERY_TYPE_2.equals(type)){
            remark = "投资人自动债权转让签约增强";
            bean.setTxCode(BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS);
        }
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_PLUS);
        bean.setOrderId(bean.getLogOrderId());
        bean.setAccountId(accountId);
        bean.setForgotPwdUrl(CustomConstants.FORGET_PASSWORD_URL);
        bean.setLastSrvAuthCode(lastSrvAuthCode);
        bean.setSmsCode(smsCode);
        bean.setLogRemark(remark);

        return bean;
    }

    /**
     *插入日志
     * @param user
     * @param bean
     * @param client
     * @param authType
     */
    public void insertUserAuthLog(UserVO user, BankCallBean bean, Integer client, String authType) {
        Date nowTime=GetDate.getNowTime();
        HjhUserAuthLogVO hjhUserAuthLog=new HjhUserAuthLogVO();
        hjhUserAuthLog.setUserId(user.getUserId());
        hjhUserAuthLog.setUserName(user.getUsername());
        hjhUserAuthLog.setOrderId(bean.getLogOrderId());
        hjhUserAuthLog.setOrderStatus(2);
        if(authType!=null&&authType.equals(BankCallConstant.QUERY_TYPE_2)){
            hjhUserAuthLog.setAuthType(4);
        }else{
            hjhUserAuthLog.setAuthType(Integer.valueOf(authType));
        }
        hjhUserAuthLog.setOperateEsb(client);
        hjhUserAuthLog.setCreateUser(user.getUserId());
        hjhUserAuthLog.setCreateTime(nowTime);
        hjhUserAuthLog.setUpdateTime(nowTime);
        hjhUserAuthLog.setUpdateUser(user.getUserId());
        hjhUserAuthLog.setDelFlg(0);
        amUserClient.insertUserAuthLog(hjhUserAuthLog);
    }
}
