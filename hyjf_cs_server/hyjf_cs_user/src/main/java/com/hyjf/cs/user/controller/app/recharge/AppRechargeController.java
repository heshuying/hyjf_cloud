/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.BankRechargeConfigVo;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.user.bean.AppRechargeRequestBean;
import com.hyjf.cs.user.bean.AppRechargeResultBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.result.AppRechargeInfoResult;
import com.hyjf.cs.user.service.recharge.AppRechargeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author fq
 * @version AppRechargeController, v0.1 2018/7/30 9:34
 */
@Api(value = "用户充值", description = "用户充值")
@RestController
@RequestMapping("/hyjf-app/user/bank/recharge")
public class AppRechargeController extends BaseUserController {
    @Autowired
    private AppRechargeService appRechargeService;
    @Autowired
    private SystemConfig systemConfig;
    /** 充值描述 */
    private final String CARD_DESC = "限额:单笔{0}，单日{1}，单月{2}";
    private final String RECHARGE_KINDLY_REMINDER = "注：网银转账时，银行请选择（城市商业银行）江西银行或南昌银行。线下充值的到账时间一般为1-3天（具体到账时间以银行的实际到账时间为准）。";
    private final String RCV_ACCOUNT_NAME = "惠众商务顾问（北京）有限公司";
    private final String RCV_ACCOUNT = "791913149300306";
    private final String RCV_OPEN_BANK_NAME = "江西银行南昌铁路支行";
    private final String KINDLY_REMINDER = "温馨提示：\n①线下充值使用网银转账时，付款账户须与平台绑定银行卡一致，不支持非平台绑定银行卡的网银转账充值功能，即“同卡进出”原则；\n②银行转账时，请选择（城市商业银行）江西银行或者南昌银行；\n③线下充值在工作日17:00前完成操作，当日到账，否则资金最晚将于下个工作日到账；\n④线下充值不符合“同卡进出”原则的，充值资金最长T+1工作日会被退回至付款账户；\n⑤不支持支付宝、微信等第三方支付平台的转账充值功能。";
    private final String IMPORTANT_HINTS = "请务必使用该卡作为付款账户进行转账";

    /** 金额单位 万元 */
    private final Integer AMOUNT_UNIT = 10000;

