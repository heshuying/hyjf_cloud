/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.trans;

import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.bean.BankMobileModifyBean;
import com.hyjf.cs.user.result.MobileModifyResultBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;

import java.text.ParseException;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version MobileModifyService, v0.1 2018/6/14 16:47
 */
public interface MobileModifyService extends BaseUserService {
    /**
     * 更换手机号条件校验
     *
     * @param newMobile
     * @param smsCode
     * @return
     */
    boolean checkForMobileModify(String newMobile, String smsCode);

    /**
     * 用户手机号修改信息查询
     *
     * @param userId
     * @return
     */
    MobileModifyResultBean queryForMobileModify(Integer userId);

    /**
     * 更换手机号码验证（已开户）
     *
     * @param newMobile
     * @param smsCode
     * @param srvAuthCode
     * @return
     */
    boolean checkForMobileModifyOpened(String newMobile, String smsCode, String srvAuthCode);

    /**
     * 调用电子账号手机号修改增强
     *
     * @param userId
     * @param newMobile
     * @param smsCode
     * @param srvAuthCode
     * @return
     */
    BankCallBean callMobileModify(Integer userId, String newMobile, String smsCode, String srvAuthCode);

    /**
     * 发送同步CA认证信息修改MQ
     *
     * @param userId
     * @throws ParseException
     * @throws MQException
     */
    void updateUserCAMQ(int userId) throws ParseException, MQException;

    /**
     * 用户修改银行预留手机号
     *
     * @param bean
     * @param sign
     * @return
     */
    Map<String, Object> getBankMobileModify(BankMobileModifyBean bean, String sign);

    /**
     * 用户修改预留手机号插入一条记录
     *
     * @param account
     * @param bankMobile
     * @param userId
     * @return
     */
    boolean insertBankMobileModify(String account, String bankMobile, int userId);

    /**
     * 异步回调更新银行预留手机号
     *
     * @param bean
     * @param oldMobile
     * @param modifyClient
     * @param ip
     * @return
     */
    BankCallResult updateNewBankMobile(BankCallBean bean, String oldMobile, String modifyClient, String ip);

    /**
     * 查询最新银行预留手机号
     *
     * @param userId
     * @return
     */
    String getNewBankMobile(Integer userId);

    /**
     * 修改预留手机号异步回调结果查询
     *
     * @param logOrdId
     * @return
     */
    String getMobileModifyMess(String logOrdId);
}
