/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.password.impl;

import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.MD5Utils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.PassWordError;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.cs.user.util.RSAJSPUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangc
 * @version PassWordServiceImpl, v0.1 2018/6/14 14:10
 */
@Service
public class PassWordServiceImpl  extends BaseUserServiceImpl implements PassWordService {


    private static Logger logger = LoggerFactory.getLogger(PassWordServiceImpl.class);



    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    SystemConfig systemConfig;

    /**
     * 修改用户登录密码
     * @param userId
     * @param oldPW
     * @param newPW
     * @return
     */
    @Override
    public WebResult<String> updatePassWd(Integer userId, String oldPW, String newPW, String newPW2) {
        logger.info("UserController.updatePassWd run...userId is :{}, oldPW is :{}, newPW is :{}",userId,oldPW,newPW);

        WebResult<String> result = new WebResult<String>();

        if (StringUtils.isBlank(oldPW)) {
            logger.error("修改用户登录密码-原始登录密码不能为空");
            throw new ReturnMessageException(PassWordError.LOGINPW_NOTNULL_ERROR);
        }
        if (StringUtils.isBlank(newPW) || StringUtils.isBlank(newPW2)) {
            logger.error("修改用户登录密码-新密码不能为空");
            throw new ReturnMessageException(PassWordError.NEWPASSWORD_NOTNULL_ERROR);
        }

        oldPW = RSAJSPUtil.rsaToPassword(oldPW);
        newPW = RSAJSPUtil.rsaToPassword(newPW);
        newPW2 = RSAJSPUtil.rsaToPassword(newPW2);

        if (!newPW.equals(newPW2)) {
            logger.error("修改用户登录密码-两次密码不一致");
            throw new ReturnMessageException(PassWordError.PASSWORD_SAME_ERROR);
        }

        UserVO user = amUserClient.findUserById(userId);

        // 验证用的password
        oldPW = MD5Utils.MD5(MD5Utils.MD5(oldPW) + user.getSalt());

        if(!oldPW.equals(user.getPassword())){
            logger.error("修改用户登录密码-旧密码不正确");
            throw new ReturnMessageException(PassWordError.OLDPASSWD_INVALID_ERROR);
        }

        if (newPW.length() < 6 || newPW.length() > 16) {
            logger.error("修改用户登录密码-密码长度6-16位");
            throw new ReturnMessageException(PassWordError.PASSWORD_LENGTH_ERROR);

        }

        if (oldPW.equals(newPW)) {
            logger.error("修改用户登录密码-新密码不能与原密码相同!");
            throw new ReturnMessageException(PassWordError.PASSWORD_SAME1_ERROR);
        }

        boolean hasNumber = false;
        for (int i = 0; i < newPW.length(); i++) {
            if (Validator.isNumber(newPW.substring(i, i + 1))) {
                hasNumber = true;
                break;
            }
        }
        if (!hasNumber) {
            logger.error("修改用户登录密码-密码必须包含数字!");
            throw new ReturnMessageException(PassWordError.PASSWORD_NO_NUMBER_ERROR);
        }

        String regEx = "^[a-zA-Z0-9]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(newPW);
        if (!m.matches()) {
            logger.error("修改用户登录密码-密码必须由数字和字母组成，如abc123");
            throw new ReturnMessageException(PassWordError.PASSWORD_FORMAT_ERROR);
        }

        UserVO iuser = new UserVO();
        iuser.setUserId(userId);
        iuser.setPassword(MD5Utils.MD5(MD5Utils.MD5(newPW) + user.getSalt()));
        boolean success =  amUserClient.updateUserById(iuser) > 0;
        if (success) {
            result.setStatus("0");
            result.setStatusDesc("修改密码成功");
        } else {
            logger.error("修改用户登录密码-修改密码失败,未作任何操作");
            throw new ReturnMessageException(PassWordError.PASSWORD_CHANGE_ERROR);
        }
        return result;
    }

    @Override
    public void updateUserIsSetPassword(Integer userId) {
        UserVO iuser = new UserVO();
        iuser.setUserId(userId);
        iuser.setIsSetPassword(1);
        amUserClient.updateUserById(iuser);
    }