    @ApiOperation(value = "短信充值发送验证码", notes = "短信充值发送验证码")
    @RequestMapping("/sendCode")
    public AppRechargeInfoResult sendCode(HttpServletRequest request, HttpServletResponse response) {
        logger.info("短信充值发送验证码开始......");
        AppRechargeInfoResult result = new AppRechargeInfoResult("/hyjf-app/user/bank/recharge/sendCode");
        String sign = request.getParameter("sign");
        Integer userId = SecretUtil.getUserId(sign); // 用户ID
        String cardNo = request.getParameter("cardNo");// 开户银行代号
        String mobile = request.getParameter("mobile");// 用户的手机号
        String isMencry = request.getParameter("isMencry");

        String message = "系统参数错误";
        String status = CustomConstants.APP_STATUS_FAIL;
        JSONObject checkResult;

        // 检查参数
        if(userId == null || StringUtils.isBlank(mobile) || StringUtils.isBlank(sign) || StringUtils.isBlank(cardNo)){
            result.setStatusDesc(message);
            result.setStatus(status);
            return result;
        }

        // 取得加密用的Key
        if(!"1".equals(isMencry)){
            String key = SecretUtil.getKey(sign);
            if (Validator.isNull(key)) {
                result.setStatus("1");
                result.setStatusDesc("请求参数非法");
                return result;
            }

            // 解密
            mobile = DES.decodeValue(key, mobile);

        }

        if(!Validator.isMobile(mobile)){
            result.setStatusDesc(message);
            result.setStatus(status);
            return result;
        }

        // 判断用户是否被禁用
        UserVO users = this.appRechargeService.getUsersById(userId);
        if (users == null || users.getStatus() == 1) {
            result.setStatusDesc("对不起,该用户已经被禁用。");
            result.setStatus(status);
            return result;
        }
        if (users.getUserType() == 1) {
            result.setStatusDesc("对不起,企业用户只能通过线下充值。");
            result.setStatus(status);
            return result;
        }
        // 用户银行卡号
        if (StringUtils.isBlank(cardNo)) {
            // 获取绑定的银行卡号
            List<BankCardVO> bankCardVOList = this.appRechargeService.getBankOpenAccountById(users);
			if (!CollectionUtils.isEmpty(bankCardVOList)) {
				BankCardVO bankCardVO = bankCardVOList.get(0);
				cardNo = bankCardVO.getCardNo();
			}
        }

        // 调用短信发送接口
        BankCallBean mobileBean = this.appRechargeService.callSendCode(userId, mobile, "directRechargeOnline", BankCallConstant.CHANNEL_APP, cardNo);

        if (Validator.isNull(mobileBean)) {
            message = "短信验证码发送失败，请稍后再试！";
            result.setStatusDesc(message);
            result.setStatus(status);
            return result;
        }

        // 短信发送返回结果码
        String retCode = mobileBean.getRetCode();
        if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode)
                && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
            message = "短信验证码发送失败！";
            result.setStatusDesc(message);
            result.setStatus(status);
            return result;
        }
        if (Validator.isNull(mobileBean.getSrvAuthCode()) && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
            message = "短信验证码发送失败！";
            result.setStatusDesc(message);
            result.setStatus(status);
            return result;
        }
        String smsSeq = mobileBean.getSmsSeq();
        message = "短信发送成功！";
        status = CustomConstants.APP_STATUS_SUCCESS;
        result.setStatusDesc(message);
        result.setSmsSeq(smsSeq);
        result.setStatus(status);
        return result;
    }

    /**
     * app获取快捷充值卡及手续费的数据接口
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "app获取充值信息接口",notes = "app获取充值信息接口")
    @PostMapping(value = "/getQpRechargeInfo")
    public AppResult getQpRechargeInfo(
            @RequestHeader(value = "userId")Integer userId,
            @RequestBody AppRechargeRequestBean requestBean){
        AppResult result = new AppResult();
/*        AppRechargeInfoResultVo resultVo = new AppRechargeInfoResultVo(AppRechargeDefine.GET_QP_RECHARGE_INFO, HOST_URL + AppRechargeDefine.RECHARGE_RULE_URL, HOST_URL
                + AppRechargeDefine.RECHARGE_OTHER_URL + "?sign=" + vo.getSign());*/
        AppRechargeResultBean resultBean = new AppRechargeResultBean();
        resultBean.setRequest("/hyjf-app/user/bank/recharge/getQpRechargeInfo");
        resultBean.setRechargeRuleUrl(systemConfig.webHost + "/user/bank/recharge/rechargeRule");
        resultBean.setOtherUrl(systemConfig.webHost + "/hyjf-app/user/bank/recharge/offLineRechageInfo?sign=" + requestBean.getSign());

        String key = requestBean.getKey();
        if (StringUtils.isEmpty(key)) {
            result.setStatus(CustomConstants.SIGN_ERROR);
            result.setStatusDesc("获取数据加密秘钥失败");
            return result;
        } else {
            // 验证order
            if (SecretUtil.checkOrder(key, requestBean.getToken(), requestBean.getRandomString(), requestBean.getOrder())) {
                // 获取用户编号
                //Integer userId = SecretUtil.getUserId(sign);
                if (null != userId) {

                    //add by xiashuqing 20171204 begin app改版2.1新增查询账户余额
                    AccountVO account = this.appRechargeService.getAccountByUserId(userId);
                    if (account != null) {
                        resultBean.setAvailableAmount(CommonUtils.formatAmount(account.getBankBalance()));
                    }
                    //add by xiashuqing 20171204 end app改版2.1新增查询账户余额

                    // 获取用户的快捷卡信息
                    BankCardVO bankCard = this.appRechargeService.selectBankCardByUserId(userId);
                    if (null != bankCard) {
                        result.setStatus(CustomConstants.APP_STATUS_SUCCESS);
                        result.setStatusDesc(CustomConstants.APP_STATUS_DESC_SUCCESS);
                        resultBean.setBank(StringUtils.isBlank(bankCard.getBank()) ? StringUtils.EMPTY : bankCard.getBank());
                        // 银行卡号
                        resultBean.setCardNo(bankCard.getCardNo());
                        resultBean.setCardNo_info(BankCardUtil.getCardNo(bankCard.getCardNo()));
                        resultBean.setMobile(bankCard.getMobile());//成功充值手机号码
                        // 银行代码
                        resultBean.setCode("");
                        Integer bankId = bankCard.getBankId();
                        BankConfigVO banksConfig = appRechargeService.getBankConfigByBankId(bankId);

                        if (banksConfig != null && StringUtils.isNotEmpty(banksConfig.getLogo())) {
                            resultBean.setLogo(systemConfig.webHost + banksConfig.getLogo());
                        } else {
                            resultBean.setLogo(systemConfig.webHost + "/data/upfiles/filetemp/image/bank_log.png");
                        }

                        if(banksConfig !=null && StringUtils.isNotEmpty(banksConfig.getName())){
                            resultBean.setBank(banksConfig.getName());
                        }

                        // 是否快捷卡
                        if(banksConfig != null && banksConfig.getQuickPayment() == 1){
                            resultBean.setIsDefault("2");
                        }else {
                            resultBean.setIsDefault("0");
                        }

                        if (!StringUtils.isEmpty(requestBean.getMoney())) {
                            // 根据快捷卡计算充值手续费
                            Long money;
                            try {
                                money = Long.parseLong(requestBean.getMoney());
                            } catch (Exception e) {
                                resultBean = new AppRechargeResultBean();
                                resultBean.setRequest("/hyjf-app/user/bank/recharge/getQpRechargeInfo");
                                resultBean.setRechargeRuleUrl(systemConfig.webHost + "/user/bank/recharge/rechargeRule");
                                resultBean.setOtherUrl(systemConfig.webHost + "/hyjf-app/user/bank/recharge/offLineRechageInfo?sign=" + requestBean.getSign());
/*                                resultVo = new AppRechargeInfoResultVo(AppRechargeDefine.GET_QP_RECHARGE_INFO, HOST_URL + AppRechargeDefine.RECHARGE_RULE_URL, HOST_URL
                                        + AppRechargeDefine.RECHARGE_OTHER_URL + "?sign=" + vo.getSign());*/
                                result.setStatusDesc("请输入有效的充值金额");
                                return result;
                            }
                            // 手续费
                            BigDecimal fee = BigDecimal.ZERO;
                            // 时间到账金额
                            BigDecimal balance = new BigDecimal(money);
                            resultBean.setBalance(CustomConstants.DF_FOR_VIEW.format(balance));
                            resultBean.setFee(CustomConstants.DF_FOR_VIEW.format(fee));
                            // 拼接展示信息字符串
                            //String moneyInfo = AppRechargeDefine.FEE + CustomConstants.DF_FOR_VIEW.format(fee) + AppRechargeDefine.RECHARGE_INFO_SUFFIX + AppRechargeDefine.BALANCE
                            //+ CustomConstants.DF_FOR_VIEW.format(balance) + AppRechargeDefine.RECHARGE_INFO_SUFFIX;
                            BankRechargeConfigVo bankRechargeConfigVo = appRechargeService.getBankRechargeConfigByBankId(bankId);
                            if (banksConfig != null) {
                                // 每次限额 单位：万元
                                BigDecimal timesLimitAmount = bankRechargeConfigVo.getSingleQuota()
                                        .divide(new BigDecimal(AMOUNT_UNIT));
                                // 每日限额 单位：万元
                                BigDecimal dayLimitAmount = bankRechargeConfigVo.getSingleCardQuota()
                                        .divide(new BigDecimal(AMOUNT_UNIT));
                                // 每月限额 单位: 万元
                                // 月限额是否还有必要,因为数据库里没有
                                //BigDecimal monthLimitAmount = bankRechargeConfigVo.getMonthCardQuota().divide(new BigDecimal(AMOUNT_UNIT));
								/*// 是否支持快捷支付1:支持 2:不支持
								Integer quickPayment = banksConfig.getQuickPayment();*/
                                BigDecimal monthLimitAmount = BigDecimal.ZERO;
/*                                if (monthLimitAmount == null) {
                                    monthLimitAmount = BigDecimal.ZERO;
                                }*/
                                String moneyInfo = MessageFormat.format(CARD_DESC, (BigDecimal.ZERO.compareTo(timesLimitAmount) == 0)?"不限":timesLimitAmount.toString() + "万元",
                                        (BigDecimal.ZERO.compareTo(dayLimitAmount)==0)?"不限":dayLimitAmount.toString() + "万元",
                                        (BigDecimal.ZERO.compareTo(monthLimitAmount)==0)?"不限":monthLimitAmount.toString() + "万元");
                                resultBean.setMoneyInfo(moneyInfo);
                            }
                        }
                    } else {
                        result.setStatus(CustomConstants.APP_STATUS_SUCCESS);
                        result.setStatusDesc("未查询到用户快捷卡信息");
                        //return result;
                    }

                    resultBean.setButtonWord("确认充值".concat(CommonUtils.formatAmount(requestBean.getVersion(), requestBean.getMoney())).concat("元"));

                    //设置线下充值信息
                    this.setOffLineRechageInfo(resultBean, userId);

                } else {
                    result.setStatusInfo(MsgEnum.ERR_SIGN);
/*                    result.setStatus(CustomConstants.TOKEN_ERROR);
                    result.setStatusDesc("用户认证失败");*/
                }
            } else {
                result.setStatusInfo(MsgEnum.ERR_ORDER_VERIFY);
            }
        }
        /** 充值描述 */
        resultBean.setRcvAccountName(RCV_ACCOUNT_NAME);
        resultBean.setRcvAccount(RCV_ACCOUNT);
        resultBean.setRcvOpenBankName(RCV_OPEN_BANK_NAME);
        resultBean.setKindlyReminder(KINDLY_REMINDER);
        resultBean.setHints(IMPORTANT_HINTS);

        result.setData(requestBean);

        return result;
    }
    /**
     * 设置线下充值信息，包含 收款方户名，收款方账号，收款方开户行名，友情提示
     * @param resultVo
     * @param userId
     */
    private void setOffLineRechageInfo(AppRechargeResultBean resultVo, Integer userId) {
        resultVo.setRcvOpenBankName(RCV_OPEN_BANK_NAME);
        resultVo.setKindlyReminder(RECHARGE_KINDLY_REMINDER);

        BankOpenAccountVO bankOpenAccount = appRechargeService.getBankOpenAccountByUserId(userId);
        if (bankOpenAccount != null) {
            resultVo.setRcvAccount(bankOpenAccount.getAccount());
        }

        UserInfoVO usersInfo = appRechargeService.getUserInfoByUserId(userId);
        if (usersInfo != null) {
            resultVo.setRcvAccountName(usersInfo.getTruename());
        }
        // 用户信息
        UserVO users = appRechargeService.getUserByUserId(userId);
        // 用户类型
        Integer userType = users.getUserType();
        // 如果是企业用户
        if (userType == 1) {
            // 根据用户ID查询企业用户账户信息
            CorpOpenAccountRecordVO record = appRechargeService.getCorpOpenAccountRecordByUserId(userId);
            resultVo.setRcvAccountName(record.getBusiName());
        }

    }
}
