/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.aems.register.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.StringRandomUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.AemsUserRegisterRequestBean;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.service.aems.register.AemsUserRegisterService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.util.GetInfoByUserIp;
import com.hyjf.cs.user.util.SignUtil;
import com.hyjf.cs.user.vo.RegisterRequest;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * AEMS系统:用户注册Service实现类
 *
 * @author liuyang
 * @version AemsUserRegisterServiceImpl, v0.1 2018/12/6 11:45
 */
@Service
public class AemsUserRegisterServiceImpl extends BaseUserServiceImpl implements AemsUserRegisterService {


    @Autowired
    private AmUserClient amUserClient;


    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private CommonProducer commonProducer;

    /**
     * 参数校验
     *
     * @param registerRequest
     */
    @Override
    public void apiCheckParam(RegisterRequest registerRequest) {
        // 手机号
        String mobile = registerRequest.getMobile();
        // 机构编号
        String instCode = registerRequest.getInstCode();
        // 注册平台
        String platform = registerRequest.getPlatform();
        // 注册渠道
        String utmId = registerRequest.getUtmId();
        //推荐人
        String reffer = registerRequest.getReffer();
        //手机号未填写
        CheckUtil.check(StringUtils.isNotEmpty(mobile), MsgEnum.STATUS_ZC000001);
        // 机构编号
        CheckUtil.check(StringUtils.isNotEmpty(instCode), MsgEnum.STATUS_ZC000002);
        // 注册平台
        CheckUtil.check(StringUtils.isNotEmpty(platform), MsgEnum.STATUS_ZC000018);
        // 推广渠道
        CheckUtil.check(StringUtils.isNotEmpty(utmId), MsgEnum.STATUS_ZC000019);

        CheckUtil.check(Validator.isMobile(mobile), MsgEnum.STATUS_ZC000003);

        // TODO: 2018/6/23 原代码平台推荐人未作处理
        if (StringUtils.isNotEmpty(reffer)) {
            // CheckUtil.check(amUserClient.countUserByRecommendName(recommended) > 0, MsgEnum.ERR_OBJECT_INVALID,"推荐人");//无效的推荐人
        }
    }


    /**
     * 获取授权状态
     *
     * @param userId
     * @return
     */
    @Override
    public String getAutoInvesStatus(Integer userId) {
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthByUserId(userId);
        if (null != hjhUserAuth) {
            return String.valueOf(hjhUserAuth.getAutoInvesStatus());
        }
        return null;
    }


