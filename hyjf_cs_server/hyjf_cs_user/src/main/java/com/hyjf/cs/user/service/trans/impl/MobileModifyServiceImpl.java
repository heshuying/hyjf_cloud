/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.trans.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.UserOperationLogConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.bean.BankMobileModifyBean;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmDataCollectClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.result.MobileModifyResultBean;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.trans.MobileModifyService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhangqingqing
 * @version MobileModifyServiceImpl, v0.1 2018/6/14 16:48
 */
@Service
public class MobileModifyServiceImpl extends BaseUserServiceImpl implements MobileModifyService {
    private static final Logger logger = LoggerFactory.getLogger(MobileModifyServiceImpl.class);

    @Autowired
    AmUserClient amUserClient;
    @Autowired
    AmConfigClient amConfigClient;
    @Autowired
    CommonProducer commonProducer;
    @Autowired
    AmDataCollectClient amDataCollectClient;

    /**
     * 更换手机号条件校验
     *
     * @param newMobile
     * @param smsCode
     */
    @Override
    public boolean checkForMobileModify(String newMobile, String smsCode) {
        String verificationType = CommonConstant.PARAM_TPL_BDYSJH;
        int cnt = amUserClient.checkMobileCode(newMobile, smsCode, verificationType, CommonConstant.CLIENT_PC,
                CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED, true);
        CheckUtil.check(cnt > 0, MsgEnum.ERR_OBJECT_INVALID, "验证码");//无效的验证码
        return true;
    }

    /**
     * 更换手机号码验证（已开户）
     */
    @Override
    public boolean checkForMobileModifyOpened(String newMobile, String smsCode, String srvAuthCode) {
        CheckUtil.check(!StringUtils.isBlank(newMobile), MsgEnum.ERR_PARAM_NUM);
        CheckUtil.check(!StringUtils.isBlank(smsCode), MsgEnum.ERR_PARAM_NUM);
        CheckUtil.check(!StringUtils.isBlank(srvAuthCode), MsgEnum.ERR_PARAM_NUM);
        return true;
    }


    /**
     * 用户手机号修改信息查询
     *
     * @param userId
     * @return
     */
    @Override
    public MobileModifyResultBean queryForMobileModify(Integer userId) {
        MobileModifyResultBean result = new MobileModifyResultBean();
        UserVO user = amUserClient.findUserById(userId);
        if (user != null && StringUtils.isNotBlank(user.getMobile())) {
            String hideMobile = user.getMobile().substring(0, user.getMobile().length() - (user.getMobile().substring(3)).length()) + "****" + user.getMobile().substring(7);
            result.setMobile(user.getMobile());
            result.setHideMobile(hideMobile);
        }

        return result;
    }

