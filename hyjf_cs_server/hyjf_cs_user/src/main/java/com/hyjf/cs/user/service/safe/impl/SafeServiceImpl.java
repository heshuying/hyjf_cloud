/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.safe.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.BindEmailLogRequest;
import com.hyjf.am.resquest.user.UserNoticeSetRequest;
import com.hyjf.am.resquest.user.UsersContractRequest;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.constants.UserConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmBankOpenClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.BindEmailError;
import com.hyjf.cs.user.constants.ContractSetError;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.mq.MailProducer;
import com.hyjf.cs.user.mq.Producer;
import com.hyjf.cs.user.result.MobileModifyResultBean;
import com.hyjf.cs.user.service.safe.SafeService;
import com.hyjf.cs.user.vo.BindEmailVO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version SafeServiceImpl, v0.1 2018/6/11 15:55
 */
@Service
public class SafeServiceImpl implements SafeService {

    private static final Logger logger = LoggerFactory.getLogger(SafeServiceImpl.class);

    @Autowired
    private AmBankOpenClient amBankOpenClient;

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private MailProducer mailProducer;

    /**
     * 修改登陆密码
     * @param userId
     * @param oldPW
     * @param newPW
     * @return
     */
    @Override
    public JSONObject updatePassWd(Integer userId, String oldPW, String newPW){
        logger.info("UserService.updatePassWd run...userId is :{}, oldPW is :{}, newPW is :{}",userId,oldPW,newPW);
        return amUserClient.updatePassWd(userId, oldPW, newPW);
    }

    /**
     * 更新用户信息
     * @param userVO
     * @return
     */
    @Override
    public int updateUserByUserId(UserVO userVO) {
        return amUserClient.updateUserById(userVO);
    }

    /**
     * 获取用户对象
     * @param userId
     * @return
     */
    @Override
    public UserVO queryUserByUserId(Integer userId) {
        UserVO user = amUserClient.findUserById(userId);
        return user;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :账户设置信息查询
     * @Param: * @param token
     * @Date: 16:47 2018/5/30
     * @Return: java.lang.String
     */
    @Override
    public Map<String,Object> safeInit(WebViewUser webViewUser) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("url","user/safe/account-setting-index");
        resultMap.put("webViewUser", webViewUser);
        if (webViewUser.getTruename() != null && webViewUser.getTruename().length() >= 1) {
            resultMap.put("truename", webViewUser.getTruename().substring(0, 1) + "**");
        }
        if (webViewUser.getIdcard() != null && webViewUser.getIdcard().length() >= 15) {
            resultMap.put("idcard", webViewUser.getIdcard().substring(0, 3) + "***********" + webViewUser.getIdcard().substring(webViewUser.getIdcard().length() - 4));
        }
        if (webViewUser.getMobile() != null && webViewUser.getMobile().length() == 11) {
            resultMap.put("mobile", webViewUser.getMobile().substring(0, 3) + "****" + webViewUser.getMobile().substring(webViewUser.getMobile().length() - 4));
        }
        if (webViewUser.getEmail() != null && webViewUser.getEmail().length() >= 2) {
            String emails[] = webViewUser.getEmail().split("@");
            resultMap.put("email", AsteriskProcessUtil.getAsteriskedValue(emails[0], 2, emails[0].length() -2) + "@" + emails[1]);
        }
        UserVO user = amUserClient.findUserById(webViewUser.getUserId());
        UserLoginLogVO userLogin = amUserClient.getUserLoginById(webViewUser.getUserId());

        // 用户角色
        UserInfoVO userInfo = this.amUserClient.findUsersInfoById(webViewUser.getUserId());
        resultMap.put("roleId", userInfo.getRoleId());
        // 是否设置交易密码
        resultMap.put("isSetPassword", user.getIsSetPassword());
        resultMap.put("lastTime", userLogin.getLastTime());
        UsersContactVO usersContactVO = amUserClient.selectUserContact(user.getUserId());
        resultMap.put("usersContract",usersContactVO);
        
        // 紧急联系人类型 
        Map<String, String> result = CacheUtil.getParamNameMap("USER_RELATION");
		resultMap.put("userRelation", result);
		
        BankOpenAccountVO bankOpenAccount =amBankOpenClient.selectById(webViewUser.getUserId());
        AccountChinapnrVO chinapnr = amUserClient.getAccountChinapnr(webViewUser.getUserId());
        resultMap.put("bankOpenAccount", bankOpenAccount);
        resultMap.put("chinapnr", chinapnr);

        UserEvalationResultVO userEvalationResult = amBankOpenClient.selectUserEvalationResultByUserId(webViewUser.getUserId());
        if (userEvalationResult != null && userEvalationResult.getId() != 0) {
            //获取评测时间加一年的毫秒数18.2.2评测 19.2.2
            Long lCreate = GetDate.countDate(userEvalationResult.getCreateTime(),1,1).getTime();
            //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
            Long lNow = GetDate.countDate(new Date(), 5,1).getTime();
            if (lCreate <= lNow) {
                //已过期需要重新评测 2是已过期
                resultMap.put("ifEvaluation", 2);
            } else {
                // ifEvaluation是否已经调查表示 1是已调查，0是未调查
                resultMap.put("ifEvaluation", 1);
                // userEvalationResult 测评结果
                resultMap.put("userEvalationResult",userEvalationResult);
            }
        } else {
            resultMap.put("ifEvaluation", 0);
        }
        HjhUserAuthVO hjhUserAuth=amUserClient.getHjhUserAuthByUserId(webViewUser.getUserId());
        resultMap.put("hjhUserAuth", getUserAuthState(hjhUserAuth));

        // 获得是否授权
        // 获取用户上传头像
        String imghost = UploadFileUtils.getDoPath(systemConfig.getHeadUrl());
        imghost = imghost.substring(0, imghost.length() - 1);
        // 实际物理路径前缀2
        String fileUploadTempPath = UploadFileUtils.getDoPath(systemConfig.getUploadHeadPath());
        if(org.apache.commons.lang3.StringUtils.isNotEmpty( user.getIconurl())){
            resultMap.put("iconUrl", imghost + fileUploadTempPath + user.getIconurl());
        }
        resultMap.put("inviteLink", systemConfig.getWebHost()+"/web/user/regist/init?from="+webViewUser.getUserId());
        return resultMap;
    }

