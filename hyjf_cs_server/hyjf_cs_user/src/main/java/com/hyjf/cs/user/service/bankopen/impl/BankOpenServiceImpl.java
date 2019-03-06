package com.hyjf.cs.user.service.bankopen.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.resquest.user.BankOpenRequest;
import com.hyjf.am.resquest.user.BankSmsLogRequest;
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.common.validator.ValidatorCheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.*;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.util.SignUtil;
import com.hyjf.cs.user.vo.BankOpenVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author xiasq
 * @version UserServiceImpl, v0.1 2018/4/11 9:34
 */

@Service
public class BankOpenServiceImpl extends BaseUserServiceImpl implements BankOpenService   {

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    private CommonProducer commonProducer;


    @Override
    public boolean checkIdNo(String idNo) {

        UserInfoVO userInfo = amUserClient.getUserByIdNo(idNo);

        if (userInfo != null) {
            return true;
        }

        return false;
    }


    @Override
    public int updateUserAccountLog(int userId, String userName, String mobile, String logOrderId, String clientPc, String name, String idno, String cardNo, String srvAuthCode) {

        BankOpenRequest bankOpenRequest = new BankOpenRequest();
        bankOpenRequest.setUserId(userId);
        bankOpenRequest.setUsername(userName);
        bankOpenRequest.setMobile(mobile);
        bankOpenRequest.setOrderId(logOrderId);
        bankOpenRequest.setChannel(clientPc);
        bankOpenRequest.setTrueName(name);
        bankOpenRequest.setIdNo(idno);
        bankOpenRequest.setCardNo(cardNo);
        bankOpenRequest.setSrvAuthCode(srvAuthCode);

        return amUserClient.updateUserAccountLog(bankOpenRequest);
    }

    /**
     * @Description 检查请求参数
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/15 17:28
     */
    @Override
    public void checkRequestParam(UserVO user, BankOpenVO bankOpenVO) {
        if (user == null) {
            CheckUtil.check(false,MsgEnum.ERR_OBJECT_GET,"用户信息");// 获取用户信息失败
            // throw new CheckException(MsgEnum.ERR_GET_USER);
        }
        if(user.getBankOpenAccount()!=null&&"1".equals(user.getBankOpenAccount())){
            // 用户已开户
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_ALREADY_OPEN);
        }
        // 手机号
        if (StringUtils.isEmpty(bankOpenVO.getMobile())) {
            throw new CheckException(MsgEnum.STATUS_ZC000001);
        }
        // 姓名
        if (StringUtils.isEmpty(bankOpenVO.getTrueName())) {
            throw new CheckException(MsgEnum.STATUS_ZC000007);
        }else{
            //判断真实姓名是否包含空格
            if (!ValidatorCheckUtil.verfiyChinaFormat(bankOpenVO.getTrueName())) {
                throw new CheckException(MsgEnum.STATUS_ZC000012);
            }
            //判断真实姓名的长度,不能超过10位
            if (bankOpenVO.getTrueName().length() > 10) {
                throw new CheckException(MsgEnum.STATUS_ZC000013);
            }
        }