    @Override
    public Map<String, Object> setPassword(UserVO user) {
        // 判断用户是否开户
        int accountFlag = user.getBankOpenAccount();
        // 未开户
        if (accountFlag!=1) {
            throw new ReturnMessageException(PassWordError.USER_OPENBANK_ERROR);
        }
        // 判断用户是否设置过交易密码
        Integer passwordFlag = user.getIsSetPassword();
        CheckUtil.check(passwordFlag != 1, MsgEnum.STATUS_TP000001);
        int userId = user.getUserId();
        // 取得用户详细信息
        UserInfoVO userInfoVO = amUserClient.findUsersInfoById(userId);
        BankOpenAccountVO bankAccount = amUserClient.selectById(userId);
        // 调用设置密码接口
        String txcode="";
        BankCallBean bean = new BankCallBean(userId,txcode, ClientConstants.WEB_CLIENT);
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_SET);
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        if(user.getUserType() == 1){
            //企业用户 传组织机构代码
            CorpOpenAccountRecordVO record= getCorpOpenAccountRecord(userId);
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        }else{
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(userInfoVO.getIdcard());
            bean.setName(userInfoVO.getTruename());
        }
        // 电子账号
        bean.setAccountId(bankAccount.getAccount());
        bean.setMobile(user.getMobile());
        String retUrl = systemConfig.getFrontHost() + "/password/openError"+"?logOrdId="+bean.getLogOrderId();
        String successUrl = systemConfig.getFrontHost() +"/password/openSuccess";
        // 异步调用路
        String bgRetUrl = systemConfig.getWebHost() + "/web/user/password/passwordBgreturn";
        bean.setRetUrl(retUrl);
        bean.setSuccessfulUrl(successUrl);
        bean.setNotifyUrl(bgRetUrl);
        // 商户私有域，存放开户平台,用户userId
        LogAcqResBean acqRes = new LogAcqResBean();
        acqRes.setUserId(userId);
        bean.setLogAcqResBean(acqRes);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDSET);
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));
        bean.setLogRemark("设置交易密码");
        Map<String,Object> map = new HashMap<>();
        try {
            map = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            throw new ReturnMessageException(PassWordError.BANK_CONNECT_ERROR);
        }
        return map;
    }

    @Override
    public Map<String, Object> resetPassword(UserVO user) {
        int userId = user.getUserId();
        UserInfoVO userInfoVO = amUserClient.findUsersInfoById(userId);
        BankOpenAccountVO bankAccount = amUserClient.selectById(userId);
        // 调用设置密码接口
        BankCallBean bean = new BankCallBean();
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        // 消息类型(密码重置)
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET);
        if(user.getUserType() == 1){
            //企业用户 传组织机构代码
            CorpOpenAccountRecordVO record= getCorpOpenAccountRecord(userId);
            // 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        }else{
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(userInfoVO.getIdcard());
            bean.setName(userInfoVO.getTruename());
        }
        // 电子账号
        bean.setAccountId(bankAccount.getAccount());
        bean.setMobile(user.getMobile());
        String retUrl = systemConfig.getFrontHost() + "/password/openError"+"?logOrdId="+bean.getLogOrderId();
        String successUrl = systemConfig.getFrontHost() +"/password/openSuccess";
        // 异步调用路
        String bgRetUrl = systemConfig.webHost + "/web/user/password/resetPasswordBgreturn";
        bean.setRetUrl(retUrl);
        bean.setSuccessfulUrl(successUrl);
        bean.setNotifyUrl(bgRetUrl);
        // 商户私有域，存放开户平台,用户userId
        LogAcqResBean acqRes = new LogAcqResBean();
        acqRes.setUserId(userId);
        bean.setLogAcqResBean(acqRes);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE);
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));
        Map<String,Object> map = new HashMap<>();
        try {
            map = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            throw new ReturnMessageException(PassWordError.BANK_CONNECT_ERROR);
        }
        return map;
    }
}