    /**
     * 获得用户授权状态信息
     * 自动投标状态          缴费授权状态      还款授权状态
     * @param auth
     * @return
     */
    public HjhUserAuthVO getUserAuthState(HjhUserAuthVO auth) {
        // 缴费授权
        int paymentAuth = valdateAuthState(auth.getAutoPaymentStatus(),auth.getAutoPaymentEndTime());
        auth.setAutoPaymentStatus(paymentAuth);
        // 还款授权
        int repayAuth = valdateAuthState(auth.getAutoRepayStatus(),auth.getAutoRepayEndTime());
        auth.setAutoRepayStatus(repayAuth);
        // 自动投资授权
        int invesAuth = valdateAuthState(auth.getAutoInvesStatus(),auth.getAutoBidEndTime());
        auth.setAutoInvesStatus(invesAuth);
        return auth;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :检查是否授权  0未授权  1已授权
     * @Param: * @param status
     * @param endTime
     * @Date: 16:47 2018/5/30
     * @Return: int
     */
    private int valdateAuthState(Integer status, String endTime) {
        String nowTime = GetDate.date2Str(new Date(),GetDate.yyyyMMdd);
        if(endTime==null || status==null){
            return 0;
        }
        if (status - 0 == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 绑定邮箱发送激活邮件
     * @param userId
     * @param email
     * @return
     * @throws MQException
     */
    @Override
    public boolean sendEmailActive(Integer userId, String email) throws MQException {
        UserVO user = amUserClient.findUserById(userId);
        String activeCode = GetCode.getRandomCode(6);

        // 保存发送的激活邮件记录
        BindEmailLogRequest request = new BindEmailLogRequest();
        request.setCreatetime(new Date());
        request.setEmailActiveCode(activeCode);
        request.setEmailActiveUrlDeadtime(GetDate.getSomeDayBeforeOrAfter(new Date(), 1));
        request.setUserEmail(email);
        request.setUserEmailStatus(UserConstant.EMAIL_ACTIVE_STATUS_1);
        request.setUserId(userId);
        amUserClient.insertBindEmailLog(request);

        // 发送激活邮件
        activeCode = MD5Utils.MD5(MD5Utils.MD5(activeCode));
        String url = systemConfig.webUIBindEmail + "?key=" + user.getUserId() + "&value=" + activeCode + "&email=" + email;
        Map<String, String> replaceMap = new HashMap<String, String>();
        replaceMap.put("url_name", url);
        if (StringUtils.isNotBlank(user.getNickname())) {
            replaceMap.put("username_name", user.getNickname());
        } else {
            replaceMap.put("username_name", user.getUsername());
        }

        MailMessage mailMessage = new MailMessage(null, replaceMap, "绑定邮箱激活", null, null, new String[] {email},
                CustomConstants.EMAILPARAM_TPL_BINDEMAIL, MessageConstant.MAILSENDFORMAILINGADDRESS);
        // 发送邮件
        mailProducer.messageSend(new Producer.MassageContent(MQConstant.MAIL_TOPIC, JSON.toJSONBytes(mailMessage)));

        return true;
    }

    /**
     * 发送激活邮件条件校验
     */
    @Override
    public void checkForEmailSend(String email, Integer userId) {
        // 邮箱为空校验
        if (StringUtils.isBlank(email)) {
            throw new ReturnMessageException(BindEmailError.EMAIL_EMPTY_ERROR);
        }
        // 邮箱格式校验
        if (!Validator.isEmailAddress(email)) {
            throw new ReturnMessageException(BindEmailError.EMAIL_FORMAT_ERROR);
        }
        // 邮箱地址使用校验
        boolean isExist = amUserClient.checkEmailUsed(email);
        if(isExist) {
            throw new ReturnMessageException(BindEmailError.EMAIL_USED_ERROR);
        }

    }

    /**
     * 绑定邮箱激活条件校验
     * @param email
     * @param userId
     * @param activeCode
     */
    @Override
    public void checkForEmailBind(BindEmailVO bindEmailVO, WebViewUser user) {
        // 邮箱为空校验
        if (StringUtils.isBlank(bindEmailVO.getEmail()) || StringUtils.isBlank(bindEmailVO.getValue()) || StringUtils.isBlank(bindEmailVO.getKey())) {
            throw new ReturnMessageException(BindEmailError.REQUEST_PARAM_ERROR);
        }

        // 校验激活是否用户本人
        if(!bindEmailVO.getKey().equals(String.valueOf(user.getUserId()))){
            throw new ReturnMessageException(BindEmailError.REQUEST_PARAM_ERROR);
        }

        // 激活邮件存在性校验
        BindEmailLogVO log = amUserClient.getBindEmailLog(Integer.parseInt(bindEmailVO.getKey()));
        if(log == null) {
            throw new ReturnMessageException(BindEmailError.EMAIL_ACTIVE_ERROR_4);
        }

        // 激活邮件过期校验
        if (new Date().after(log.getEmailActiveUrlDeadtime())) {
            throw new ReturnMessageException(BindEmailError.EMAIL_ACTIVE_ERROR_3);
        }

        // 激活校验
        if(!bindEmailVO.getKey().equals(String.valueOf(log.getUserId())) || !bindEmailVO.getEmail().equals(log.getUserEmail()) || !bindEmailVO.getValue().equals(log.getEmailActiveCode())) {
            throw new ReturnMessageException(BindEmailError.EMAIL_ACTIVE_ERROR);
        }
    }

    /**
     * 绑定邮箱更新
     * @param userId
     * @param email
     * @return
     * @throws MQException
     */
    @Override
    public boolean updateEmail(Integer userId, String email) throws MQException {
        BindEmailLogVO vo = amUserClient.getBindEmailLog(userId);
        BindEmailLogRequest requestBean = new BindEmailLogRequest();
        BeanUtils.copyProperties(vo, requestBean);
        amUserClient.updateBindEmail(requestBean);
        return true;
    }

    /**
     * 紧急联系人参数校验
     */
    @Override
    public void checkForContractSave(String relationId, String rlName, String rlPhone, WebViewUser user) {
        // 请求参数空值校验
        if (StringUtils.isBlank(relationId) || StringUtils.isBlank(rlName) || StringUtils.isBlank(rlPhone)) {
            throw new ReturnMessageException(ContractSetError.REQUEST_PARAM_ERROR);
        }
        // 姓名格式校验
        if (rlName.length() < 2 || rlName.length() > 4) {
            throw new ReturnMessageException(ContractSetError.NAME_FORMAT_ERROR);
        }
        // 手机号码格式校验
        if (rlPhone.length() != 11 || !Validator.isMobile(rlPhone)) {
            throw new ReturnMessageException(ContractSetError.PHONE_FORMAT_ERROR);
        }
    }

    /**
     * 保存紧急联系人
     */
    @Override
    public boolean saveContract(String relationId, String rlName, String rlPhone, WebViewUser user) throws MQException {
        UsersContractRequest requestBean = new UsersContractRequest();
        requestBean.setRelation(Integer.parseInt(relationId));
        requestBean.setRlName(rlName);
        requestBean.setRlPhone(rlPhone);
        requestBean.setUserId(user.getUserId());
        int cnt = amUserClient.updateUserContract(requestBean);

        if (cnt <= 0) {
            throw new ReturnMessageException(ContractSetError.CONTRACT_SAVE_ERROR);
        }

        return true;
    }

    /**
     * 更换手机号条件校验
     * @param newMobile
     * @param smsCode
     */
    @Override
    public boolean checkForMobileModify(String newMobile, String smsCode) {
        String verificationType = CommonConstant.PARAM_TPL_BDYSJH;
        int cnt = amUserClient.checkMobileCode(newMobile, smsCode, verificationType, CommonConstant.CLIENT_PC,
                CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED);
        if (cnt <= 0) {
            throw new ReturnMessageException(RegisterError.SMSCODE_INVALID_ERROR);
        }

        return true;
    }

    /**
     * 用户手机号修改信息查询
     * @param userId
     * @return
     */
    @Override
    public MobileModifyResultBean queryForMobileModify(Integer userId) {
        MobileModifyResultBean result = new MobileModifyResultBean();
        UserVO user = amUserClient.findUserById(userId);
        if(user != null && StringUtils.isNotBlank(user.getMobile())) {
            String hideMobile = user.getMobile().substring(0,user.getMobile().length()-(user.getMobile().substring(3)).length())+"****"+user.getMobile().substring(7);
            result.setMobile(user.getMobile());
            result.setHideMobile(hideMobile);
        }

        return result;
    }


    /**
     * 更新用户信息
     * @param requestBean
     * @return
     */
    @Override
    public int updateUserNoticeSet(UserNoticeSetRequest requestBean) {
        return amUserClient.updateUserNoticeSet(requestBean);
    }

}
