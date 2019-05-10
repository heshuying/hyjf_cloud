/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.trans.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankMobileModifyVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.FddCertificateAuthorityVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.bean.BankMobileModifyBean;
import com.hyjf.cs.user.client.AmConfigClient;
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
        BankCallBean openAccoutBean = new BankCallBean(bean.getUserId(), BankCallConstant.TXCODE_MOBILE_MODIFY_PAGE, Integer.parseInt(bean.getPlatform()), BankCallConstant.BANK_URL_MOBILE_MODIFY_PAGE);
        openAccoutBean.setChannel(bean.getChannel());
        // 该接口只有一个返回页面
        String successPath = "/user/bankMobileModifySuccess";
        // 同步地址  是否跳转到前端页面
        String retUrl = super.getFrontHost(systemConfig, bean.getPlatform()) + successPath;
        // 如果是移动端  返回别的url
        if ((ClientConstants.APP_CLIENT + "").equals(bean.getPlatform()) || (ClientConstants.APP_CLIENT_IOS + "").equals(bean.getPlatform()) || (ClientConstants.WECHAT_CLIENT + "").equals(bean.getPlatform())) {
            successPath = "/user/bankMobileModify/result/success";
            // 同步地址  是否跳转到前端页面
            retUrl = super.getFrontHost(systemConfig, bean.getPlatform()) + successPath + "?status=000&statusDesc=";
            retUrl += "&token=1&sign=" + sign;
        }
        String bgRetUrl = "http://CS-USER/hyjf-web/user/bankMobileModifyBgReturn?phone=" + bean.getBankMobile();
        // 接口只给了一个返回地址
        openAccoutBean.setRetUrl(retUrl);
        openAccoutBean.setNotifyUrl(bgRetUrl);
        openAccoutBean.setLogRemark("修改银行预留手机号");
        openAccoutBean.setLogIp(bean.getIp());
        openAccoutBean.setAccountId(bean.getAccountId());
        bean.setOrderId(openAccoutBean.getLogOrderId());
        try {
            Map<String, Object> map = BankCallUtils.callApiMap(openAccoutBean);
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
    public BankCallResult updateNewBankMobile(BankCallBean bean, String oldMobile) {
        String newBankMobile = "";
        BankCallResult result = new BankCallResult();
        int userId = Integer.parseInt(bean.getLogUserId());
        // 银行返回响应代码
        String retCode = StringUtils.isNotBlank(bean.getRetCode()) ? bean.getRetCode() : "";
        logger.info("更新银行预留手机号异步处理start,UserId:{} 更新平台为：{} 银行返回响应代码为：{}", bean.getLogUserId(), bean.getLogClient(), retCode);
        // State为0时候为0：交易失败 1：交易成功
        if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
            logger.info("银行预留手机号修改失败，失败原因:银行返回响应代码:[" + retCode + "],订单号:[" + bean.getLogOrderId() + "].");
            result.setStatus(false);
            return result;
        }
        // 重新调用一下查询银行预留手机号
        newBankMobile = getNewBankMobile(bean, userId);
        if (StringUtils.isBlank(newBankMobile)) {
            // 接口未查询出最新手机号用原异步回掉返回的手机号
            newBankMobile = bean.getMobile();
        }
        // 同一手机号不更新
        if(oldMobile.equals(newBankMobile)) {
            result.setStatus(true);
            logger.info("页面更新银行预留手机号与原手机号相同,UserId:{} 更新平台为：{}", bean.getLogUserId(), bean.getLogClient());
            result.setMessage("更新银行预留手机号处理成功,但银行新预留手机号码与旧手机号码相同。");
            return result;
        }
        // 更新用户信息表
        this.amUserClient.updateBankMobileByUserId(userId, newBankMobile);
        // 更新用户修改银行预留手机号日志表
        BankMobileModifyVO vo = new BankMobileModifyVO();
        vo.setUserId(userId);
        vo.setBankMobileNew(newBankMobile);
        vo.setUpdateBy(userId + "");
        vo.setUpdateTime(new Date());
        this.amUserClient.updateBankMobileModify(vo);
        result.setStatus(true);
        result.setMessage("更新银行预留手机号处理成功");
        logger.info("页面更新银行预留手机号处理end,UserId:{} 更新平台为：{}", bean.getLogUserId(), bean.getLogClient());
        return result;
    }

    /**
     * 重新查询最新银行预留手机号
     *
     * @param bean
     * @return
     */
    private String getNewBankMobile(BankCallBean bean, Integer userId) {
        BankCallBean callBean = new BankCallBean(userId, BankCallConstant.TXCODE_MOBILE_MODIFY_QUERY, 0);
        callBean.setChannel(BankCallConstant.CHANNEL_PC);
        callBean.setLogRemark("根据电子账户号查询手机号");
        callBean.setLogUserId(userId + "");
        callBean.setAccountId(bean.getAccountId());
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
}