    @Override
    public BankCallBean callMobileModify(Integer userId, String newMobile, String smsCode, String srvAuthCode) {
        BankOpenAccountVO bankAccount = amUserClient.selectById(userId);
        // 调用电子账号手机号修改增强
        BankCallBean bean = new BankCallBean();
        bean.setTxCode(BankCallConstant.TXCODE_MOBILE_MODIFY_PLUS);
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        bean.setAccountId(bankAccount.getAccount());// 电子账号
        bean.setOption(BankCallConstant.OPTION_1);// 修改
        bean.setMobile(newMobile);// 新手机号
        bean.setLastSrvAuthCode(srvAuthCode);// 业务授权码
        bean.setSmsCode(smsCode);// 短信验证码
        // 商户私有域，存放开户平台,用户userId
        LogAcqResBean acqRes = new LogAcqResBean();
        acqRes.setUserId(userId);
        bean.setLogAcqResBean(acqRes);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));
        // 返回参数
        BankCallBean retBean = null;
        try {
            // 调用接口
            retBean = BankCallUtils.callApiBg(bean);
        } catch (Exception e) {
            logger.error("请求手机号码修改接口失败", e);
            return null;
        }

        return retBean;
    }

    @Override
    public void updateUserCAMQ(int userId) throws ParseException, MQException {
        // add by liuyang 20180209 开户成功后,将用户ID加入到CA认证消息队列 start
        // 加入到消息队列

        String startTime = GetDate.dateToString(new Date());
        // 循环去做CA认证

        FddCertificateAuthorityVO fddCertificateAuthorityVO = new FddCertificateAuthorityVO();
        fddCertificateAuthorityVO.setUserId(userId);
        fddCertificateAuthorityVO.setCertFrom("mobileModify");
        commonProducer.messageSend(new MessageContent(MQConstant.FDD_CERTIFICATE_AUTHORITY_TOPIC,
                UUID.randomUUID().toString(), fddCertificateAuthorityVO));

        // 处理结束时间
        String endTime = GetDate.dateToString(new Date());
        // 处理用时
        String consumeTime = GetDate.countTime(GetDate.stringToDate(startTime), GetDate.stringToDate(endTime));
        logger.info("处理用时:" + startTime + "减去" + endTime + "等于" + consumeTime);
    }

    /**
     * 用户修改银行预留手机号
     *
     * @param bean
     * @param sign
     * @return
     */
    @Override
    @HystrixCommand(commandKey = "修改银行预留手机号(三端)-getBankMobileModify", fallbackMethod = "fallBackBankMobileModify", ignoreExceptions = CheckException.class, commandProperties = {
            //设置断路器生效
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            //一个统计窗口内熔断触发的最小个数3/10s
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "50"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            //熔断5秒后去尝试请求
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            //失败率达到30百分比后熔断
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30")})
    public Map<String, Object> getBankMobileModify(BankMobileModifyBean bean, String sign) {
        // 调用开户接口
        BankCallBean mobileModifyBean = new BankCallBean(bean.getUserId(), BankCallConstant.TXCODE_MOBILE_MODIFY_PAGE, Integer.parseInt(bean.getPlatform()), BankCallConstant.BANK_URL_MOBILE_MODIFY_PAGE);
        mobileModifyBean.setChannel(bean.getChannel());
        // 该接口只有一个返回页面
        String successPath = "/user/bankMobileModify/result?logOrdId=" + mobileModifyBean.getLogOrderId();
        // 同步地址  是否跳转到前端页面
        String retUrl = super.getFrontHost(systemConfig, bean.getPlatform()) + successPath;
        // 如果是移动端  返回别的url
        if ((ClientConstants.APP_CLIENT + "").equals(bean.getPlatform()) || (ClientConstants.APP_CLIENT_IOS + "").equals(bean.getPlatform()) || (ClientConstants.WECHAT_CLIENT + "").equals(bean.getPlatform())) {
            successPath = "/user/setting/mobile/jump";
            // 同步地址  是否跳转到前端页面
            retUrl = super.getFrontHost(systemConfig, bean.getPlatform()) + successPath + "?status=000&statusDesc=";
            retUrl += "&token=1&sign=" + sign + "&logOrdId=" + mobileModifyBean.getLogOrderId();
        }
        String bgRetUrl = "http://CS-USER/hyjf-web/user/bankMobileModifyBgReturn?phone=" + bean.getBankMobile()+"&modifyclient="+bean.getPlatform()+"&logIp=" + bean.getIp();
        // 接口只给了一个返回地址
        mobileModifyBean.setRetUrl(retUrl);
        mobileModifyBean.setNotifyUrl(bgRetUrl);
        mobileModifyBean.setLogRemark("修改银行预留手机号");
        mobileModifyBean.setLogIp(bean.getIp());
        mobileModifyBean.setAccountId(bean.getAccountId());
        bean.setOrderId(mobileModifyBean.getLogOrderId());
        try {
            Map<String, Object> map = BankCallUtils.callApiMap(mobileModifyBean);
            return map;
        } catch (Exception e) {
            throw new CheckException(MsgEnum.ERR_BANK_CALL);
        }
    }

    /**
     * 用户修改预留手机号插入一条记录
     *
     * @param account
     * @param bankMobile
     * @param userId
     * @return
     */
    @Override
    public boolean insertBankMobileModify(String account, String bankMobile, int userId) {
        BankMobileModifyVO vo = new BankMobileModifyVO();
        vo.setUserId(userId);
        vo.setAccount(account);
        vo.setBankMobileOld(bankMobile);
        vo.setStatus(0);
        vo.setCreateBy(userId + "");
        vo.setCreateTime(new Date());
        vo.setUpdateBy(userId + "");
        vo.setUpdateTime(new Date());
        return amUserClient.insertBankMobileModify(vo);
    }

    /**
     * 异步回调更新银行预留手机号
     *
     * @param bean
     * @param oldMobile
     * @return
     */
    @Override
    public BankCallResult updateNewBankMobile(BankCallBean bean, String oldMobile, String modifyClient, String ip) {
        String newBankMobile = "";
        BankCallResult result = new BankCallResult();
        int userId = Integer.parseInt(bean.getLogUserId());
        // 获取用户信息
        UserVO user = getUsersById(userId);
        if (null == user) {
            result.setStatus(false);
            result.setMessage("根据用户ID获取用户信息失败。");
            return result;
        }
        UserInfoVO userInfo = getUserInfo(userId);
        if (null == userInfo) {
            result.setStatus(false);
            result.setMessage("根据用户ID获取用户详细信息失败。");
            return result;
        }
        // 银行返回响应代码
        String retCode = StringUtils.isNotBlank(bean.getRetCode()) ? bean.getRetCode() : "";
        logger.info("更新银行预留手机号异步处理start,UserId:{} 更新平台为：{} 银行返回响应代码为：{}", bean.getLogUserId(), bean.getLogClient(), retCode);
        // State为0时候为0：交易失败 1：交易成功
        if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
            logger.info("银行预留手机号修改失败，失败原因:银行返回响应代码:[" + retCode + "],订单号:[" + bean.getLogOrderId() + "].");
            result.setMessage("银行预留手机号修改失败。");
            result.setStatus(false);
            return result;
        }
        // 重新调用一下查询银行预留手机号
        newBankMobile = getBankMobile(bean.getAccountId(), userId);
        if (StringUtils.isBlank(newBankMobile)) {
            // 接口未查询出最新手机号用原异步回掉返回的手机号
            newBankMobile = bean.getMobile();
        }
        // 同一手机号不更新
        if (oldMobile.equals(newBankMobile)) {
            result.setStatus(true);
            logger.info("页面更新银行预留手机号与原手机号相同,UserId:{} 更新平台为：{}", bean.getLogUserId(), bean.getLogClient());
            result.setMessage("更新银行预留手机号处理成功,但银行新预留手机号码与旧手机号码相同。");
            return result;
        }
        // 更新用户信息表
        this.amUserClient.updateBankMobileByUserId(userId, newBankMobile);
        // 更新redis里面的值
        WebViewUserVO redisUser = RedisUtils.getObj(RedisConstants.USERID_KEY + userId, WebViewUserVO.class);
        if (redisUser != null) {
            redisUser.setBankMobile(newBankMobile);
            RedisUtils.setObjEx(RedisConstants.USERID_KEY + userId, redisUser, 7 * 24 * 60 * 60);
        }
        // 更新用户修改银行预留手机号日志表
        BankMobileModifyVO vo = new BankMobileModifyVO();
        vo.setUserId(userId);
        vo.setBankMobileNew(newBankMobile);
        vo.setUpdateBy(userId + "");
        vo.setUpdateTime(new Date());
        this.amUserClient.updateBankMobileModify(vo);
        result.setStatus(true);
        result.setMessage("更新银行预留手机号处理成功");
        // 会员操作日志表更新
        // 插入用户操作明细
        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE13);
        userOperationLogEntity.setIp(ip);
        try {
            userOperationLogEntity.setPlatform(Integer.parseInt(modifyClient));
        } catch (NumberFormatException e) {
            userOperationLogEntity.setPlatform(0);
            logger.info("格式化操作平台失败,平台编号：{}", modifyClient);
        }
        userOperationLogEntity.setRemark("原手机号:" + oldMobile);
        userOperationLogEntity.setOperationTime(new Date());
        userOperationLogEntity.setUserName(user.getUsername());
        userOperationLogEntity.setUserRole(String.valueOf(userInfo.getRoleId()));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
        } catch (MQException e) {
            logger.error("保存用户日志失败", e);
        }
        logger.info("页面更新银行预留手机号处理end,UserId:{} 更新平台为：{}", bean.getLogUserId(), bean.getLogClient());
        return result;
    }

    /**
     * 查询最新银行预留手机号更新数据库
     *
     * @param userId
     * @return
     */
    @Override
    @HystrixCommand(commandKey = "查询银行预留手机号(三端)-getBankMobileModify", fallbackMethod = "fallBackGetBankMobile", ignoreExceptions = CheckException.class, commandProperties = {
            //设置断路器生效
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            //一个统计窗口内熔断触发的最小个数3/10s
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "50"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            //熔断5秒后去尝试请求
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            //失败率达到30百分比后熔断
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30")})
    public String getNewBankMobile(Integer userId) {
        logger.info("查询更新银行预留手机号开始，userId:{}" + userId);
        // 获取用户信息
        UserVO user = this.getUsersById(userId);
        if (user == null) {
            // 获取用户信息失败
            CheckUtil.check(false, MsgEnum.ERR_OBJECT_GET, "用户信息");
        }
        // 目前只有个人用户可修改
        if(null == user.getUserType() || user.getUserType() ==  1) {
            // 只针对个人用户修改手机号
            throw new CheckException(MsgEnum.ERR_USER_PERSON_ONLY);
        }
        BankOpenAccountVO bankOpenAccountVO = this.getBankOpenAccount(userId);
        if (null == bankOpenAccountVO || StringUtils.isBlank(bankOpenAccountVO.getAccount())) {
            // 用户未开户
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 调用银行获取最新银行预留手机号
        String newBankMobile = getBankMobile(bankOpenAccountVO.getAccount(), userId);
        if(StringUtils.isBlank(newBankMobile)) {
            throw new CheckException(MsgEnum.STATUS_CE000004);
        }
        user.setBankMobile(newBankMobile);
        // 更新redis里面的值
        WebViewUserVO redisUser = RedisUtils.getObj(RedisConstants.USERID_KEY + userId, WebViewUserVO.class);
        if (redisUser != null) {
            redisUser.setBankMobile(newBankMobile);
            RedisUtils.setObjEx(RedisConstants.USERID_KEY + userId, redisUser, 7 * 24 * 60 * 60);
        }
        // 更新用户信息表
        this.amUserClient.updateBankMobileByUserId(userId, newBankMobile);
        // 更新用户修改银行预留手机号日志表
        BankMobileModifyVO vo = new BankMobileModifyVO();
        vo.setUserId(user.getUserId());
        vo.setBankMobileNew(newBankMobile);
        vo.setUpdateBy(userId + "");
        vo.setUpdateTime(new Date());
        this.amUserClient.updateBankMobileModify(vo);
        return newBankMobile;
    }

    /**
     * 修改预留手机号异步回调结果查询
     *
     * @param logOrdId
     * @return
     */
    @Override
    public String getMobileModifyMess(String logOrdId) {

        //根据ordid获取retcode
        String retCode = amDataCollectClient.getRetCode(logOrdId);
        logger.info("根据"+logOrdId+"获取retcode="+retCode);
        if (retCode==null){
            // 设定个变量页面展示处理中
            return "WATING";
        }
        if(retCode.equals("00000000")){
            return "00000000";
        }
        //根据retCode获取retMsg
        String retMsg = this.getBankRetMsg(retCode);
        return retMsg;
    }

    /**
     * 查询最新银行预留手机号
     *
     * @param accountId
     * @param userId
     * @return
     */
    private String getBankMobile(String accountId, Integer userId) {
        BankCallBean callBean = new BankCallBean(userId, BankCallConstant.TXCODE_MOBILE_MODIFY_QUERY, 0);
        callBean.setChannel(BankCallConstant.CHANNEL_PC);
        callBean.setLogRemark("根据电子账户号查询手机号");
        callBean.setLogUserId(userId + "");
        callBean.setAccountId(accountId);
        callBean = BankCallUtils.callApiBg(callBean);
        logger.info("根据电子账户号查询手机号 结果：{}  ", JSONObject.toJSONString(callBean));
        return callBean.getMobile();
    }

    /**
     * @param bean
     * @param sign
     * @return
     */
    public Map<String, Object> fallBackBankMobileModify(BankMobileModifyBean bean, String sign) {
        logger.info("==================已进入 修改银行预留手机号（三端）fallBackBankOpen 方法================");
        return null;
    }

    /**
     * @param userId
     * @return
     */
    public String fallBackGetBankMobile(Integer userId) {
        logger.info("==================已进入 查询银行预留手机号（三端）fallBackGetBankMobile 方法================");
        return null;
    }
}
