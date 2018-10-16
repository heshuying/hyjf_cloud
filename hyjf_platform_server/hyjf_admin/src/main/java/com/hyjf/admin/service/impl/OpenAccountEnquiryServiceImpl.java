package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean;
import com.hyjf.admin.beans.OpenAccountEnquiryErrorResultBean;
import com.hyjf.admin.beans.request.OpenAccountEnquiryDefineRequestBean;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.mq.SmsProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.OpenAccountEnquiryService;
import com.hyjf.admin.utils.BankUtil;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.resquest.user.BankOpenAccountLogRequest;
import com.hyjf.am.resquest.user.BankOpenAccountRequest;
import com.hyjf.am.resquest.user.UserManagerUpdateRequest;
import com.hyjf.am.vo.admin.BankOpenAccountLogVO;
import com.hyjf.am.vo.admin.OpenAccountEnquiryCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @version OpenAccountEnquiryServiceImpl, v0.1 2018/8/20 16:36
 * @Author: Zha Daojian
 */
@Service
public class OpenAccountEnquiryServiceImpl extends BaseServiceImpl implements OpenAccountEnquiryService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    AmUserClient amUserClient;

    @Autowired
    private SmsProducer smsProducer;

    /**
     * 用户按照手机号和身份证号查询开户掉单
     *
     * @param requestBean
     * @return com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean
     * @author Zha Daojian
     * @date 2018/8/20 16:26
     **/
    @Override
    public OpenAccountEnquiryDefineResultBean openAccountEnquiry(AdminSystemVO currUser, OpenAccountEnquiryDefineRequestBean requestBean) {
        OpenAccountEnquiryDefineResultBean resultBean= new OpenAccountEnquiryDefineResultBean();

        String userId = currUser.getId();
        //选择1为手机号查询，2为身份证号查询
        String num = requestBean.getNum();
        String phone =null;
        String idcard =null;
        if (Integer.parseInt(num)==1){
            //手机号
            phone = requestBean.getLastname().trim();
        }
        if (Integer.parseInt(num)==2){
            //身份证
            idcard = requestBean.getLastname().trim();
        }
        //获取掉单用户信息
        BankOpenAccountLogRequest bankOpenAccountLog=new BankOpenAccountLogRequest();
        bankOpenAccountLog.setMobile(phone);
        bankOpenAccountLog.setIdcard(idcard);
        // 调用查询电子账户
        BankCallBean selectbean = new BankCallBean();
        selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        if(Integer.parseInt(num)==1){
            selectbean.setTxCode(BankCallConstant.TXCODE_ACCOUNT_QUERY_BY_MOBILE);
        }
        if(Integer.parseInt(num)==2){
            selectbean.setTxCode(BankCallConstant.TXCODE_ACCOUNTID_QUERY);
        }
        selectbean.setInstCode(systemConfig.getBANK_INSTCODE());// 机构代码
        selectbean.setBankCode(systemConfig.getBANK_BANKCODE());
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        selectbean.setChannel("000002");
        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(Integer.parseInt(userId)));
        selectbean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        selectbean.setLogClient(0);
        //通过手机号和身份证查询掉单信息
        List<BankOpenAccountLogVO> bankOpenAccountLogs=amUserClient.bankOpenAccountLogSelect(bankOpenAccountLog);
        // 返回参数
        BankCallBean retBean = null;
        if (Integer.parseInt(num)==1) {//手机号查询

            //手机号格式check
            CheckUtil.check(Validator.isNotNull(phone) && Validator.isMobile(phone), MsgEnum.ERR_FMT_MOBILE);
            OpenAccountEnquiryCustomizeVO accountMap=this.amUserClient.searchAccountEnquiry(bankOpenAccountLog);
            if (accountMap==null) {
                resultBean.setResult("该用户不存在！");
                resultBean.setMobile(phone);
                return resultBean;
            }
            selectbean.setMobile(phone);
            String bankopenaccountString=accountMap.getAccountStatus();
            // 调用接口
            retBean = BankCallUtils.callApiBg(selectbean);
            if (retBean!=null) {
                //银行是否开户
                if (bankopenaccountString.equals("0")) {//未开户0
                    //判断电子账号是否为空
                    if (StringUtils.isNotBlank(retBean.getAccountId())) {
                        //银行已开户，系统掉单
                        // 准备数据查询江西银行开户数据，同步系统开户数据
                        String ordeidString=null;
                        if(bankOpenAccountLogs.size()>0){
                            ordeidString=bankOpenAccountLogs.get(0).getOrderId();
                            resultBean.setRegTimeEnd(GetDate.date2Str(bankOpenAccountLogs.get(0).getCreateTime(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                        }
                        resultBean.setAccountStatus("已开户");
                        resultBean.setMobile(phone);
                        resultBean.setUsername(accountMap.getUsername());
                        resultBean.setOrdeidString(ordeidString);
                        resultBean.setUserid(accountMap.getUserid());
                        resultBean.setChannel(retBean.getChannel());
                        resultBean.setAccountId(retBean.getAccountId());
                        if (retBean.getAcctState().equals("")||retBean.getAcctState().equals("A")) {
                            resultBean.setAccountId(retBean.getAccountId());
                            resultBean.setPlatform(accountEsbStates(accountMap.getPlatform()));//开户平台
                        }
                        return resultBean;
                    }else {
                        resultBean.setAccountStatus("未开户");
                        resultBean.setMobile(phone);
                        resultBean.setUsername(accountMap.getUsername());
                        return resultBean;

                    }
                }
                if (bankopenaccountString.equals("1")) {//已开户1
                    resultBean.setAccountStatus(accountState(retBean.getAcctState()));//开户状态
                    resultBean.setMobile(phone);
                    resultBean.setUsername(accountMap.getUsername());
                    if (retBean.getAcctState().equals("")||retBean.getAcctState().equals("A")) {
                        resultBean.setAccountId(retBean.getAccountId());
                        resultBean.setPlatform(accountEsbStates(accountMap.getPlatform()));//开户平台
                    }
                    if(bankOpenAccountLogs.size()>0){
                        resultBean.setRegTimeEnd(GetDate.date2Str(bankOpenAccountLogs.get(0).getCreateTime(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                    }
                    return resultBean;
                }
            }else {//银行未返回值
                resultBean.setResult("该用户无银行开户信息！");
                resultBean.setMobile(phone);
                return resultBean;
            }

        }
        if (Integer.parseInt(num)==2) {//身份证号查询
            // 画面验证
            // 身份证号码格式以及长度的校验
            CheckUtil.check(Validator.isNotNull(idcard) && Validator.isIdcard(idcard), MsgEnum.ERR_FMT_IDCARDNO);
            //ValidatorCheckUtil.validateIdCard(errjson, "idcard", idcard, "statusDesc", phone,18, 18, true);
            boolean booleans=ValidatorFieldCheckUtil.isIDCard(idcard);
            if (!booleans) {
                resultBean.setResult("输入验证失败！");
                resultBean.setIdcard(idcard);
                return resultBean;
            }
            OpenAccountEnquiryCustomizeVO accountMap=amUserClient.searchAccountEnquiry(bankOpenAccountLog);
            if (accountMap==null) {
                resultBean.setResult("该用户不存在");
                resultBean.setIdcard(idcard);
                return resultBean;
            }
            String bankopenaccountString=accountMap.getAccountStatus().toString();
            selectbean.setIdType("01");
            selectbean.setIdNo(idcard);
            // 调用接口
            retBean = BankCallUtils.callApiBg(selectbean);
            if (retBean!=null) {
                //银行是否开户
                if (bankopenaccountString.equals("0")) {//未开户
                    if (StringUtils.isNotBlank(retBean.getAccountId())) {
                        // 准备数据查询江西银行开户数据，同步系统开户数据
                        String ordeidString=null;
                        if(bankOpenAccountLogs.size()>0){
                            ordeidString=bankOpenAccountLogs.get(0).getOrderId();
                        }
                        resultBean.setAccountStatus("已开户");
                        resultBean.setIdcard(idcard);
                        resultBean.setUsername(accountMap.getUsername());
                        resultBean.setOrdeidString(ordeidString);
                        resultBean.setUserid(accountMap.getUserid());
                        resultBean.setChannel(retBean.getChannel());
                        resultBean.setAccountId(retBean.getAccountId());
                        if (retBean.getAcctState().equals("")||retBean.getAcctState().equals("A")) {
                            resultBean.setAccountId(retBean.getAccountId());
                            resultBean.setPlatform(accountEsbStates(accountMap.getPlatform()));//开户平台
                        }
                        if(bankOpenAccountLogs.size()>0){
                            resultBean.setRegTimeEnd(GetDate.date2Str(bankOpenAccountLogs.get(0).getCreateTime(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                        }
                        return resultBean;
                    }else {
                        resultBean.setAccountStatus("未开户");
                        resultBean.setIdcard(idcard);
                        resultBean.setUsername(accountMap.getUsername());
                        return resultBean;
                    }
                }
                if (bankopenaccountString.equals("1")) {//已开户
                    resultBean.setAccountStatus(accountState(retBean.getAcctState()));//开户状态
                    resultBean.setMobile(accountMap.getMobile());
                    resultBean.setIdcard(idcard);
                    resultBean.setUsername(accountMap.getUsername());
                    if (retBean.getAcctState().equals("")||retBean.getAcctState().equals("A")) {
                        resultBean.setAccountId(retBean.getAccountId());
                        resultBean.setPlatform(accountEsbStates(accountMap.getPlatform()));//开户平台
                    }
                    if(bankOpenAccountLogs.size()>0){
                        resultBean.setRegTimeEnd(GetDate.date2Str(bankOpenAccountLogs.get(0).getCreateTime(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                    }
                    return resultBean;
                }
            }else {
                resultBean.setResult("该用户无银行开户信息！");
                resultBean.setIdcard(idcard);
                resultBean.setUsername(accountMap.getUsername());
                return resultBean;
            }

        }
        return resultBean;
    }

    /**
     * ajax用户按照手机号和身份证号查询开户掉单校验
     *
     * @param currUser
     * @param requestBean
     * @return com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean
     * @author Zha Daojian
     * @date 2018/8/20 16:26
     **/
    @Override
    public OpenAccountEnquiryErrorResultBean openAccountEnquiryError(AdminSystemVO currUser, OpenAccountEnquiryDefineRequestBean requestBean) {
        OpenAccountEnquiryErrorResultBean resultBean = new OpenAccountEnquiryErrorResultBean();
        String userId = currUser.getId();
        //选择1为手机号查询，2为身份证号查询
        String type = requestBean.getNum();
        String nums =requestBean.getLastname().trim();
        // 按照手机号查询 2按照身份证查询
        // 调用查询电子账户
        BankCallBean selectbean = new BankCallBean();
        selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        selectbean.setInstCode(systemConfig.getBANK_INSTCODE());// 机构代码
        selectbean.setBankCode(systemConfig.getBANK_BANKCODE());
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        selectbean.setChannel("000002");
        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(Integer.parseInt(userId)));
        selectbean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        selectbean.setLogClient(0);
        // 返回参数
        BankCallBean retBean = null;
        // 设置本地的开户状态
        String isOpen = setDbOpenParam(type, nums);
        // 已经开户的就不查询了
        if ("0".equals(isOpen)) {
            if ("1".equals(type)) {
                // 根据手机号查询
                CheckUtil.check(Validator.isNotNull(nums) && Validator.isMobile(nums), MsgEnum.ERR_FMT_MOBILE);
                // 手机号码验证成功
                selectbean.setTxCode(BankCallConstant.TXCODE_ACCOUNT_QUERY_BY_MOBILE);
                //获取掉单用户信息
                BankOpenAccountLogRequest bankOpenAccountLog=new BankOpenAccountLogRequest();
                bankOpenAccountLog.setMobile(nums);
                selectbean.setTxCode(BankCallConstant.TXCODE_ACCOUNTID_QUERY);
                OpenAccountEnquiryCustomizeVO accountMap=this.amUserClient.searchAccountEnquiry(bankOpenAccountLog);
                if (accountMap == null) {
                    resultBean.setStatus("n");
                    resultBean.setInfo("该用户不存在,输入手机号码不存在！");
                    return resultBean;
                }
                selectbean.setMobile(nums);
                // 首次查询上送空；翻页查询上送1； 翻页标志
                selectbean.setRtnInd("");
                // nxProduct翻页产品号 翻页控制使用；首次查询上送空；翻页查询时上送上页返回的最后一条记录的产品。
                // 调用接口
                retBean = BankCallUtils.callApiBg(selectbean);
                if (retBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode())) {
                    JSONArray jsa = JSONArray.parseArray(retBean.getSubPacks());
                    if (jsa != null && jsa.size() > 0) {
                        // 如果返回的结果大于0条
                        resultBean.setStatus("y");
                        resultBean.setInfo("验证通过！");
                        return resultBean;
                    } else {
                        resultBean.setStatus("n");
                        resultBean.setInfo("该用户无银行开户信息！");
                        return resultBean;
                    }
                } else {
                    resultBean.setStatus("n");
                    resultBean.setInfo("该用户无银行开户信息！");
                    return resultBean;
                }
            } else if ("2".equals(type)) {
                // 根据身份证查询
                // 根据身份证查询时候必须验证用户名不能为空
                if (nums==null||"".equals(nums)) {
                    resultBean.setStatus("n");
                    resultBean.setInfo("该用户无银行开户信息！");
                    return resultBean;
                }
                //获取掉单用户信息
                BankOpenAccountLogRequest bankOpenAccountLog=new BankOpenAccountLogRequest();
                bankOpenAccountLog.setIdcard(nums);
                OpenAccountEnquiryCustomizeVO accountMap=this.amUserClient.searchAccountEnquiry(bankOpenAccountLog);
                if (accountMap==null) {
                    resultBean.setStatus("n");
                    resultBean.setInfo("该用户不存在！");
                    return resultBean;
                }
                // 根据身份证查询
                selectbean.setTxCode(BankCallConstant.TXCODE_ACCOUNTID_QUERY);
                selectbean.setIdType("01");
                selectbean.setIdNo(nums);
                // 调用接口
                retBean = BankCallUtils.callApiBg(selectbean);
                if (retBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode())) {
                    resultBean.setStatus("y");
                    resultBean.setInfo("验证成功！");
                    return resultBean;
                } else {
                    resultBean.setStatus("n");
                    resultBean.setInfo("该用户不存在！");
                    return resultBean;
                }
            }
        }
        return resultBean;
    }

    /**
     * 用户按照手机号和身份证号查询开户掉单后同步系统掉单信息，更改用户状态
     *
     * @param requestBean
     * @return com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean
     * @author Zha Daojian
     * @date 2018/8/20 16:35
     **/
    @Override
    public OpenAccountEnquiryDefineResultBean openAccountEnquiryUpdate(OpenAccountEnquiryDefineRequestBean requestBean) {
        OpenAccountEnquiryDefineResultBean resultBean= new OpenAccountEnquiryDefineResultBean();
        String ordeidString =requestBean.getOrdeidString().trim();
        String userid = requestBean.getUserid().trim();
        String channel =requestBean.getChannel().trim();
        String accountId = requestBean.getAccountId().trim();
        if(ordeidString!=null||userid!=null||channel!=null||accountId!=null){
            updateUserAccount(ordeidString, Integer.parseInt(userid),Integer.parseInt(channel), accountId);
        }
        resultBean.setStatus("success");
        resultBean.setResult("开户掉单同步成功");
        return resultBean;
    }
    /**
     * 保存开户的数据
     */
    public boolean updateUserAccount(String orderId, Integer userId, Integer client, String account) {

        BankOpenAccountLogVO openAccoutLog = amUserClient.selectBankOpenAccountLogByOrderId(orderId);
        if (Validator.isNull(openAccoutLog)) {
            throw new RuntimeException("查询用户开户日志表失败，用户开户订单号：" + orderId + ",用户userId:" + userId);
        }
        boolean deleteLogFlag = this.amUserClient.deleteBankOpenAccountLogByUserId(userId);
        if (!deleteLogFlag) {
            logger.error("删除用户开户日志表失败，用户开户订单号：" + orderId + ",用户userId:" + userId);
            return false;
        }
        // 查询返回的电子账号是否已开户
        boolean result = amUserClient.checkAccountByAccountId(account);
        if (result) {// 校验未通过
            logger.error("==========该电子账号已被用户关联,无法完成掉单修复!============关联电子账号: " + account);
            return false;
        }
        UserVO user = amUserClient.getUserByUserId(userId);// 获取用户信息
        String userName = user.getUsername();
        String idCard = openAccoutLog.getIdNo(); // 身份证号
        String trueName = null;// 真实姓名
        try {
            trueName = URLDecoder.decode(openAccoutLog.getName(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("===========url编码异常!=============");
        }
        if (idCard != null && idCard.length() < 18) {
            try {
                idCard = IdCard15To18.getEighteenIDCard(idCard);
            } catch (Exception e) {
                logger.error("===========url编码异常!=============");
            }
        }
        int sexInt = Integer.parseInt(idCard.substring(16, 17));// 性别
        if (sexInt % 2 == 0) {
            sexInt = 2;
        } else {
            sexInt = 1;
        }
        String birthDayTemp = idCard.substring(6, 14);// 出生日期
        String birthDay = StringUtils.substring(birthDayTemp, 0, 4) + "-" + StringUtils.substring(birthDayTemp, 4, 6) + "-" + StringUtils.substring(birthDayTemp, 6, 8);
        user.setBankOpenAccount(1);
        user.setBankAccountEsb(client);
        user.setRechargeSms(0);
        user.setWithdrawSms(0);
        user.setUserType(0);
        user.setMobile(openAccoutLog.getMobile());
        //user.setVersion(user.getVersion().add(new BigDecimal("1")));
        UserManagerUpdateRequest request = new UserManagerUpdateRequest();

        // 更新相应的用户表
        boolean usersFlag = amUserClient.updataUserInfo(request)> 0 ? true : false;
        if (!usersFlag) {
            logger.error("更新用户表失败！");
        }
        UserInfoVO userInfo = amUserClient.selectUsersInfoByUserId(userId);
        if (userInfo == null) {
            logger.error("用户详情表数据错误，用户id：" + user.getUserId());
        }
        userInfo.setTruename(trueName);
        userInfo.setIdcard(idCard);
        userInfo.setSex(sexInt);
        userInfo.setBirthday(birthDay);
        userInfo.setTruenameIsapprove(1);
        userInfo.setMobileIsapprove(1);
        // 更新用户详细信息表
        boolean userInfoFlag = amUserClient.updateUserInfoByUserInfo(userInfo) > 0 ? true : false;
        if (!userInfoFlag) {
            logger.error("更新用户详情表失败！");
        }
        // 插入汇付关联表
        BankOpenAccountRequest openAccount = new BankOpenAccountRequest();
        openAccount.setUserId(userId);
        openAccount.setUserName(user.getUsername());
        openAccount.setAccount(account);
        openAccount.setCreateTime(openAccoutLog.getCreateTime());
        openAccount.setCreateUserId(userId);
        boolean openAccountFlag = amUserClient.insertBankOpenAccount(openAccount)> 0 ? true : false;
        if (!openAccountFlag) {
            logger.error("插入用户开户表失败！");
        }
        String bank = BankUtil.getNameOfBank(openAccoutLog.getCardNo());
        // 插入相应的银行卡
        BankCardRequest bankCard = new BankCardRequest();
        bankCard.setUserId(userId);
        bankCard.setUserName(userName);
        bankCard.setCardNo(openAccoutLog.getCardNo());
        bankCard.setBank(bank);
        bankCard.setStatus(1);
        bankCard.setCreateTime(openAccoutLog.getCreateTime());
        bankCard.setCreateUserId(userId);
        boolean bankFlag = amUserClient.insertUserCard(bankCard) > 0 ? true : false;
        if (!bankFlag) {
            logger.error("插入用户银行卡失败！");
        }
        // 开户更新开户渠道统计开户时间
        AppChannelStatisticsDetailVO appChannelStatisticsDetailVO = amUserClient.getAppChannelStatisticsDetailByUserId(userId);
        if (appChannelStatisticsDetailVO != null) {
            appChannelStatisticsDetailVO.setOpenAccountTime(openAccoutLog.getCreateTime());
            amUserClient.updateByPrimaryKeySelective(appChannelStatisticsDetailVO);
        }

        // add by liuyang 20180227 开户掉单处理成功之后 发送法大大CA认证MQ  start
        // 加入到消息队列
        Map<String, String> params = new HashMap<String, String>();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        params.put("userId", String.valueOf(userId));
        try {
            SmsMessage smsMessage = new SmsMessage(userId, params, null, null, MessageConstant.SMS_SEND_FOR_USER, null, CustomConstants.PARAM_TPL_CHONGZHI_SUCCESS,
                    CustomConstants.CHANNEL_TYPE_NORMAL);
            smsProducer.messageSend(new MessageContent(MQConstant.FDD_CERTIFICATE_AUTHORITY_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));

        } catch (Exception e) {
            logger.error("开户掉单处理成功之后 发送法大大CA认证MQ消息失败！userId:[{}]",userId);
        }
        //rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_COUPON, RabbitMQConstants.ROUTINGKEY_CERTIFICATE_AUTHORITY, JSONObject.toJSONString(params));
        // add by liuyang 20180227 开户掉单处理成功之后 发送法大大CA认证MQ  end
        return true;
    }

    //reg_esb   hyjf_user表
    private String accountEsbStates(int string) {
        if (string==0) {
            return "PC";
        }
        if (string==1) {
            return "微信";
        }
        if (string==2) {
            return "Android";
        }
        if (string==3) {
            return "iOS";
        }
        if (string==4) {
            return "其他";
        }
        return null;

    }
    private String accountState(String string) {
        if (string.isEmpty()) {
            return "正常";
        }
        if (string.equals("A")) {
            return "待激活";
        }
        if (string.equals("C")) {
            return "止付";
        }
        if (string.equals("Z")) {
            return "注销";
        }
        return null;

    }

    // 查询本地开户状态
    private String setDbOpenParam(String requestType, String phone) {
        BankOpenAccountLogRequest bankOpenAccountLog=new BankOpenAccountLogRequest();
        bankOpenAccountLog.setMobile(phone);

        if ("1".equals(requestType)) {
            // 手机号
            bankOpenAccountLog.setMobile(phone);
        } else if ("2".equals(requestType)) {
            // 身份证
            bankOpenAccountLog.setIdcard(phone);
        }
        OpenAccountEnquiryCustomizeVO accountMap=this.amUserClient.searchAccountEnquiry(bankOpenAccountLog);
        if (accountMap != null) {
            // 已经开户了
            return accountMap.getAccountStatus();
        } else {
            // 没开户
            return "0";
        }
    }
}
