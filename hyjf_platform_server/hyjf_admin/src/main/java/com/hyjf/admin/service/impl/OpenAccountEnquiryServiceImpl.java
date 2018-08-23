package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean;
import com.hyjf.admin.beans.request.OpenAccountEnquiryDefineRequestBean;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.config.SystemConfig;
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
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.IdCard15To18;
import com.hyjf.common.validator.Validator;
import com.hyjf.common.validator.ValidatorCheckUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<BankOpenAccountLogVO> bankOpenAccountLogs=amUserClient.bankOpenAccountLogSelect(phone,idcard);
        // 返回参数
        BankCallBean retBean = null;
        if (Integer.parseInt(num)==1) {//手机号查询

            //手机号格式check
            JSONObject errjson = new JSONObject();
            ValidatorCheckUtil.validateMobile(errjson, "手机号", "statusDesc", phone, 11, false);
            if (ValidatorCheckUtil.hasValidateError(errjson)) {
                resultBean.setResult("输入验证失败！");
                resultBean.setMobile(phone);
                return resultBean;
            }
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

            //手机号格式check
            JSONObject errjson = new JSONObject();
            ValidatorCheckUtil.validateMobile(errjson, "手机号", "statusDesc", phone, 11, false);
            if (ValidatorCheckUtil.hasValidateError(errjson)) {
                resultBean.setResult("输入验证失败！");
                resultBean.setMobile(phone);
                return resultBean;
            }
            ValidatorCheckUtil.validateIdCard(errjson, "idcard", idcard, "statusDesc", phone,18, 18, true);
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
     * 用户按照手机号和身份证号查询开户掉单后同步系统掉单信息，更改用户状态
     *
     * @param requestBean
     * @return com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean
     * @author Zha Daojian
     * @date 2018/8/20 16:35
     **/
    @Override
    public OpenAccountEnquiryDefineResultBean openAccountEnquiryUpdate(AdminSystemVO currUser, OpenAccountEnquiryDefineRequestBean requestBean) {
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
}
