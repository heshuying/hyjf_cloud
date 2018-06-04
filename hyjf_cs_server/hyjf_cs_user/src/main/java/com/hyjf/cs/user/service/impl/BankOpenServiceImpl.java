package com.hyjf.cs.user.service.impl;

import com.hyjf.am.resquest.user.BankOpenRequest;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.PropUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.common.validator.ValidatorCheckUtil;
import com.hyjf.cs.user.beans.OpenAccountPageBean;
import com.hyjf.cs.user.client.AmBankOpenClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.OpenAccountError;
import com.hyjf.cs.user.redis.RedisUtil;
import com.hyjf.cs.user.result.AppResult;
import com.hyjf.cs.user.service.BankOpenService;
import com.hyjf.cs.user.util.ClientConstant;
import com.hyjf.cs.user.vo.BankOpenVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiasq
 * @version UserServiceImpl, v0.1 2018/4/11 9:34
 */

@Service
public class BankOpenServiceImpl implements BankOpenService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmBankOpenClient amBankOpenClient;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * @param userId
     * @return
     */
    @Override
    public UserVO getUsers(Integer userId) {

        UserVO userVO = amUserClient.findUserById(userId);

        return userVO;
    }

    @Override
    public boolean existUser(String mobile) {
        UserVO userVO = amUserClient.findUserByMobile(mobile);
        return userVO == null ? false : true;
    }

    @Override
    public boolean checkIdNo(String idNo) {

        UserInfoVO userInfo = amBankOpenClient.findUserInfoByCardNo(idNo);

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

        return amBankOpenClient.updateUserAccountLog(bankOpenRequest);
    }

    @Override
    public AppResult<String> checkRequestParam(UserVO user, BankOpenVO bankOpenVO) {
        AppResult<String> result = new AppResult<String>();
        result.setStatus(OpenAccountError.SUCCESS.getErrCode());
        // 验证请求参数
        if (user == null) {
            result.setStatus(OpenAccountError.USER_NOT_LOGIN_ERROR.getErrCode());
            result.setStatusDesc("用户未登录！");
            return result;
        }
        // 手机号
        if (StringUtils.isEmpty(bankOpenVO.getMobile())) {
            result.setStatus(OpenAccountError.PARAM_ERROR.getErrCode());
            result.setStatusDesc("手机号不能为空！");
            return result;
        }
        // 姓名
        if (StringUtils.isEmpty(bankOpenVO.getTrueName())) {
            result.setStatus(OpenAccountError.PARAM_ERROR.getErrCode());
            result.setStatusDesc("真实姓名不能为空！");
            return result;
        } else {
            //判断真实姓名是否包含空格
            if (!ValidatorCheckUtil.verfiyChinaFormat(bankOpenVO.getTrueName())) {
                result.setStatus(OpenAccountError.PARAM_ERROR.getErrCode());
                result.setStatusDesc("真实姓名不能包含空格！");
                return result;
            }
            //判断真实姓名的长度,不能超过10位
            if (bankOpenVO.getTrueName().length() > 10) {
                result.setStatus(OpenAccountError.PARAM_ERROR.getErrCode());
                result.setStatusDesc("真实姓名不能超过10位！");
                return result;
            }
        }
        // 身份证号
        if (StringUtils.isEmpty(bankOpenVO.getIdNo())) {
            result.setStatus(OpenAccountError.PARAM_ERROR.getErrCode());
            result.setStatusDesc("身份证号不能为空！");
            return result;
        }

        if (bankOpenVO.getIdNo().length() != 18) {
            result.setStatus(OpenAccountError.PARAM_ERROR.getErrCode());
            result.setStatusDesc("身份证号格式不正确！");
            return result;
        }
        String idNo = bankOpenVO.getIdNo().toUpperCase().trim();
        bankOpenVO.setIdNo(idNo);
        //增加身份证唯一性校验
        boolean isOnly = checkIdNo(idNo);
        if (!isOnly) {
            result.setStatus(OpenAccountError.PARAM_ERROR.getErrCode());
            result.setStatusDesc("身份证已存在！");
            return result;
        }
        if (!Validator.isMobile(bankOpenVO.getMobile())) {
            result.setStatus(OpenAccountError.PARAM_ERROR.getErrCode());
            result.setStatusDesc("手机号格式错误！");
            return result;
        }
        String mobile = user.getMobile();
        if (StringUtils.isBlank(mobile)) {
            if (StringUtils.isNotBlank(bankOpenVO.getMobile())) {
                if (existUser(bankOpenVO.getMobile())) {
                    result.setStatus(OpenAccountError.PARAM_ERROR.getErrCode());
                    result.setStatusDesc("用户信息错误，手机号码重复！");
                    return result;
                }
            } else {
                result.setStatus(OpenAccountError.PARAM_ERROR.getErrCode());
                result.setStatusDesc("用户信息错误，未获取到用户的手机号码！");
                return result;
            }
        } else {
            if (StringUtils.isNotBlank(bankOpenVO.getMobile()) && !mobile.equals(bankOpenVO.getMobile())) {
                result.setStatus(OpenAccountError.PARAM_ERROR.getErrCode());
                result.setStatusDesc("用户信息错误，用户的手机号码错误！");
                return result;
            }
        }

        return result;
    }

    @Override
    public ModelAndView getOpenAccountMV(OpenAccountPageBean openBean) {
        ModelAndView mv = new ModelAndView();
        // 根据身份证号码获取性别
        String gender = "";
        int sexInt = Integer.parseInt(openBean.getIdNo().substring(16, 17));
        if (sexInt % 2 == 0) {
            gender = "F";
        } else {
            gender = "M";
        }
        // 获取共同参数
        String bankCode = PropUtils.getSystem(BankCallConstant.BANK_BANKCODE);
        String bankInstCode = PropUtils.getSystem(BankCallConstant.BANK_INSTCODE);
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        String idType = BankCallConstant.ID_TYPE_IDCARD;
        // 调用开户接口
        BankCallBean openAccoutBean = new BankCallBean();
        openAccoutBean.setVersion(BankCallConstant.VERSION_10);
        openAccoutBean.setTxCode(BankCallConstant.TXCODE_ACCOUNT_OPEN_PAGE);
        openAccoutBean.setInstCode(bankInstCode);
        openAccoutBean.setBankCode(bankCode);
        openAccoutBean.setIdentity(openBean.getIdentity());
        // 代偿角色的账户类型为  00100-担保账户  其他的是 00000-普通账户
        if ("3".equals(openBean.getIdentity())) {
            openAccoutBean.setAcctUse("00100");
        } else {
            openAccoutBean.setAcctUse("00000");
        }
        openAccoutBean.setTxDate(txDate);
        openAccoutBean.setTxTime(txTime);
        openAccoutBean.setSeqNo(seqNo);
        openAccoutBean.setChannel(openBean.getChannel());
        openAccoutBean.setIdType(idType);
        openAccoutBean.setIdNo(openBean.getIdNo());
        openAccoutBean.setName(openBean.getTrueName());
        openAccoutBean.setGender(gender);
        openAccoutBean.setMobile(openBean.getMobile());
        openAccoutBean.setAcctUse(openBean.getAcctUse());
        openAccoutBean.setIdentity(openBean.getIdentity());
        // 同步地址  是否跳转到前端页面
        String retUrl = systemConfig.getWebHost() + ClientConstant.CLIENT_HEADER_MAP.get(openBean.getPlatform()) + "/user/open/return?phone=" + openBean.getMobile();
        String successUrl = retUrl + "&isSuccess=1";
        // 异步调用路
        String bgRetUrl = systemConfig.getWebHost() + ClientConstant.CLIENT_HEADER_MAP.get(openBean.getPlatform()) + "/user/open/bgReturn.do?phone=" + openBean.getMobile();
        openAccoutBean.setRetUrl(retUrl);
        openAccoutBean.setSuccessfulUrl(successUrl);
        openAccoutBean.setNotifyUrl(bgRetUrl);
        openAccoutBean.setCoinstName(openBean.getCoinstName());
        // 页面调用必须传的
        String orderId = GetOrderIdUtils.getOrderId2(openBean.getUserId());
        openAccoutBean.setLogBankDetailUrl(BankCallConstant.BANK_URL_ACCOUNT_OPEN_PAGE);
        openAccoutBean.setLogOrderId(orderId);
        openAccoutBean.setLogOrderDate(orderDate);
        openAccoutBean.setLogUserId(String.valueOf(openBean.getUserId()));
        openAccoutBean.setLogRemark("页面开户");
        openAccoutBean.setLogIp(openBean.getIp());
        openAccoutBean.setLogClient(Integer.parseInt(openBean.getPlatform()));
        openBean.setOrderId(orderId);
        try {
            mv = BankCallUtils.callApi(openAccoutBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

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
            this.amBankOpenClient.updateUserAccountLogState(userId, bean.getLogOrderId(), 4);
            logger.info("开户失败，失败原因:银行返回响应代码:[" + retCode + "],订单号:[" + bean.getLogOrderId() + "].");
            result.setStatus(false);
            return result;
        }
        // 开户成功后,保存用户的开户信息
        Integer saveBankAccountFlag = this.amBankOpenClient.saveUserAccount(bean);
        if (saveBankAccountFlag.intValue() != 1) {
            logger.info("开户失败,保存用户的开户信息失败:[" + retCode + "],订单号:[" + bean.getLogOrderId() + "].");
            result.setStatus(false);
            result.setMessage("开户失败,保存用户开户信息失败");
            return result;
        }
        // 保存银行卡信息
        Integer saveBankCardFlag = this.amBankOpenClient.saveCardNoToBank(bean);
        if (saveBankCardFlag.intValue() != 1) {
            logger.info("开户失败,保存银行卡信息失败:[" + retCode + "],订单号:[" + bean.getLogOrderId() + "].");
            result.setStatus(false);
            result.setMessage("开户失败,保存银行卡信息失败");
            return result;
        }
        result.setStatus(true);
        result.setMessage("开户成功");
        logger.info("页面开户异步处理end,UserId:{} 开户平台为：{}", bean.getLogUserId());
        return result;
    }

    @Override
    public Map<String, String> openAccountReturn(String token, String isSuccess) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "success");
        WebViewUser user = (WebViewUser) redisUtil.get(token);
        if (user == null) {
            throw new ReturnMessageException(OpenAccountError.USER_NOT_LOGIN_ERROR);
        }
        if (isSuccess == null || !"1".equals(isSuccess)) {
            resultMap.put("status", "fail");
        } else {
            resultMap.put("status", "success");
        }
        return resultMap;
    }

}
