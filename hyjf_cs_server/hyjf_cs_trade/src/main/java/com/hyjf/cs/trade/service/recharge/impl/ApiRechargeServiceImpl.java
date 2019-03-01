/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.recharge.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.resquest.trade.HandleAccountRechargeRequest;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.resquest.user.BankSmsLogRequest;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.ApiUserRechargeRequestBean;
import com.hyjf.cs.trade.bean.ApiUserRechargeResultBean;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.recharge.ApiRechargeService;
import com.hyjf.cs.trade.util.SignUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: ApiRechargeServiceImpl, v0.1 2018/8/28 10:40
 */
@Service
public class ApiRechargeServiceImpl extends BaseTradeServiceImpl implements ApiRechargeService {

    @Autowired
    private SystemConfig systemConfig;

    // 充值状态:充值中
    private static final int RECHARGE_STATUS_WAIT = 1;
    // 充值状态:失败
    private static final int RECHARGE_STATUS_FAIL = 3;
    // 充值状态:成功
    private static final int RECHARGE_STATUS_SUCCESS = 2;

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
        this.sendSmsCheckParams(requestBean);
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
            BankOpenAccountVO bankOpenAccount = amUserClient.selectBankOpenAccountByAccountId(accountId);
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

    /**
     * 短信充值
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    @HystrixCommand(commandKey = "短信充值(api)-apiRechargeService",fallbackMethod = "fallBackApiRecharge",ignoreExceptions = CheckException.class,commandProperties = {
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
    public ApiUserRechargeResultBean recharge(HttpServletRequest request, ApiUserRechargeRequestBean requestBean) {
        logger.info("-----------短信充值-----------");
        ApiUserRechargeResultBean resultBean = new ApiUserRechargeResultBean();
        this.rechargeCheckParams(requestBean);
        try {
            // 短信验证码
            String smsCode = requestBean.getSmsCode();
            // 银行卡预留手机号
            String mobile = requestBean.getMobile();
            // 充值银行卡号
            String cardNo = requestBean.getCardNo();
            // 充值金额
            String account = requestBean.getAccount();
            // 银行电子账户号
            String accountId = requestBean.getAccountId();
            // 渠道
            String channel = requestBean.getChannel();
            // 机构编号
            String instCode = requestBean.getInstCode();
            // 充值平台
            String platform = requestBean.getPlatform();

            // 根据机构编号检索机构信息
            HjhInstConfigVO instConfig = amTradeClient.selectInstConfigByInstCode(instCode);
            // 机构编号
            if (instConfig == null) {
                logger.info("获取机构信息为空,机构编号:[" + instCode + "].");
                throw new CheckException(MsgEnum.STATUS_ZC000004);
            }
            // 根据用户电子账户号查询用户信息
            BankOpenAccountVO bankOpenAccount = amUserClient.selectBankOpenAccountByAccountId(accountId);
            if (bankOpenAccount == null) {
                logger.info("查询用户开户信息失败,用户电子账户号:[" + accountId + "]");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("充值失败");
                return resultBean;
            }
            // 用户ID
            Integer userId = bankOpenAccount.getUserId();
            // 根据用户ID查询用户信息
            UserVO user = amUserClient.findUserById(userId);
            if (user == null) {
                logger.info("根据用户ID查询用户信息失败,用户电子账户号:[" + accountId + "],用户ID:[" + userId + "].");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("充值失败");
                return resultBean;
            }
            // 根据用户ID查询用户详情
            UserInfoVO userInfo = amUserClient.findUserInfoById(userId);
            if (userInfo == null) {
                logger.info("根据用户ID查询用户详情失败,用户电子账户号:[" + accountId + ",用户ID:[" + userId + "]");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("充值失败");
                return resultBean;
            }
            // 身份证号
            String idNo = userInfo.getIdcard();
            // 姓名
            String trueName = userInfo.getTruename();
            // 根据用户ID查询用户平台银行卡信息
            BankCardVO bankCard = amUserClient.getBankCardByUserId(userId);
            if (bankCard == null) {
                logger.info("根据用户ID查询用户银行卡信息失败,用户电子账户号:[" + accountId + "],用户ID:[" + userId + "].");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("充值失败");
                return resultBean;
            }
            // 用户汇盈平台的银行卡卡号
            String localCardNo = bankCard.getCardNo() == null ? "" : bankCard.getCardNo();
            // 如果两边银行卡号不一致
            if (!cardNo.equals(localCardNo)) {
                logger.info("用户银行卡信息不一致,用户电子账户号:[" + accountId + "],请求银行卡号:[" + cardNo + "],平台保存的银行卡号:[" + localCardNo + "].");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("充值失败");
                return resultBean;
            }
            // 获取短信序列号
            BankSmsLogRequest bankSmsLogRequest = new BankSmsLogRequest();
            bankSmsLogRequest.setUserId(userId);
            bankSmsLogRequest.setSrvTxCode(BankCallMethodConstant.TXCODE_DIRECT_RECHARGE_ONLINE);
            String smsSeq = amUserClient.selectBankSmsLog(bankSmsLogRequest);
            if (StringUtils.isBlank(smsSeq)) {
                logger.info("短信序列号为空,电子账户号:[" + accountId + "],用户ID:[" + userId + "],短信序列号:[" + smsSeq + "].");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("充值失败");
                return resultBean;
            }
            // 充值订单号
            String logOrderId = GetOrderIdUtils.getOrderId2(userId);
            // 充值订单日期
            String orderDate = GetOrderIdUtils.getOrderDate();
            // 调用 2.3.4联机绑定卡到电子账户充值
            BankCallBean bean = new BankCallBean();
            bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
            bean.setTxCode(BankCallMethodConstant.TXCODE_DIRECT_RECHARGE_ONLINE);// 交易代码
            bean.setInstCode(systemConfig.getBankInstcode());// 机构代码
            bean.setBankCode(systemConfig.getBankBankcode());// 银行代码
            bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
            bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
            bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
            bean.setChannel(channel); // 交易渠道
            bean.setAccountId(accountId); // 电子账号
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD); // 证件类型
            bean.setIdNo(idNo); // 身份证号
            bean.setName(trueName); // 姓名
            bean.setMobile(mobile); // 手机号
            bean.setCardNo(cardNo); // 银行卡号
            bean.setTxAmount(CustomUtil.formatAmount(account)); // 交易金额
            bean.setCurrency(BankCallConstant.CURRENCY_TYPE_RMB); // 交易币种
            bean.setSmsCode(smsCode); // 充值时短信验证,p2p使用
            bean.setSmsSeq(smsSeq);
            // 充值时短信验证,p2p使用
            bean.setUserIP(GetCilentIP.getIpAddr(request));// 客户IP
            bean.setLogOrderId(logOrderId);// 订单号
            bean.setLogOrderDate(orderDate);// 充值订单日期
            bean.setLogUserId(String.valueOf(userId));
            bean.setLogUserName(user.getUsername());
            bean.setLogRemark("短信充值");
            bean.setLogClient(Integer.parseInt(platform));// 充值平台
            // 插入充值记录
            int ret = this.insertRechargeInfo(bean);
            if (ret == 0) {
                logger.info("调用接口前,插入充值记录表失败,用户电子账户号:[" + accountId + "],用户ID:[" + userId + "],充值订单号:[" + logOrderId + "].");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("充值失败");
                return resultBean;
            }
            // 调用短信充值接口
            BankCallBean bankCallBean = BankCallUtils.callApiBg(bean);
            if (Validator.isNull(bankCallBean)) {
                logger.info("调用银行接口失败,银行返回为空,用户电子账户号:[" + accountId + "],用户ID:[" + userId + "],充值订单号:[" + logOrderId + "].");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("充值失败");
                resultBean.setOrderId(logOrderId);
                return resultBean;
            }
            // 银行返回响应代码
            String retCode = StringUtils.isNotBlank(bankCallBean.getRetCode()) ? bankCallBean.getRetCode() : "";
            if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                String error = amConfigClient.getBankRetMsg(retCode);
                logger.info("调用银行接口失败,银行返回结果retCode:[" + retCode + "],用户电子账户号:[" + accountId + "],用户ID:[" + userId + "],充值订单号:[" + logOrderId + "],银行返回错误信息:[" + error + "].");
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("充值失败");
                resultBean.setOrderId(logOrderId);
                return resultBean;
            }
            // 充值成功
            // 更新充值的相关信息
            String ip = CustomUtil.getIpAddr(request);
            BankCallBeanVO bankCallBeanVO = CommonUtils.convertBean(bankCallBean,BankCallBeanVO.class);
            String errorMsg = amConfigClient.getBankRetMsg(bankCallBean.getRetCode());
            // 组装请求参数
            HandleAccountRechargeRequest rechargeRequest = new HandleAccountRechargeRequest();
            rechargeRequest.setIp(ip);
            //rechargeRequest.setMobile(mobile);
            rechargeRequest.setBankCallBeanVO(bankCallBeanVO);
            rechargeRequest.setErrorMsg(errorMsg);
            String result = amTradeClient.handleRechargeInfo(rechargeRequest);
            JSONObject msg = JSONObject.parseObject(result);
            // 充值成功
            if (msg != null && msg.get("error").equals("0")) {
                logger.info("短信充值成功,手机号:[" + mobile + "],用户ID:[" + userId + "],充值金额:[" + bankCallBean.getTxAmount() + "]");
                if(msg.getBoolean("flag")){
                    //这一步本来在amTradeClient.handleRechargeInfo这里面执行，但是跨库了
                    String cardId = msg.getString("cardId");
                    updateBankMobile(userId,cardId,mobile);
                }
                resultBean.setStatus(BaseResult.SUCCESS);
                resultBean.setStatusDesc("充值成功");// 充值成功
                resultBean.setTxAmount(bankCallBean.getTxAmount());
                resultBean.setOrderId(logOrderId);
                return resultBean;
            } else {
                String message = (msg != null && msg.containsKey("data"))?msg.getString("data"):"amTrade返回的结果为 null -> [" + (msg == null) + "],如果结果不为null，就是未返回key值为data的数据";
                logger.info("短信充值失败,手机号:[" + mobile + "],用户ID:[" + userId + "],冲值金额:[" + bankCallBean.getTxAmount() + "],失败原因:[" + message + "].");
                // 充值失败
                resultBean.setStatus(BaseResult.FAIL);
                resultBean.setStatusDesc("充值失败");
                resultBean.setOrderId(logOrderId);
                return resultBean;
            }
        } catch (Exception e) {
            logger.info("短信充值发生异常,错误信息:[" + e.getMessage() + "]");
            // 充值失败
            resultBean.setStatus(BaseResult.FAIL);
            resultBean.setStatusDesc("充值失败");
            return resultBean;
        }
    }

    public ApiUserRechargeResultBean fallBackApiRecharge(HttpServletRequest request, ApiUserRechargeRequestBean requestBean) {
        logger.info("==================已进入 短信充值(api) fallBackApiRecharge 方法================");
        // 充值失败
        ApiUserRechargeResultBean resultBean = new ApiUserRechargeResultBean();
        resultBean.setStatus(BaseResult.FAIL);
        resultBean.setStatusDesc("充值失败");
        return resultBean;
    }

    /**
     * 更新银行预留手机号
     * @param userId
     * @param cardNo
     * @param newMobile
     */
    private void updateBankMobile(Integer userId, String cardNo, String newMobile){

        // 成功后更新银行预留手机号码
        if (StringUtils.isBlank(newMobile) || StringUtils.isBlank(cardNo)) {
            return;
        }
        BankCardRequest request = new BankCardRequest();
        request.setUserId(userId);
        request.setCardNo(cardNo);

        BankCardVO bankCardVO = amUserClient.selectBankCardByUserIdAndCardNo(request);
        if (bankCardVO != null) {

            if(bankCardVO.getMobile() == null || !bankCardVO.getMobile().equals(newMobile)){
                BankCardRequest bankCardRequest = new BankCardRequest();
                bankCardRequest.setId(bankCardVO.getId());
                bankCardRequest.setMobile(newMobile);
                bankCardRequest.setUpdateTime(new Date());
                bankCardRequest.setUpdateUserId(userId);
                amUserClient.updateUserCard(bankCardRequest);
            }
        }
    }
    /**
     * 预插入短信充值记录表
     * @auth sunpeikai
     * @param
     * @return
     */
    private int insertRechargeInfo(BankCallBean bean){
        int ret = 0;
        String ordId = bean.getLogOrderId() == null ? "" : bean.getLogOrderId(); // 订单号
        List<AccountRechargeVO> listAccountRecharge = amTradeClient.selectAccountRechargeByOrderId(ordId);
        if (listAccountRecharge != null && listAccountRecharge.size() > 0) {
            return ret;
        }
        int nowTime = GetDate.getNowTime10(); // 当前时间
        // 当前日期
        Date nowDate = new Date();
        // 银行卡号
        String cardNo = bean.getCardNo();
        // 根据银行卡号检索银行卡信息
        // 组装请求参数
        BankCardRequest bankCardRequest = new BankCardRequest();
        bankCardRequest.setUserId(Integer.parseInt(bean.getLogUserId()));
        bankCardRequest.setCardNo(cardNo);
        BankCardVO bankCard = amUserClient.selectBankCardByUserIdAndCardNo(bankCardRequest);
        BigDecimal money = new BigDecimal(bean.getTxAmount()); // 充值金额
        AccountRechargeVO record = new AccountRechargeVO();
        record.setNid(bean.getLogOrderId()); // 订单号
        record.setUserId(Integer.parseInt(bean.getLogUserId())); // 用户ID
        record.setUsername(bean.getLogUserName());// 用户 名
        record.setTxDate(Integer.parseInt(bean.getTxDate()));// 交易日期
        record.setTxTime(Integer.parseInt(bean.getTxTime()));// 交易时间
        record.setSeqNo(Integer.parseInt(bean.getSeqNo())); // 交易流水号
        record.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo()); // 交易日期+交易时间+交易流水号
        record.setStatus(RECHARGE_STATUS_WAIT); // 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败
        record.setAccountId(bean.getAccountId());// 电子账号
        record.setMoney(money); // 金额
        record.setCardid(cardNo);// 银行卡号
        // record.setFeeFrom(null);// 手续费扣除方式
        record.setFee(BigDecimal.ZERO); // 费用
        // record.setDianfuFee(BigDecimal.ZERO);// 垫付费用
        record.setBalance(money); // 实际到账余额
        record.setPayment(bankCard == null ? "" : bankCard.getBank()); // 所属银行
        record.setGateType("QP"); // 网关类型：QP快捷支付
        record.setType(1); // 类型.1网上充值.0线下充值
        record.setRemark("快捷充值");// 备注
        record.setCreateTime(nowDate);
        record.setOperator(bean.getLogUserId());
        // record.setAddtime(String.valueOf(nowTime));
        record.setAddIp(bean.getUserIP());
        record.setClient(bean.getLogClient()); // 0pc
        record.setIsBank(1);// 资金托管平台 0:汇付,1:江西银行
        // 插入用户充值记录表
        return this.amTradeClient.insertAccountRecharge(record);
    }

    /**
     * 发送短信的参数校验
     * @auth sunpeikai
     * @param
     * @return
     */
    private void sendSmsCheckParams(ApiUserRechargeRequestBean requestBean){
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
        CheckUtil.check(!SignUtil.verifyRequestSign(requestBean, "/server/user/recharge/sendSms"),MsgEnum.ERR_SIGN);
        // 手机号合法性校验
        CheckUtil.check(!Validator.isMobile(mobile),MsgEnum.ERR_MOBILE_IS_NOT_REAL);
    }

    /**
     * 短信充值的参数校验
     * @auth sunpeikai
     * @param
     * @return
     */
    private void rechargeCheckParams(ApiUserRechargeRequestBean requestBean){
        // 短信验证码
        String smsCode = requestBean.getSmsCode();
        // 银行卡预留手机号
        String mobile = requestBean.getMobile();
        // 充值银行卡号
        String cardNo = requestBean.getCardNo();
        // 充值金额
        String account = requestBean.getAccount();
        // 银行电子账户号
        String accountId = requestBean.getAccountId();
        // 渠道
        String channel = requestBean.getChannel();
        // 机构编号
        String instCode = requestBean.getInstCode();
        // 充值平台
        String platform = requestBean.getPlatform();
        // 验证请求参数
        // 手机号
        CheckUtil.check(Validator.isNull(mobile),MsgEnum.ERR_OBJECT_REQUIRED,"银行预留手机号");
        // 银行卡号
        CheckUtil.check(Validator.isNull(cardNo),MsgEnum.ERR_OBJECT_REQUIRED,"银行卡号");
        // 银行电子账户号
        CheckUtil.check(Validator.isNull(accountId),MsgEnum.ERR_OBJECT_REQUIRED,"银行电子账户号");
        // 渠道
        CheckUtil.check(Validator.isNull(channel),MsgEnum.ERR_OBJECT_REQUIRED,"渠道");
        // 充值金额
        CheckUtil.check(Validator.isNull(account),MsgEnum.ERR_OBJECT_REQUIRED,"充值金额");
        // 短信验证码
        CheckUtil.check(Validator.isNull(smsCode),MsgEnum.ERR_OBJECT_REQUIRED,"短信验证码");
        // 机构编号
        CheckUtil.check(Validator.isNull(instCode),MsgEnum.ERR_OBJECT_REQUIRED,"机构编号");
        // 充值平台
        CheckUtil.check(Validator.isNull(platform),MsgEnum.ERR_OBJECT_REQUIRED,"充值平台");
        // 验签
        CheckUtil.check(!SignUtil.verifyRequestSign(requestBean, "/server/user/recharge/recharge"),MsgEnum.ERR_SIGN);
        // 手机号合法性校验
        CheckUtil.check(!Validator.isMobile(mobile),MsgEnum.STATUS_ZC000003);
        // 充值金额校验
        CheckUtil.check(!account.matches("^[1-9][0-9]*$"),MsgEnum.ERR_FMT_MONEY);
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
        bean.setInstCode(systemConfig.getBankInstcode());// 机构代码
        bean.setBankCode(systemConfig.getBankBankcode());// 银行代码
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
