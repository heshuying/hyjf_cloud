package com.hyjf.cs.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.BankCardRequest;
package com.hyjf.cs.user.service.bankopen.impl;

import com.hyjf.am.resquest.user.BankOpenRequest;
import com.hyjf.am.vo.borrow.BanksConfigVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.common.validator.ValidatorCheckUtil;
import com.hyjf.cs.user.beans.ApiBankOpenRequestBean;
import com.hyjf.cs.user.beans.OpenAccountPageBean;
import com.hyjf.cs.user.client.AmBankOpenClient;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.OpenAccountError;
import com.hyjf.cs.user.redis.RedisUtil;
import com.hyjf.cs.user.result.AppResult;
import com.hyjf.cs.user.service.BankOpenService;
import com.hyjf.cs.user.util.ClientConstant;
import com.hyjf.cs.user.util.ErrorCodeConstant;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiasq
 * @version UserServiceImpl, v0.1 2018/4/11 9:34
 */

@Service
public class BankOpenServiceImpl implements BankOpenService {

	private static final Logger logger = LoggerFactory.getLogger(BankOpenServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmBankOpenClient amBankOpenClient;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AmConfigClient amConfigClient;

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
        String idType = BankCallConstant.ID_TYPE_IDCARD;
        // 调用开户接口
        BankCallBean openAccoutBean =  new BankCallBean(openBean.getUserId(),BankCallConstant.TXCODE_ACCOUNT_OPEN_PAGE,Integer.parseInt(openBean.getPlatform()),BankCallConstant.BANK_URL_ACCOUNT_OPEN_PAGE);
        openAccoutBean.setIdentity(openBean.getIdentity());
        // 代偿角色的账户类型为  00100-担保账户  其他的是 00000-普通账户
        if ("3".equals(openBean.getIdentity())) {
            openAccoutBean.setAcctUse("00100");
        } else {
            openAccoutBean.setAcctUse("00000");
        }
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
        openAccoutBean.setLogRemark("页面开户");
        openAccoutBean.setLogIp(openBean.getIp());

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
        // 查询银行卡绑定信息
        Integer saveBankCardFlag = saveCardNoToBank(bean);
        if (saveBankCardFlag.intValue() != 1) {
            logger.info("开户失败,保存银行卡信息失败:[" + retCode + "],订单号:[" + bean.getLogOrderId() + "].");
            // 这里是不是写false？
            result.setStatus(false);
            result.setMessage("开户失败,保存银行卡信息失败");
            return result;
        }
        result.setStatus(true);
        result.setMessage("开户成功");
        logger.info("页面开户异步处理end,UserId:{} 开户平台为：{}", bean.getLogUserId());
        return result;
    }


    /**
     * 保存用户银行卡信息
     * @param bean
     * @return
     */
    private Integer saveCardNoToBank(BankCallBean bean) {
        Integer userId = Integer.parseInt(bean.getLogUserId());
        UserVO user = getUsers(userId);
        // 调用江西银行接口查询用户绑定的银行卡
        BankCallBean cardBean = new BankCallBean(userId, BankCallConstant.TXCODE_ACCOUNT_OPEN_PAGE, bean.getLogClient());
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
                    return this.amBankOpenClient.saveCardNoToBank(bank);
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
        bean.setChannel(channel);
        bean.setAccountId(cardNo);
        bean.setLogRemark("联行号查询");
        return BankCallUtils.callApiBg(bean);
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

}