        if(!Validator.isMobile(bankOpenVO.getMobile())){
            throw new CheckException(MsgEnum.ERR_FMT_MOBILE);
        }
    }

    /**
     * @Description 组装跳转江西银行的参数
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/15 17:20
     */
    @Override
    @HystrixCommand(commandKey = "开户(三端)-getOpenAccountMV",fallbackMethod = "fallBackBankOpen",ignoreExceptions = CheckException.class,commandProperties = {
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
    public Map<String,Object> getOpenAccountMV(OpenAccountPageBean openBean, String sign) {
        // 根据身份证号码获取性别
        String gender = "F";
        String idType = BankCallConstant.ID_TYPE_IDCARD;
        // 调用开户接口
        BankCallBean openAccoutBean =  new BankCallBean(openBean.getUserId(),BankCallConstant.TXCODE_ACCOUNT_OPEN_ENCRYPT_PAGE,Integer.parseInt(openBean.getPlatform()),BankCallConstant.BANK_URL_ACCOUNT_OPEN_ENCRYPT_PAGE);
        openAccoutBean.setIdentity(openBean.getIdentity());
        /**1：出借角色2：借款角色3：代偿角色*/
        openAccoutBean.setChannel(openBean.getChannel());
        openAccoutBean.setIdType(idType);
        openAccoutBean.setName(openBean.getTrueName());
        openAccoutBean.setGender(gender);
        openAccoutBean.setMobile(openBean.getMobile());
        // 代偿角色的账户类型为  00100-担保账户  其他的是 00000-普通账户
        openAccoutBean.setAcctUse(BankCallConstant.ACCOUNT_USE_COMMON);
        openAccoutBean.setIdentity(openBean.getIdentity());
        // 失败页面
        String errorPath = "/user/openError";
        // 成功页面
        String successPath = "/user/openSuccess";
        // 同步地址  是否跳转到前端页面
        String retUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + errorPath +"?logOrdId="+openAccoutBean.getLogOrderId()+"&sign=" +sign;
        String successUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + successPath;
        // 如果是移动端  返回别的url
        if((ClientConstants.APP_CLIENT+"").equals(openBean.getPlatform())||(ClientConstants.APP_CLIENT_IOS+"").equals(openBean.getPlatform())||(ClientConstants.WECHAT_CLIENT+"").equals(openBean.getPlatform())){
            errorPath = "/user/open/result/failed";
            successPath = "/user/open/result/success";
            // 同步地址  是否跳转到前端页面
            retUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + errorPath +"?status=99&statusDesc=&logOrdId="+openAccoutBean.getLogOrderId();
            successUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + successPath+"?status=000&statusDesc=";
            retUrl += "&token=1&sign=" +sign;
            successUrl += "&token=1&sign=" +sign;
        }
        String bgRetUrl = "http://CS-USER/hyjf-web/user/secure/open/bgReturn?phone=" + openBean.getMobile()+"&openclient="+openBean.getPlatform()+"&roleId="+openBean.getIdentity();
        openAccoutBean.setRetUrl(retUrl);
        openAccoutBean.setSuccessfulUrl(successUrl);
        openAccoutBean.setNotifyUrl(bgRetUrl);
        openAccoutBean.setCoinstName(openBean.getCoinstName()==null?"汇盈金服":openBean.getCoinstName());
        openAccoutBean.setLogRemark("开户+设密码页面");
        openAccoutBean.setLogIp(openBean.getIp());
        openBean.setOrderId(openAccoutBean.getLogOrderId());
        try {
            Map<String,Object> map = BankCallUtils.callApiMap(openAccoutBean);
            return map;
        } catch (Exception e) {
            throw new CheckException(MsgEnum.ERR_BANK_CALL);
        }
    }

    /**
     *
     * @param openBean
     * @param sign
     * @return
     */
    public Map<String,Object> fallBackBankOpen(OpenAccountPageBean openBean, String sign){
        logger.info("==================已进入 开户（三端）fallBackBankOpen 方法================");
        return null;
    }

    /**
     * @Description 开户异步逻辑处理
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/15 17:25
     */
    @Override
    public BankCallResult openAccountBgReturn(BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        int userId = Integer.parseInt(bean.getLogUserId());
        // 银行返回响应代码
        String retCode = StringUtils.isNotBlank(bean.getRetCode()) ? bean.getRetCode() : "";
        logger.info("页面开户异步处理start,UserId:{} 开户平台为：{} 银行返回响应代码为：{} status为{}", bean.getLogUserId(), bean.getLogClient(),retCode,bean.getStatus());
        // State为0时候为0：交易失败 1：交易成功 2：开户成功设置交易密码失败
        if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode) || "0".equals(bean.getStatus())) {
            // 开户失败   将开户记录状态改为4
            // 查询失败原因
            String retMsg = bean.getRetMsg();
            BankReturnCodeConfigVO retMsgVo = amConfigClient.getBankReturnCodeConfig(retCode);
            if (retMsgVo != null) {
                retMsg = retMsgVo.getErrorMsg();
            }
            this.amUserClient.updateUserAccountLogState(userId, bean.getLogOrderId(), 4,retCode,retMsg);
            logger.info("开户失败，失败原因:银行返回响应代码:[" + retCode + "],订单号:[" + bean.getLogOrderId() + "].");
            result.setStatus(false);
            return result;
        }
        // 开户成功后,保存用户的开户信息
        Integer saveBankAccountFlag = this.amUserClient.saveUserAccount(bean);
        if (saveBankAccountFlag.intValue() != 1) {
            logger.info("开户失败,保存用户的开户信息失败:[" + retCode + "],订单号:[" + bean.getLogOrderId() + "].");
            result.setStatus(true);
            result.setMessage("开户失败,保存用户开户信息失败");
            return result;
        }
        // 更新account表的电子帐户号
        Integer saveResult = amTradeClient.updateAccountNumberByUserId(userId,bean.getAccountId());

        // 更新redis里面的值
        WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY + userId, WebViewUserVO.class);
        if (user != null) {
            user.setBankOpenAccount(true);
            UserVO userVO = this.amUserClient.findUserById(userId);
            //update by cwyang 2019-01-30
            if(saveResult == 0){
                sendMqToSaveAccount(userId,userVO.getUsername(),bean.getAccountId());
            }
            //end
            // add by liuyang 神策数据统计追加 20180927 start
            if ("10000000".equals(userVO.getInstCode())) {
                if (!RedisUtils.exists(RedisConstants.SENSORS_DATA_OPEN_ACCOUNT + userId)) {
                    try {
                        RedisUtils.sadd(RedisConstants.SENSORS_DATA_OPEN_ACCOUNT + userId, String.valueOf(userId));
                        // 开户成功后,发送神策数据统计MQ
                        SensorsDataBean sensorsDataBean = new SensorsDataBean();
                        sensorsDataBean.setUserId(userId);
                        sensorsDataBean.setEventCode("open_success");
                        this.sendSensorsDataMQ(sensorsDataBean);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
            }
            // add by liuyang 神策数据统计追加 20180927 end
            // 开户+设密的话   状态改为已设置交易密码
            if (BankCallConstant.TXCODE_ACCOUNT_OPEN_ENCRYPT_PAGE.equals(bean.getTxCode())
                    && "1".equals(bean.getStatus())) {
                user.setIsSetPassword(1);
            }
            user.setRoleId(bean.getIdentity());
            RedisUtils.setObj(RedisConstants.USERID_KEY + userId, user);

        }
        // 查询银行卡绑定信息
        Integer saveBankCardFlag = saveCardNoToBank(bean);
        if (saveBankCardFlag.intValue() != 1) {
            logger.info("开户失败,保存银行卡信息失败:[" + retCode + "],订单号:[" + bean.getLogOrderId() + "].");
            // 这里是不是写false？
            result.setStatus(true);
            result.setMessage("开户失败,保存银行卡信息失败");
            return result;
        }

        try {
            // 加入到消息队列
            Map<String, String> params = new HashMap<String, String>();
            params.put("mqMsgId", GetCode.getRandomCode(10));
            params.put("userId", String.valueOf(userId));
            params.put("accountId", bean.getAccountId());
            logger.info("开户异步处理，发送MQ，userId:[{}],mqMgId:[{}]",userId,params.get("mqMsgId"));
            commonProducer.messageSend(new MessageContent(MQConstant.FDD_CERTIFICATE_AUTHORITY_TOPIC, UUID.randomUUID().toString(), params));
        } catch (MQException e) {
            logger.error("用户开户后，发送【法大大CA认证】MQ消息失败！userId:[{}]",userId);
        }
        result.setStatus(true);
        result.setMessage("开户成功");
        logger.info("页面开户异步处理end,UserId:{} 开户平台为：{}", bean.getLogUserId(),bean.getLogClient());
        return result;
    }

    /**
     * 开户更新account表异常，再次插入账户表
     *
     * @param userId
     * @throws MQException
     */
    private void sendMqToSaveAccount(int userId, String userName,String accountID) {
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
        account.setAccountId(accountID);
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

        logger.info("开户更新account表异常，再次插入account：{}", JSON.toJSONString(account));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_TOPIC, UUID.randomUUID().toString(), account));
        } catch (MQException e) {
            logger.error("开户更新account表异常，推送account——mq失败.... user_id is :{}", userId);
//            throw new RuntimeException("开户更新account表异常，推送account——mq失败...");
        }
    }

    /**
     * 开户成功后,发生神策数据统计MQ
     *
     * @param sensorsDataBean
     */
    private void sendSensorsDataMQ(SensorsDataBean sensorsDataBean) throws MQException {
        this.commonProducer.messageSendDelay(new MessageContent(MQConstant.SENSORSDATA_OPEN_ACCOUNT_TOPIC, UUID.randomUUID().toString(), sensorsDataBean), 2);
    }


    /**
     * 保存用户银行卡信息
     * @param bean
     * @return
     */
    private Integer saveCardNoToBank(BankCallBean bean) {
        Integer userId = Integer.parseInt(bean.getLogUserId());
        logger.info("保存用户银行卡信息  userId {}   ",userId);
        UserVO user = this.getUsersById(userId);
        // 调用江西银行接口查询用户绑定的银行卡
        BankCallBean cardBean = new BankCallBean(userId, BankCallConstant.TXCODE_CARD_BIND_DETAILS_QUERY, bean.getLogClient());
        cardBean.setChannel(bean.getChannel());// 交易渠道
        cardBean.setAccountId(bean.getAccountId());// 存管平台分配的账号
        cardBean.setState("1"); // 查询状态 0-所有（默认） 1-当前有效的绑定卡
        cardBean.setLogRemark("银行卡关系查询");
        // 调用江西银行查询银行卡
        BankCallBean call = BankCallUtils.callApiBg(cardBean);
        String respCode = call == null ? "" : call.getRetCode();
        logger.info("保存用户银行卡信息  银行返回码  {}   ",respCode);
        // 如果调用成功
        if (BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
            String usrCardInfolist = call.getSubPacks();
            if (!StringUtils.isEmpty(usrCardInfolist)) {
                JSONArray array = JSONObject.parseArray(usrCardInfolist);
                if (array != null && array.size() > 0) {
                    // 查询有结果  取第一条
                    JSONObject obj = null;
                    obj = array.getJSONObject(0);
                    BankCardRequest bank = new BankCardRequest();
                    bank.setUserId(userId);
                    // 设置绑定的手机号
                    bank.setMobile(bean.getMobile());
                    bank.setUserName(user.getUsername());
                    bank.setStatus(1);// 默认都是1
                    bank.setCardNo(obj.getString("cardNo"));
                    // 银行联号
                    String payAllianceCode = "";
                    // 调用江西银行接口查询银行联号
                    BankCallBean payAllianceCodeQueryBean = payAllianceCodeQuery(obj.getString("cardNo"), userId, bean);
                    if (payAllianceCodeQueryBean != null && BankCallConstant.RESPCODE_SUCCESS.equals(payAllianceCodeQueryBean.getRetCode())) {
                        payAllianceCode = payAllianceCodeQueryBean.getPayAllianceCode();
                    } else {
                        logger.error("调用江西银行查询联行号失败，userId:{}", userId);
                    }
                    SimpleDateFormat sdf = GetDate.yyyymmddhhmmss;
                    try {
                        bank.setCreateTime(sdf.parse(obj.getString("txnDate") + obj.getString("txnTime")));
                    } catch (ParseException e) {
                        logger.error("银行返回日期格式化失败，userId:{}", userId);
                    }
                    bank.setCreateUserId(userId);
                    bank.setCreateUsername(user.getUsername());
                    // 根据银行卡号查询所  bankId
                    // 调用config原子层
                    String bankId = amConfigClient.getBankIdByCardNo(bank.getCardNo());
                    logger.info("保存用户银行卡信息  bankId  {}   ",bankId);
                    if (!StringUtils.isEmpty(bankId)) {
                        bank.setBankId(Integer.parseInt(bankId));
                        JxBankConfigVO banksConfigVO = amConfigClient.getJxBankConfigById(Integer.parseInt(bankId));
                        if (banksConfigVO != null) {
                            bank.setBank(banksConfigVO.getBankName());
                            logger.info("保存用户银行卡所属银行  banksConfigVO.getBankName()  {}   ",banksConfigVO.getBankName());
                            // 如果联行号为空  则更新联行号
                            if (StringUtils.isEmpty(payAllianceCode)) {
                                payAllianceCode = banksConfigVO.getPayAllianceCode();
                            }
                        }
                    }
                    // 更新联行号
                    bank.setPayAllianceCode(payAllianceCode);
                    return amUserClient.saveCardNoToBank(bank);
                } else {
                    logger.error("更新银行卡信息出错，转换array失败，userId:{}", userId);
                }
            } else {
                logger.error("更新银行卡信息出错，返回空，userId:{}", userId);
            }
        }
        return 0;
    }

    /**
     * 调用江西银行查询联行号
     * @param cardNo
     * @param userId
     * @param bean
     * @return
     */
    private BankCallBean payAllianceCodeQuery(String cardNo, Integer userId, BankCallBean bean) {
        BankCallBean codeBean = new BankCallBean(userId,BankCallConstant.TXCODE_PAY_ALLIANCE_CODE_QUERY,bean.getLogClient());
        String channel = bean.getChannel();
        codeBean.setChannel(channel);
        codeBean.setAccountId(cardNo);
        codeBean.setLogRemark("联行号查询");
        return BankCallUtils.callApiBg(codeBean);
    }

    @Override
    public Map<String, String> checkApiParam(ApiBankOpenRequestBean requestBean) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "0");
        if (Validator.isNull(requestBean.getInstCode())) {
            logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("statusDesc", "机构编号不能为空");
            return resultMap;
        }
        // 手机号
        if (Validator.isNull(requestBean.getMobile())) {
            logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "手机号不能为空");
            return resultMap;
        }
        if (Validator.isNull(requestBean.getRetUrl())) {
            logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "同步地址不能为空");
            return resultMap;
        }
        if (Validator.isNull(requestBean.getBgRetUrl())) {
            logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "异步地址不能为空");
            return resultMap;
        }
        // 姓名
        if (Validator.isNull(requestBean.getTrueName())) {
            logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "真实姓名不能为空");
            return resultMap;
        }
        // 渠道
        if (Validator.isNull(requestBean.getChannel())) {
            logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "渠道号不能为空");
            return resultMap;
        }
        // 验签
        /*if (!this.verifyRequestSign(payRequestBean, "/server/autoPlus/userAuthInves")) {
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000002, "验签失败"));
            logger.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
            return getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE000002);
        }*/
        if(!this.verifyRequestSign(requestBean, "/server/user/accountOpenEncryptPage/open.do")){
            logger.info("验签失败[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "验签失败！");
            return resultMap;
        }
        // 判断真实姓名是否包含特殊字符
        if (!ValidatorCheckUtil.verfiyChinaFormat(requestBean.getTrueName())) {
            logger.info("真实姓名包含特殊字符[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "真实姓名包含特殊字符");
            return resultMap;
        }
        // 判断真实姓名的长度,不能超过10位
        if (requestBean.getTrueName().length() > 10) {
            logger.info("真实姓名不能超过10位[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "真实姓名不能超过10位");
            return resultMap;
        }
        // 判断身份属性
        if (Validator.isNull(requestBean.getIdentity()) || (!"1".equals(requestBean.getIdentity())
                && !"2".equals(requestBean.getIdentity()) && !"3".equals(requestBean.getIdentity()))) {
            logger.info("身份属性参数错误[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "身份属性参数错误");
            return resultMap;
        }
        // 根据手机号查询用户
        UserVO user = this.amUserClient.findUserByMobile(requestBean.getMobile());
        if (user == null) {
            logger.info("根据手机号查询用户失败[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_ZC000021);
            resultMap.put("mess", "根据手机号查询用户失败");
            return resultMap;
        }
        resultMap.put("status", "1");
        return resultMap;
    }

    private String replaceIdNo(String idNo) {
        String lastString = idNo.substring(idNo.length() - 1);
        if ("x".equalsIgnoreCase(lastString)) {
            idNo = idNo.replace(idNo.charAt(idNo.length() - 1) + "", "X");
        }
        return idNo;
    }

    /**
     * @param logOrdId
     * @Description 根据 logOrdId查询失败原因
     * @Author sunss
     * @Date 2018/6/21 15:34
     */
    @Override
    public WebResult<Object> getFiledMess(String logOrdId,int userId) {
        WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY + userId, WebViewUserVO.class);
        if(user!=null){
            if(user.isBankOpenAccount()&&"0".equals(user.getIsSetPassword()+"")){
                // 已经开户  未设置交易密码  开户成功，设置交易密码失败
                throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_ERROR);
            }
        }
        WebResult<Object> result = new WebResult<Object>();
        String errorMess = amUserClient.getBankOpenAccountFiledMess(logOrdId);
        result.setStatus(WebResult.SUCCESS);
        Map<String,String> map = new HashedMap();
        map.put("error",errorMess);
        result.setData(map);
        return result;
    }

    /**
     * 获得担保机构开户调用银行的参数
     *
     * @param openBean
     * @return
     */
    @Override
    public Map<String, Object> getAssureOpenAccountMV(OpenAccountPageBean openBean,String sign) {
        {
            // 根据身份证号码获取性别
            String gender = "M";
            // 获取共同参数
            String idType = BankCallConstant.ID_TYPE_IDCARD;
            // 调用开户接口
            BankCallBean openAccoutBean = new BankCallBean(openBean.getUserId(), BankCallConstant.TXCODE_ACCOUNT_OPEN_ENCRYPT_PAGE, Integer.parseInt(openBean.getPlatform()), BankCallConstant.BANK_URL_ACCOUNT_OPEN_ENCRYPT_PAGE);
            openAccoutBean.setIdentity(openBean.getIdentity());
            /**1：出借角色2：借款角色3：代偿角色*/
            openAccoutBean.setChannel(openBean.getChannel());
            openAccoutBean.setIdType(idType);
            openAccoutBean.setName(openBean.getTrueName());
            openAccoutBean.setGender(gender);
            openAccoutBean.setMobile(openBean.getMobile());
            // 代偿角色的账户类型为  00100-担保账户  其他的是 00000-普通账户
            openAccoutBean.setAcctUse(BankCallConstant.ACCOUNT_USE_GUARANTEE);
            openAccoutBean.setIdentity(openBean.getIdentity());
            // 同步地址  是否跳转到前端页面
            // 失败页面
            String errorPath = "/user/openError";
            // 成功页面
            String successPath = "/user/openSuccess";
            // 同步地址  是否跳转到前端页面
            String retUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + errorPath +"?logOrdId="+openAccoutBean.getLogOrderId()+"&sign=" +sign;
            String successUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + successPath;
            // 异步调用路
            String bgRetUrl = "http://CS-USER/hyjf-web/user/secure/open/bgReturn?phone=" + openBean.getMobile() +"&openclient="+openBean.getPlatform()+"&roleId="+openBean.getIdentity();
            openAccoutBean.setRetUrl(retUrl);
            openAccoutBean.setSuccessfulUrl(successUrl);
            openAccoutBean.setNotifyUrl(bgRetUrl);
            openAccoutBean.setCoinstName(openBean.getCoinstName() == null ? "汇盈金服" : openBean.getCoinstName());
            openAccoutBean.setLogRemark("担保机构页面开户");
            openAccoutBean.setLogIp(openBean.getIp());
            openBean.setOrderId(openAccoutBean.getLogOrderId());
            try {
                Map<String, Object> map = BankCallUtils.callApiMap(openAccoutBean);
                return map;
            } catch (Exception e) {
                throw new CheckException(MsgEnum.ERR_BANK_CALL);
            }
        }
    }

    /**
     * 获得借款人开户调用银行的参数
     *
     * @param openBean
     * @return
     */
    @Override
    public Map<String, Object> getLoanOpenAccountMV(OpenAccountPageBean openBean,String sign) {
        {
            // 根据身份证号码获取性别
            String gender = "M";
            // 获取共同参数
            String idType = BankCallConstant.ID_TYPE_IDCARD;
            // 调用开户接口
            BankCallBean openAccoutBean = new BankCallBean(openBean.getUserId(), BankCallConstant.TXCODE_ACCOUNT_OPEN_ENCRYPT_PAGE, Integer.parseInt(openBean.getPlatform()), BankCallConstant.BANK_URL_ACCOUNT_OPEN_ENCRYPT_PAGE);
            openAccoutBean.setIdentity(openBean.getIdentity());
            /**1：出借角色2：借款角色3：代偿角色*/
            openAccoutBean.setChannel(openBean.getChannel());
            openAccoutBean.setIdType(idType);
            openAccoutBean.setName(openBean.getTrueName());
            openAccoutBean.setGender(gender);
            openAccoutBean.setMobile(openBean.getMobile());
            // 代偿角色的账户类型为  00100-担保账户  其他的是 00000-普通账户
            openAccoutBean.setAcctUse(BankCallConstant.ACCOUNT_USE_COMMON);
            openAccoutBean.setIdentity(openBean.getIdentity());
            // 失败页面
            String errorPath = "/user/openError";
            // 成功页面
            String successPath = "/user/openSuccess";
            // 同步地址  是否跳转到前端页面
            String retUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + errorPath +"?logOrdId="+openAccoutBean.getLogOrderId()+"&sign=" +sign;
            String successUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + successPath;
            // 异步调用路
            String bgRetUrl = "http://CS-USER/hyjf-web/user/secure/open/bgReturn?phone=" + openBean.getMobile() +"&openclient="+openBean.getPlatform()+"&roleId="+openBean.getIdentity();
            openAccoutBean.setRetUrl(retUrl);
            openAccoutBean.setSuccessfulUrl(successUrl);
            openAccoutBean.setNotifyUrl(bgRetUrl);
            openAccoutBean.setCoinstName(openBean.getCoinstName() == null ? "汇盈金服" : openBean.getCoinstName());
            openAccoutBean.setLogRemark("借款人页面开户");
            openAccoutBean.setLogIp(openBean.getIp());
            openBean.setOrderId(openAccoutBean.getLogOrderId());
            try {
                Map<String, Object> map = BankCallUtils.callApiMap(openAccoutBean);
                return map;
            } catch (Exception e) {
                throw new CheckException(MsgEnum.ERR_BANK_CALL);
            }
        }
    }

    @Override
    public OpenAccountPlusResult checkAndUpdateForSendCode(OpenAccountPlusRequest openAccountRequestBean, String ipAddr){
        OpenAccountPlusResult resultBean = new OpenAccountPlusResult();
        // 手机号
        String mobile = openAccountRequestBean.getMobile();
        // 真实姓名
        String trueName = openAccountRequestBean.getTrueName();
        // 身份证号
        String idNo = openAccountRequestBean.getIdNo();
        // 推荐人
        String referee = openAccountRequestBean.getReferee();
        // 推广平台
        String utmId = openAccountRequestBean.getUtmId();
        // 注册渠道
        String channel = openAccountRequestBean.getChannel();
        // 注册平台
        String platform = openAccountRequestBean.getPlatform();
        // 机构编号
        String instCode = openAccountRequestBean.getInstCode();
        // 第三方绑定用户id
        Integer bindUniqueId = openAccountRequestBean.getBindUniqueId();

        UtmPlatVO utmPlat = null;
        try {
            // 根据渠道号检索推广渠道
            utmPlat = this.amUserClient.selectUtmPlatByUtmId(utmId);
        } catch (Exception e) {
            logger.error("查询渠道异常：", e);
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
            resultBean.setStatusDesc("第三方操作平台非法");
            return resultBean;
        }
        if (utmPlat == null) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
            resultBean.setStatusDesc("第三方操作平台非法");
            return resultBean;
        }

        // 根据机构编号检索机构信息
        HjhInstConfigVO instConfig = this.amTradeClient.selectInstConfigByInstCode(instCode);
        if (instConfig == null) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
            resultBean.setStatusDesc("机构编号非法");
            return resultBean;
        }

        // 如果有推荐人，校验推荐人正确性
        UserVO refereeUser = null;
        if (!StringUtils.isBlank(referee)) {
            if(CommonUtils.isMobile(referee)){
                refereeUser = amUserClient.findUserByMobile(referee);
            }else {
                refereeUser = amUserClient.findUserById(Integer.parseInt(referee));
            }

            if (refereeUser == null) {
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                resultBean.setStatusDesc("推荐人无效");
                return resultBean;
            }
        }

        // 验签
        if (!this.verifyRequestSign(openAccountRequestBean, "/register")) {
            logger.info("----验签失败----");
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            resultBean.setStatusDesc("验签失败！");
            return resultBean;
        }

        // 查询绑定关系
        BindUserVo bindUser = amUserClient.getBindUser(bindUniqueId, Integer.parseInt(utmId));

        // 根据手机号查询用户
        UserVO user = amUserClient.findUserByMobile(mobile);
        if (user == null) {
            // 检查用户身份证号是否开户（身份证存在代表开户成功）
            boolean checkIdNo = checkIdNo(idNo);
            if (!checkIdNo) {
                // 身份证不存在，在汇盈金服平台注册
                RegisterUserRequest registerUserRequest = new RegisterUserRequest();
                BeanUtils.copyProperties(openAccountRequestBean, registerUserRequest);
                registerUserRequest.setUtmId(channel);
                registerUserRequest.setReffer(referee);
                registerUserRequest.setLoginIp(ipAddr);
                UserVO userVO = amUserClient.register(registerUserRequest);
                if (userVO == null) {
                    logger.info("用户注册失败");
                    resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                    resultBean.setStatusDesc("用户注册失败，请重试");
                    return resultBean;
                }
                UserVO checkUser = amUserClient.findUserById(userVO.getUserId());
                if (checkUser == null) {
                    logger.info("根据用户ID获取用户信息表失败,用户ID:[" + userVO.getUserId() + "]");
                    resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                    resultBean.setStatusDesc("注册失败");
                    return resultBean;
                }

                // 用户名
                String userName = checkUser.getUsername();

                // 调用江西银行短信验证码接口
                resultBean = this.callBankSendSms(mobile, userVO.getUserId(), userName, channel, platform);
                if(!ErrorCodeConstant.SUCCESS.equals(resultBean.getStatus())){
                    return resultBean;
                }


                if (bindUser == null) {
                    // 绑定用户
                    Boolean result = amUserClient.bindThirdUser(userVO.getUserId(), bindUniqueId, Integer.parseInt(utmId));
                    if (!result) {
                        resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_ZCOOOO22);
                        resultBean.setStatusDesc("绑定用户失败");
                        return resultBean;
                    }
                }

            } else {
                // 身份证号已存在
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_ZC000005);
                resultBean.setStatusDesc("身份证已开户，请保持手机号一致");
                return resultBean;
            }

        } else {
            Integer userId = user.getUserId();
            // 手机号已注册，判断用户身份证号是否存在
            boolean idNo1 = this.checkIdNo(idNo);
            if (!idNo1) {
                String userName = user.getUsername();
                // 调用江西银行短信验证码接口
                resultBean =  this.callBankSendSms(mobile, userId, userName, channel, platform);
                if(resultBean != null && !ErrorCodeConstant.SUCCESS.equals(resultBean.getStatus())){
                    return resultBean;
                }
            } else {
                // 判断注册手机号与已存在的手机号是否相同
                UserInfoVO userInfoVO = amUserClient.getUserByIdNo(idNo);
                if(userInfoVO != null){
                    UserVO users = amUserClient.findUserById(userInfoVO.getUserId());
                    if (users != null && mobile.equals(users.getMobile())) {
                        resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_ZC000005);
                        resultBean.setStatusDesc("手机号已被他人注册,请更换手机号");
                        return resultBean;
                    } else {
                        resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_ZC000023);
                        resultBean.setStatusDesc("已开户，无需再次开户");

                        // 已开户的用户，需要返回电子账号和银联行号
                        if(null == users.getUserId()){
                            logger.info("定位一下错误，请勿删除 -> userId:[{}]",users.getUserId());
                        }
                        BankOpenAccountVO bankOpenAccountVO = amUserClient.selectBankAccountById(users.getUserId());
                        resultBean.setAccountId(bankOpenAccountVO.getAccount());
                        BankCardVO bankCardVO = amUserClient.getBankCardByUserId(users.getUserId());
                        if(bankCardVO != null){
                            resultBean.setPayAllianceCode(bankCardVO.getPayAllianceCode());
                        }
                        if (users.getIsSetPassword() != null) {
                            resultBean.setIsSetPassword(String.valueOf(users.getIsSetPassword()));
                        }
                        HjhUserAuthVO hjhUserAuthVO = amUserClient.getHjhUserAuthByUserId(users.getUserId());
                        resultBean.setAutoInvesStatus(String.valueOf(hjhUserAuthVO.getAutoInvesStatus()));
                        resultBean.setTrueName(userInfoVO.getTruename());
                        return resultBean;
                    }
                }


            }

            if (bindUser == null) {
                // 绑定关系不存在，则绑定
                Boolean result = amUserClient.bindThirdUser(userId, bindUniqueId, Integer.parseInt(utmId));
                if (!result) {
                    resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_ZCOOOO22);
                    resultBean.setStatusDesc("绑定用户失败");
                    return resultBean;
                }
            }
        }

        return resultBean;
    }
    @Override
    public String getBankOpenAccountByMobile(String mobile) {
        try {
            BankCallBean bean = new BankCallBean();
            bean.setLogOrderId(GetOrderIdUtils.getOrderId2(1));
            bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
            bean.setLogUserId(StringUtil.valueOf(1));
            bean.setLogRemark("根据手机号查询银行电子账户号");
            bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
            bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
            bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
            bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
            bean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道
            bean.setVersion(ChinaPnrConstant.VERSION_10);
            bean.setTxCode(BankCallMethodConstant.TXCODE_ACCOUNT_QUERY_BY_MOBILE);
            bean.setMobile(mobile);
            // 调用汇付接口
            BankCallBean retBean = BankCallUtils.callApiBg(bean);
            return retBean.getAccountId();
        } catch (Exception e) {
            logger.info("开户同步步处理,mobile:{}", "根据手机号查询电子账户信息失败"+mobile);
            throw new CheckException(MsgEnum.ERR_BANK_CALL);
        }

    }

    /**
     * 调用银行接口发送验证码
     */
    private OpenAccountPlusResult callBankSendSms(String mobile, Integer userId, String userName,
                                                  String utmId, String platform) {
        OpenAccountPlusResult resultBean = new OpenAccountPlusResult();
        // 发送短信订单
        String orderId = GetOrderIdUtils.getOrderId2(userId);
        UserVO userVO = amUserClient.findUserById(userId);
        UserInfoVO userInfoVO = amUserClient.findUserInfoById(userId);
        // 插入开户记录表
        int uflag = this.updateUserAccountLog(userId, userName, userVO.getMobile(), orderId,platform ,userInfoVO.getTruename(),userInfoVO.getIdcard(),"", "");
        if (uflag <= 0) {
            logger.info("插入开户记录表失败,手机号:[" + mobile + "].");
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            resultBean.setStatusDesc("发送短信验证码失败");
            return resultBean;
        }
        try {
            // 调用江西银行发送短信接口
            BankCallBean bankCallBean = this.sendOpenAccountSms(userId, orderId,
                    BankCallConstant.TXCODE_ACCOUNT_OPEN_PLUS, mobile, utmId);
            if (Validator.isNull(bankCallBean)) {
                logger.info("调用银行发送短信接口失败");
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                resultBean.setStatusDesc("发送短信验证码失败");
                return resultBean;
            }
            // 短信发送返回结果码
            String retCode = bankCallBean.getRetCode();
            if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode)
                    && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
                logger.info("开户发送短信验证码,手机号:[" + mobile + "],银行返回结果:retCode:[" + retCode + "]");
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                resultBean.setStatusDesc("发送短信验证码失败");
                return resultBean;
            }
            if (Validator.isNull(bankCallBean.getSrvAuthCode())
                    && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
                logger.info("开户发送短信验证码,手机号:[" + mobile + "],银行返回结果:retCode:[" + retCode + "],前导业务授权码:"
                        + bankCallBean.getSrvAuthCode());
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                resultBean.setStatusDesc("发送短信验证码失败");
                return resultBean;
            }
            // 业务授权码
            String srvAuthCode = bankCallBean.getSrvAuthCode();
            if (Validator.isNotNull(srvAuthCode)) {
                // 更新用户开户日志,更新前导业务授权码
                uflag = this.updateUserAccountLog(userId, userName, userVO.getMobile(), orderId,platform ,userInfoVO.getTruename(),userInfoVO.getIdcard(),"", srvAuthCode);
                if (uflag<=0) {
                    logger.info("保存开户日志失败,更新前导业务授权码,手机号:[" + mobile + "],前导业务授权码:[" + srvAuthCode + "],订单号:[" + orderId
                            + "]");
                    resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                    resultBean.setStatusDesc("发送短信验证码失败");
                    return resultBean;
                }
            }
            logger.info("发送短信验证码成功,手机号:[" + mobile + "],前导业务授权码:[" + srvAuthCode + "],订单号:[" + orderId + "]");
            resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            resultBean.setStatusDesc("发送短信验证码成功");
            resultBean.setOrderId(orderId);// 平台返回的唯一订单号
        } catch (Exception e) {
            logger.info("发送短信验证码异常,手机号:[" + mobile + "],异常信息:[" + e.getMessage() + "]");
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            resultBean.setStatusDesc("发送短信验证码失败");
            return resultBean;
        }

        return null;

    }

    /**
     * 调用银行发送短信接口
     *
     * @param userId
     * @param orderId
     * @param srvTxCode
     * @param mobile
     * @param channel
     * @return
     */
    @Override
    public BankCallBean sendOpenAccountSms(Integer userId, String orderId, String srvTxCode, String mobile, String channel) {
        // 调用存管接口发送验证码
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallMethodConstant.TXCODE_SMSCODE_APPLY);// 交易代码cardBind
        bean.setTxDate(GetOrderIdUtils.getOrderDate());// 交易日期
        bean.setTxTime(GetOrderIdUtils.getOrderTime());// 交易时间
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
        bean.setChannel(channel);// 交易渠道000001手机APP 000002网页
        bean.setSrvTxCode(srvTxCode);
        bean.setMobile(mobile);// 交易渠道
        bean.setLogOrderId(orderId);// 订单号
        bean.setLogRemark("发送短信验证码");
        bean.setLogUserId(String.valueOf(userId));// 请求用户名
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
            if (Validator.isNotNull(mobileBean.getSrvAuthCode())) {
                BankSmsLogRequest request = new BankSmsLogRequest();
                request.setSrvAuthCode(srvAuthCode);
                request.setSrvTxCode(srvTxCode);
                request.setUserId(userId);
                request.setSmsSeq(mobileBean.getSmsSeq());
                boolean smsFlag = amUserClient.updateBankSmsLog(request);
                if (smsFlag) {
                    return mobileBean;
                }
            } else {
                // 保存用户开户日志
                BankSmsLogRequest request = new BankSmsLogRequest();
                request.setSrvAuthCode(bean.getSrvAuthCode());
                request.setSrvTxCode(bean.getTxCode());
                request.setUserId(Integer.parseInt(bean.getLogUserId()));
                srvAuthCode = amUserClient.selectBankSmsLog(request);
                if (Validator.isNull(srvAuthCode)) {
                    return null;
                } else {
                    mobileBean.setSrvAuthCode(srvAuthCode);
                    return mobileBean;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * AEMS系统:用户开户参数校验
     *
     * @param requestBean
     * @return
     */
    @Override
    public Map<String, String> checkAemsOpenBankAccountParam(@Valid AemsBankOpenEncryptPageRequestBean requestBean) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "0");
        if (Validator.isNull(requestBean.getInstCode())) {
            logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("statusDesc", "机构编号不能为空");
            return resultMap;
        }
        // 手机号
        if (Validator.isNull(requestBean.getMobile())) {
            logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "手机号不能为空");
            return resultMap;
        }
        if (Validator.isNull(requestBean.getRetUrl())) {
            logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "同步地址不能为空");
            return resultMap;
        }
        if (Validator.isNull(requestBean.getBgRetUrl())) {
            logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "异步地址不能为空");
            return resultMap;
        }
        // 姓名
        if (Validator.isNull(requestBean.getTrueName())) {
            logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "真实姓名不能为空");
            return resultMap;
        }
        // 渠道
        if (Validator.isNull(requestBean.getChannel())) {
            logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "渠道号不能为空");
            return resultMap;
        }
        // 验签
        if(!SignUtil.AEMSVerifyRequestSign(requestBean, "/aems/encryptpage/open")){
            logger.info("验签失败[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "验签失败！");
            return resultMap;
        }
        // 判断真实姓名是否包含特殊字符
        if (!ValidatorCheckUtil.verfiyChinaFormat(requestBean.getTrueName())) {
            logger.info("真实姓名包含特殊字符[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "真实姓名包含特殊字符");
            return resultMap;
        }
        // 判断真实姓名的长度,不能超过10位
        if (requestBean.getTrueName().length() > 10) {
            logger.info("真实姓名不能超过10位[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "真实姓名不能超过10位");
            return resultMap;
        }
        // 判断身份属性
        if (Validator.isNull(requestBean.getIdentity()) || (!"1".equals(requestBean.getIdentity())
                && !"2".equals(requestBean.getIdentity()) && !"3".equals(requestBean.getIdentity()))) {
            logger.info("身份属性参数错误[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "身份属性参数错误");
            return resultMap;
        }
        // 根据手机号查询用户
        UserVO user = this.amUserClient.findUserByMobile(requestBean.getMobile());
        if (user == null) {
            logger.info("根据手机号查询用户失败[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_ZC000021);
            resultMap.put("mess", "根据手机号查询用户失败");
            return resultMap;
        }
        resultMap.put("status", "1");
        return resultMap;
    }

}
