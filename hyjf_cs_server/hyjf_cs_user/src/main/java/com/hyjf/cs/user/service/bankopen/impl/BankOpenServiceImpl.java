package com.hyjf.cs.user.service.bankopen.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.resquest.user.BankOpenRequest;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.common.validator.ValidatorCheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.ApiBankOpenRequestBean;
import com.hyjf.cs.user.bean.OpenAccountPageBean;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.mq.producer.FddCertificateProducer;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.vo.BankOpenVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private SystemConfig systemConfig;

    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    private FddCertificateProducer fddCertificateProducer;


    @Override
    public boolean checkIdNo(String idNo) {

        UserInfoVO userInfo = amUserClient.getUserByIdNo(idNo);

        if (userInfo != null) {
            return true;
        }

        return false;
    }


    @Override
    public int updateUserAccountLog(int userId, String userName, String mobile, String logOrderId, String clientPc, String name, String idno, String cardNo) {

        BankOpenRequest bankOpenRequest = new BankOpenRequest();
        bankOpenRequest.setUserId(userId);
        bankOpenRequest.setUsername(userName);
        bankOpenRequest.setMobile(mobile);
        bankOpenRequest.setOrderId(logOrderId);
        bankOpenRequest.setChannel(clientPc);
        bankOpenRequest.setTrueName(name);
        bankOpenRequest.setIdNo(idno);
        bankOpenRequest.setCardNo(cardNo);

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
        // 身份证号
        if (StringUtils.isEmpty(bankOpenVO.getIdNo())) {
            throw new CheckException(MsgEnum.STATUS_ZC000008);
        }

        if (bankOpenVO.getIdNo().length() != 18) {
            throw new CheckException(MsgEnum.ERR_FMT_IDCARDNO);
        }
        String idNo = bankOpenVO.getIdNo().toUpperCase().trim();
        bankOpenVO.setIdNo(idNo);
        //增加身份证唯一性校验
        boolean isOnly = this.checkIdNo(idNo);
        if (isOnly) {
            throw new CheckException(MsgEnum.STATUS_ZC000014);
        }
        if(!Validator.isMobile(bankOpenVO.getMobile())){
            throw new CheckException(MsgEnum.ERR_FMT_MOBILE);
        }
        String mobile = user.getMobile();
        if (StringUtils.isBlank(mobile)) {
            if (StringUtils.isNotBlank(bankOpenVO.getMobile())) {
                if(!this.existUser(bankOpenVO.getMobile())){
                    mobile = bankOpenVO.getMobile();
                }else{
                    throw new CheckException(MsgEnum.ERR_MOBILE_EXISTS);
                }
            } else {
                throw new CheckException(MsgEnum.ERR_MOBILE_IS_NOT_REAL);
            }
        } else {
            if (StringUtils.isNotBlank(bankOpenVO.getMobile()) && !mobile.equals(bankOpenVO.getMobile())) {
                throw new CheckException(MsgEnum.ERR_MOBILE_IS_NOT_REAL);
            }
        }
    }

    /**
     * @Description 组装跳转江西银行的参数
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/15 17:20
     */
    @Override
    public Map<String,Object> getOpenAccountMV(OpenAccountPageBean openBean, String sign) {
        // 根据身份证号码获取性别
        String gender = "";
        int sexInt = Integer.parseInt(openBean.getIdNo().substring(16, 17));
        if (sexInt % 2 == 0) {
            gender = "F";
        } else {
            gender = "M";
        }
        // 获取共同参数
        String idType = BankCallConstant.ID_TYPE_IDCARD;
        // 调用开户接口
        BankCallBean openAccoutBean =  new BankCallBean(openBean.getUserId(),BankCallConstant.TXCODE_ACCOUNT_OPEN_PAGE,Integer.parseInt(openBean.getPlatform()),BankCallConstant.BANK_URL_ACCOUNT_OPEN_PAGE);
        openAccoutBean.setIdentity(openBean.getIdentity());
        /**1：出借角色2：借款角色3：代偿角色*/
        openBean.setIdentity("1");
        openAccoutBean.setChannel(openBean.getChannel());
        openAccoutBean.setIdType(idType);
        openAccoutBean.setIdNo(openBean.getIdNo());
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
        String retUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + errorPath +"?logOrdId="+openAccoutBean.getLogOrderId();
        String successUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + successPath;
        // 如果是移动端  返回别的url
        if((ClientConstants.APP_CLIENT+"").equals(openBean.getPlatform())||(ClientConstants.APP_CLIENT_IOS+"").equals(openBean.getPlatform())||(ClientConstants.CLIENT_HEADER_WX+"").equals(openBean.getPlatform())){
            errorPath = "/user/open/result/fail";
            successPath = "/user/open/result/success";
            // 同步地址  是否跳转到前端页面
            retUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + errorPath +"?status=99&statusDesc=开户失败&logOrdId="+openAccoutBean.getLogOrderId();
            successUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + successPath+"?status=000&statusDesc=开户成功";
            retUrl += "&token=1&sign=" +sign;
            successUrl += "&token=1&sign=" +sign;
        }
        // 异步调用路
        String bgRetUrl = systemConfig.getWebHost()+"/user/secure/open/bgReturn?phone=" + openBean.getMobile();
        openAccoutBean.setRetUrl(retUrl);
        openAccoutBean.setSuccessfulUrl(successUrl);
        openAccoutBean.setNotifyUrl(bgRetUrl);
        openAccoutBean.setCoinstName(openBean.getCoinstName()==null?"汇盈金服":openBean.getCoinstName());
        openAccoutBean.setLogRemark("页面开户");
        openAccoutBean.setLogIp(openBean.getIp());
        openBean.setOrderId(openAccoutBean.getLogOrderId());
        try {
            Map<String,Object> map = BankCallUtils.callApiMap(openAccoutBean);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        logger.info("页面开户异步处理start,UserId:{} 开户平台为：{}", bean.getLogUserId(), bean.getLogClient());
        int userId = Integer.parseInt(bean.getLogUserId());
        // 银行返回响应代码
        String retCode = StringUtils.isNotBlank(bean.getRetCode()) ? bean.getRetCode() : "";
        // 开户失败
        if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
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
            fddCertificateProducer.messageSend(new MessageContent(MQConstant.FDD_CERTIFICATE_AUTHORITY_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(params)));
        } catch (MQException e) {
            logger.error("用户开户后，发送【法大大CA认证】MQ消息失败！userId:[{}]",userId);
        }
        result.setStatus(true);
        result.setMessage("开户成功");
        logger.info("页面开户异步处理end,UserId:{} 开户平台为：{}", bean.getLogUserId(),bean.getLogClient());
        return result;
    }


    /**
     * 保存用户银行卡信息
     * @param bean
     * @return
     */
    private Integer saveCardNoToBank(BankCallBean bean) {
        Integer userId = Integer.parseInt(bean.getLogUserId());
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
                    if (!StringUtils.isEmpty(bankId)) {
                        bank.setBankId(Integer.parseInt(bankId));
                        BanksConfigVO banksConfigVO = amConfigClient.getBankNameByBankId(bankId);
                        if (banksConfigVO != null) {
                            bank.setBank(banksConfigVO.getBankName());
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
            resultMap.put("mess", "机构编号不能为空");
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
        // 身份证号
        if (Validator.isNull(requestBean.getIdNo())) {
            logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            resultMap.put("mess", "身份证号不能为空");
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

        if (requestBean.getIdNo().length() != 18) {
            logger.info("身份证(18位)校验位错误[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_ZC000021);
            resultMap.put("mess", "身份证(18位)校验位错误");
            return resultMap;
        }
        String replaceIdNo = replaceIdNo(requestBean.getIdNo());
        requestBean.setIdNo(replaceIdNo);
        // 检查用户身份证号是否在汇盈已经存在
        UserInfoVO userInfo = amUserClient.getUserByIdNo(requestBean.getIdNo());
        if (userInfo!=null) {
            logger.info("身份证已存在[" + JSONObject.toJSONString(requestBean, true) + "]");
            resultMap.put("status", ErrorCodeConstant.STATUS_ZC000021);
            resultMap.put("mess", "身份证已存在");
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
    public WebResult<Object> getFiledMess(String logOrdId) {
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
    public Map<String, Object> getAssureOpenAccountMV(OpenAccountPageBean openBean) {
        {
            // 根据身份证号码获取性别
            String gender = "";
            int sexInt = Integer.parseInt(openBean.getIdNo().substring(16, 17));
            if (sexInt % 2 == 0) {
                gender = "F";
            } else {
                gender = "M";
            }
            // 获取共同参数
            String idType = BankCallConstant.ID_TYPE_IDCARD;
            // 调用开户接口
            BankCallBean openAccoutBean = new BankCallBean(openBean.getUserId(), BankCallConstant.TXCODE_ACCOUNT_OPEN_PAGE, Integer.parseInt(openBean.getPlatform()), BankCallConstant.BANK_URL_ACCOUNT_OPEN_PAGE);
            openAccoutBean.setIdentity(openBean.getIdentity());
            /**1：出借角色2：借款角色3：代偿角色*/
            openAccoutBean.setChannel(openBean.getChannel());
            openAccoutBean.setIdType(idType);
            openAccoutBean.setIdNo(openBean.getIdNo());
            openAccoutBean.setName(openBean.getTrueName());
            openAccoutBean.setGender(gender);
            openAccoutBean.setMobile(openBean.getMobile());
            // 代偿角色的账户类型为  00100-担保账户  其他的是 00000-普通账户
            openAccoutBean.setAcctUse(BankCallConstant.ACCOUNT_USE_GUARANTEE);
            openAccoutBean.setIdentity(openBean.getIdentity());
            // 同步地址  是否跳转到前端页面
            String retUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + "/user/openError" + "?logOrdId=" + openAccoutBean.getLogOrderId();
            String successUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + "/user/openSuccess";
            // 异步调用路
            String bgRetUrl = systemConfig.getWebHost() + "/user/secure/assurebankopen/bgReturn?phone=" + openBean.getMobile();
            openAccoutBean.setRetUrl(retUrl);
            openAccoutBean.setSuccessfulUrl(successUrl);
            openAccoutBean.setNotifyUrl(bgRetUrl);
            openAccoutBean.setCoinstName(openBean.getCoinstName() == null ? "汇盈金服" : openBean.getCoinstName());
            openAccoutBean.setLogRemark("页面开户");
            openAccoutBean.setLogIp(openBean.getIp());
            openBean.setOrderId(openAccoutBean.getLogOrderId());
            try {
                Map<String, Object> map = BankCallUtils.callApiMap(openAccoutBean);
                return map;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 获得借款人开户调用银行的参数
     *
     * @param openBean
     * @return
     */
    @Override
    public Map<String, Object> getLoanOpenAccountMV(OpenAccountPageBean openBean) {
        {
            // 根据身份证号码获取性别
            String gender = "";
            int sexInt = Integer.parseInt(openBean.getIdNo().substring(16, 17));
            if (sexInt % 2 == 0) {
                gender = "F";
            } else {
                gender = "M";
            }
            // 获取共同参数
            String idType = BankCallConstant.ID_TYPE_IDCARD;
            // 调用开户接口
            BankCallBean openAccoutBean = new BankCallBean(openBean.getUserId(), BankCallConstant.TXCODE_ACCOUNT_OPEN_PAGE, Integer.parseInt(openBean.getPlatform()), BankCallConstant.BANK_URL_ACCOUNT_OPEN_PAGE);
            openAccoutBean.setIdentity(openBean.getIdentity());
            /**1：出借角色2：借款角色3：代偿角色*/
            openAccoutBean.setChannel(openBean.getChannel());
            openAccoutBean.setIdType(idType);
            openAccoutBean.setIdNo(openBean.getIdNo());
            openAccoutBean.setName(openBean.getTrueName());
            openAccoutBean.setGender(gender);
            openAccoutBean.setMobile(openBean.getMobile());
            // 代偿角色的账户类型为  00100-担保账户  其他的是 00000-普通账户
            openAccoutBean.setAcctUse(BankCallConstant.ACCOUNT_USE_COMMON);
            openAccoutBean.setIdentity(openBean.getIdentity());
            // 同步地址  是否跳转到前端页面
            String retUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + "/user/openError" + "?logOrdId=" + openAccoutBean.getLogOrderId();
            String successUrl = super.getFrontHost(systemConfig,openBean.getPlatform()) + "/user/openSuccess";
            // 异步调用路
            String bgRetUrl = systemConfig.getWebHost() + "/user/secure/loanbankopen/bgReturn?phone=" + openBean.getMobile();
            openAccoutBean.setRetUrl(retUrl);
            openAccoutBean.setSuccessfulUrl(successUrl);
            openAccoutBean.setNotifyUrl(bgRetUrl);
            openAccoutBean.setCoinstName(openBean.getCoinstName() == null ? "汇盈金服" : openBean.getCoinstName());
            openAccoutBean.setLogRemark("页面开户");
            openAccoutBean.setLogIp(openBean.getIp());
            openBean.setOrderId(openAccoutBean.getLogOrderId());
            try {
                Map<String, Object> map = BankCallUtils.callApiMap(openAccoutBean);
                return map;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