    @Override
    @HystrixCommand(commandKey = "AEMS系统用户注册",fallbackMethod = "fallBackApiRegister",ignoreExceptions = CheckException.class,commandProperties = {
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
    public UserVO apiRegister(AemsUserRegisterRequestBean aemsUserRegisterRequestBean, RegisterRequest registerRequest, String ip) {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        BeanUtils.copyProperties(registerRequest, registerUserRequest);
        registerUserRequest.setLoginIp(ip);
        // 根据机构编号检索机构信息
        HjhInstConfigVO instConfig = this.amTradeClient.selectInstConfigByInstCode(registerRequest.getInstCode());
        // 机构编号
        CheckUtil.check(instConfig != null, MsgEnum.STATUS_ZC000004);
        registerUserRequest.setInstType(instConfig.getInstType());
        // 根据渠道号检索推广渠道是否存在
        UtmPlatVO utmPlat = this.amUserClient.selectUtmPlatByUtmId(registerRequest.getUtmId());
        CheckUtil.check(null != utmPlat, MsgEnum.STATUS_ZC000020);
        String password = StringRandomUtil.getStringRandom(6);
        // 密码
        registerUserRequest.setPassword(password);

        //add by libin 用户注册时通过ip获得用户所在的省，市 start
        logger.info("获取到的用户ip为：" + ip);
        String info = GetInfoByUserIp.getInfoByUserIp(ip);
        if(info == null || StringUtils.isEmpty(info)){
            logger.error("通过httpRequest获取的ip解析后未获取到省市信息！");
            registerUserRequest.setProvince("");
            registerUserRequest.setCity("");
        } else {
            StringBuffer line = new StringBuffer(info);
            int first_idx   = line.indexOf("|");
            String country = line.substring(0, first_idx);//所属国家暂时不用先保留

            line = new StringBuffer(line.substring(first_idx + 1) );
            int second_idx   = line.indexOf("|");
            String number = line.substring(0, second_idx);//所属数字暂时不用先保留

            line = new StringBuffer(line.substring(second_idx + 1) );
            int thrid_idx   = line.indexOf("|");
            String province = line.substring(0, thrid_idx);//省
            if(province != null && StringUtils.isNotEmpty(province)){
                registerUserRequest.setProvince(province);
            } else {
                registerUserRequest.setProvince("");
            }

            line = new StringBuffer(line.substring(thrid_idx + 1) );
            int fouth_idx   = line.indexOf("|");
            String city = line.substring(0, fouth_idx);//市
            if(city != null && StringUtils.isNotEmpty(city)){
                registerUserRequest.setCity(city);
            } else {
                registerUserRequest.setCity("");
            }
        }
        //add by libin 用户注册时通过ip获得用户所在的省，市 end
        // 2.注册
        UserVO userVO = amUserClient.register(registerUserRequest);
        CheckUtil.check(userVO != null, MsgEnum.ERR_USER_REGISTER);

        // 3. 注册成功用户保存账户表
        sendMqToSaveAccount(userVO.getUserId(), userVO.getUsername());
        return userVO;
    }


    public UserVO fallBackApiRegister(AemsUserRegisterRequestBean aemsUserRegisterRequestBean, RegisterRequest registerRequest, String ip) {
        logger.info("==================已进入 用户注册(api) fallBackApiRegister 方法================");
        return null;
    }
    /**
     * 注册保存账户表
     *
     * @param userId
     * @throws MQException
     */
    private void sendMqToSaveAccount(int userId, String userName) {
        AccountVO account = new AccountVO();
        account.setUserId(userId);
        account.setUserName(userName);
        // 银行存管相关
        account.setPlanAccedeBalance(BigDecimal.ZERO);
        account.setPlanAccedeFrost(BigDecimal.ZERO);
        account.setBankBalance(BigDecimal.ZERO);
        account.setBankBalanceCash(BigDecimal.ZERO);
        account.setBankFrost(BigDecimal.ZERO);
        account.setBankFrostCash(BigDecimal.ZERO);
        account.setBankInterestSum(BigDecimal.ZERO);
        account.setBankInvestSum(BigDecimal.ZERO);
        account.setBankWaitCapital(BigDecimal.ZERO);
        account.setBankWaitInterest(BigDecimal.ZERO);
        account.setBankWaitRepay(BigDecimal.ZERO);
        account.setBankTotal(BigDecimal.ZERO);
        account.setBankAwaitCapital(BigDecimal.ZERO);
        account.setBankAwaitInterest(BigDecimal.ZERO);
        account.setBankAwait(BigDecimal.ZERO);
        account.setBankWaitRepayOrg(BigDecimal.ZERO);
        account.setBankAwaitOrg(BigDecimal.ZERO);
        // 汇付相关
        account.setTotal(BigDecimal.ZERO);
        account.setBalance(BigDecimal.ZERO);
        account.setBalanceCash(BigDecimal.ZERO);
        account.setBalanceFrost(BigDecimal.ZERO);
        account.setFrost(BigDecimal.ZERO);
        account.setAwait(BigDecimal.ZERO);
        account.setRepay(BigDecimal.ZERO);
        account.setPlanAccedeTotal(BigDecimal.ZERO);
        account.setPlanBalance(BigDecimal.ZERO);
        account.setPlanFrost(BigDecimal.ZERO);
        account.setPlanAccountWait(BigDecimal.ZERO);
        account.setPlanCapitalWait(BigDecimal.ZERO);
        account.setPlanInterestWait(BigDecimal.ZERO);
        account.setPlanRepayInterest(BigDecimal.ZERO);
        logger.info("注册插入account：{}", JSON.toJSONString(account));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(account)));
        } catch (MQException e) {
            logger.error("注册成功推送account——mq失败.... user_id is :{}", userId);
            throw new RuntimeException("注册成功推送account——mq失败...");
        }
    }

}
