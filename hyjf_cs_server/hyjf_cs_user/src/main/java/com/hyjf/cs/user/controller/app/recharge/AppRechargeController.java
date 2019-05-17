/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.app.recharge.AppRechargeLimitVO;
import com.hyjf.am.vo.app.recharge.AppRechargeRuleVO;
import com.hyjf.am.vo.app.recharge.AppRechargeVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.AppRechargeRequestBean;
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
@Api(value = "app端-用户充值", tags = "app端-用户充值")
@RestController
@RequestMapping("/hyjf-app/user/bank/recharge")
public class AppRechargeController extends BaseUserController {
    @Autowired
    private AppRechargeService appRechargeService;
    @Autowired
    private SystemConfig systemConfig;


    /** 充值描述 */
    private final String CARD_DESC = "限额:{0}{1}{2}";
    private final String RECHARGE_KINDLY_REMINDER = "注：网银转账时，银行请选择（城市商业银行）江西银行或南昌银行。线下充值的到账时间一般为1-3天（具体到账时间以银行的实际到账时间为准）。";
    private final String RCV_ACCOUNT_NAME = "惠众商务顾问（北京）有限公司";
    private final String RCV_ACCOUNT = "791913149300306";
    private final String RCV_OPEN_BANK_NAME = "江西银行南昌铁路支行";
    private final String KINDLY_REMINDER = "温馨提示：\n①转账充值使用网银转账时，付款账户须与平台绑定银行卡一致，不支持非平台绑定银行卡的网银转账充值功能，即“同卡进出”原则；\n②银行转账时，请选择（城市商业银行）江西银行或者南昌银行；\n③转账充值在工作日17:00前完成操作，当日到账，否则资金最晚将于下个工作日到账；\n④转账充值不符合“同卡进出”原则的，充值资金最长T+1工作日会被退回至付款账户；\n⑤不支持支付宝、微信等第三方支付平台的转账充值功能。";
    private final String IMPORTANT_HINTS = "请务必使用该卡作为付款账户进行转账";

    /** 金额单位 万元 */
    private final Integer AMOUNT_UNIT = 10000;

    @ApiOperation(value = "短信充值发送验证码", notes = "短信充值发送验证码")
    @PostMapping("/sendCode")
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
    @ApiOperation(value = "获取充值信息接口",notes = "获取充值信息接口")
    @PostMapping(value = "/getQpRechargeInfo")
    public AppRechargeInfoResult getQpRechargeInfo(@RequestHeader(value = "userId")Integer userId,AppRechargeRequestBean requestBean){
        AppRechargeInfoResult result = new AppRechargeInfoResult("/hyjf-app/user/bank/recharge/getQpRechargeInfo");
        //result.setRequest("/hyjf-app/user/bank/recharge/getQpRechargeInfo");
        //result.setRechargeRuleUrl(systemConfig.webHost + "/user/bank/recharge/rechargeRule");
        //result.setOtherUrl(systemConfig.webHost + "/hyjf-app/user/bank/recharge/offLineRechageInfo?sign=" + requestBean.getSign());
        result.setRechargeRuleUrl(systemConfig.getAppFrontHost() + "/user/bank/recharge/rechargeRule");
        result.setOtherUrl(systemConfig.getAppFrontHost() + "/hyjf-app/user/bank/recharge/offLineRechageInfo?sign=" + requestBean.getSign());
        // 取得加密用的Key
        String key = SecretUtil.getKey(requestBean.getSign());
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

                    AccountVO account = this.appRechargeService.getAccountByUserId(userId);
                    if (account != null) {
                        result.setAvailableAmount(CommonUtils.formatAmount(account.getBankBalance()));
                    }

                    // 获取用户的快捷卡信息
                    BankCardVO bankCard = this.appRechargeService.selectBankCardByUserId(userId);
                    if (null != bankCard) {
                        result.setStatus(CustomConstants.APP_STATUS_SUCCESS);
                        result.setStatusDesc(CustomConstants.APP_STATUS_DESC_SUCCESS);
                        result.setBank(StringUtils.isBlank(bankCard.getBank()) ? StringUtils.EMPTY : bankCard.getBank());
                        // 银行卡号
                        result.setCardNo(bankCard.getCardNo());
                        result.setCardNo_info(BankCardUtil.getFormatCardNo(bankCard.getCardNo()));
                        result.setMobile(bankCard.getMobile());//成功充值手机号码
                        // 银行代码
                        result.setCode("");
                        Integer bankId = bankCard.getBankId();
                        JxBankConfigVO jxBankConfigVO = appRechargeService.getJxBankConfigByBankId(bankId);

                        if (jxBankConfigVO != null && StringUtils.isNotEmpty(jxBankConfigVO.getBankIcon())) {
                            result.setLogo(systemConfig.getAppFrontHost() + jxBankConfigVO.getBankIcon());
                        } else {
                            result.setLogo(systemConfig.getAppFrontHost() + "/data/upfiles/filetemp/image/bank_log.png");
                        }

                        if(jxBankConfigVO !=null && StringUtils.isNotEmpty(jxBankConfigVO.getBankName())){
                            result.setBank(jxBankConfigVO.getBankName());
                        }

                        // 是否快捷卡
                        if(jxBankConfigVO != null && jxBankConfigVO.getQuickPayment() == 1){
                            result.setIsDefault("2");
                        }else {
                            result.setIsDefault("0");
                        }

                        if (!StringUtils.isEmpty(requestBean.getMoney())) {
                            // 根据快捷卡计算充值手续费
                            Long money;
                            try {
                                money = Long.parseLong(requestBean.getMoney());
                            } catch (Exception e) {
                                result = new AppRechargeInfoResult("/hyjf-app/user/bank/recharge/getQpRechargeInfo");
                                result.setRechargeRuleUrl(systemConfig.getAppFrontHost() + "/user/bank/recharge/rechargeRule");
                                result.setOtherUrl(systemConfig.getAppFrontHost() + "/hyjf-app/user/bank/recharge/offLineRechageInfo?sign=" + requestBean.getSign());

                                //result.setRequest("/hyjf-app/user/bank/recharge/getQpRechargeInfo");
                                //result.setRechargeRuleUrl(systemConfig.webHost + "/user/bank/recharge/rechargeRule");
                                //result.setOtherUrl(systemConfig.webHost + "/hyjf-app/user/bank/recharge/offLineRechageInfo?sign=" + requestBean.getSign());
                                result.setStatusDesc("请输入有效的充值金额");
                                return result;
                            }
                            // 手续费
                            BigDecimal fee = BigDecimal.ZERO;
                            // 时间到账金额
                            BigDecimal balance = new BigDecimal(money);
                            result.setBalance(CustomConstants.DF_FOR_VIEW.format(balance));
                            result.setFee(CustomConstants.DF_FOR_VIEW.format(fee));
                            // 拼接展示信息字符串
                            //String moneyInfo = AppRechargeDefine.FEE + CustomConstants.DF_FOR_VIEW.format(fee) + AppRechargeDefine.RECHARGE_INFO_SUFFIX + AppRechargeDefine.BALANCE
                            //+ CustomConstants.DF_FOR_VIEW.format(balance) + AppRechargeDefine.RECHARGE_INFO_SUFFIX;
                            if (jxBankConfigVO != null) {
/*                                BigDecimal timesLimit = bankRechargeConfigVo.getSingleQuota();
                                timesLimit = (timesLimit == null)?BigDecimal.ZERO:timesLimit;
                                BigDecimal dayLimit = bankRechargeConfigVo.getSingleCardQuota();
                                dayLimit = (dayLimit == null)?BigDecimal.ZERO:dayLimit;*/
                                // 每次限额 单位：万元
                                BigDecimal timesLimitAmount = jxBankConfigVO.getSingleQuota().divide(new BigDecimal(AMOUNT_UNIT));
                                // 每日限额 单位：万元
                                BigDecimal dayLimitAmount = jxBankConfigVO.getSingleCardQuota().divide(new BigDecimal(AMOUNT_UNIT));
                                // 每月限额 单位: 万元
                                BigDecimal monthLimitAmount = jxBankConfigVO.getMonthCardQuota().divide(new BigDecimal(AMOUNT_UNIT));
								/*// 是否支持快捷支付1:支持 2:不支持
								Integer quickPayment = banksConfig.getQuickPayment();*/
                                if (monthLimitAmount == null) {
                                    monthLimitAmount = BigDecimal.ZERO;
                                }
                                String symbol = ",";
                                String symBol2 = ",";
                                if(BigDecimal.ZERO.compareTo(dayLimitAmount)==0 && BigDecimal.ZERO.compareTo(monthLimitAmount)==0){
                                    symbol = "";
                                }
                                if(BigDecimal.ZERO.compareTo(monthLimitAmount)==0){
                                    symBol2 = "";
                                }
                                String moneyInfo = MessageFormat.format(CARD_DESC, (BigDecimal.ZERO.compareTo(timesLimitAmount) == 0)?"":"单笔 "+timesLimitAmount.toString() + "万元" + symbol,
                                        (BigDecimal.ZERO.compareTo(dayLimitAmount)==0)?"":"单日 " + dayLimitAmount.toString() + "万元" + symBol2,
                                        (BigDecimal.ZERO.compareTo(monthLimitAmount)==0)?"":"单月 " + monthLimitAmount.toString() + "万元");

                                result.setMoneyInfo(moneyInfo);
                            }
                        }
                    } else {
                        result.setStatus(CustomConstants.APP_STATUS_SUCCESS);
                        result.setStatusDesc("未查询到用户快捷卡信息");
                    }

                    result.setButtonWord("确认充值".concat(CommonUtils.formatAmount(requestBean.getVersion(), requestBean.getMoney())).concat("元"));

                    //设置线下充值信息
                    this.setOffLineRechageInfo(result, userId);

                } else {
                    result.setStatus(MsgEnum.ERR_USER_AUTH.getCode());
                    result.setStatusDesc(MsgEnum.ERR_USER_AUTH.getMsg());
                }
            } else {
                result.setStatus(MsgEnum.ERR_DATA_VERIFICATION.getCode());
                result.setStatusDesc(MsgEnum.ERR_DATA_VERIFICATION.getMsg());
            }
            //add by cwyang 20180629 增加app3.0.9的转账指南
            String transferUrl = systemConfig.getAppRechangeGuideUrl();
            result.setTransferGuideURL(systemConfig.getAppServerHost() + transferUrl);
        }
        /** 充值描述 */
        result.setRcvAccountName(RCV_ACCOUNT_NAME);
        result.setRcvAccount(RCV_ACCOUNT);
        result.setRcvOpenBankName(RCV_OPEN_BANK_NAME);
        result.setKindlyReminder(KINDLY_REMINDER);
        result.setHints(IMPORTANT_HINTS);

        return result;
    }
    /**
     * 设置线下充值信息，包含 收款方户名，收款方账号，收款方开户行名，友情提示
     * @param resultVo
     * @param userId
     */
    private void setOffLineRechageInfo(AppRechargeInfoResult resultVo, Integer userId) {
        resultVo.setRcvOpenBankName(RCV_OPEN_BANK_NAME);
        resultVo.setKindlyReminder(RECHARGE_KINDLY_REMINDER);

        BankOpenAccountVO bankOpenAccount = appRechargeService.getBankOpenAccount(userId);
        if (bankOpenAccount != null) {
            resultVo.setRcvAccount(bankOpenAccount.getAccount());
        }

        UserInfoVO usersInfo = appRechargeService.getUserInfo(userId);
        if (usersInfo != null) {
            resultVo.setRcvAccountName(usersInfo.getTruename());
        }
        // 用户信息
        UserVO users = appRechargeService.getUsersById(userId);
        // 用户类型
        Integer userType = users.getUserType();
        // 如果是企业用户
        if (userType == 1) {
            // 根据用户ID查询企业用户账户信息
            CorpOpenAccountRecordVO record = appRechargeService.getCorpOpenAccountRecord(userId);
            resultVo.setRcvAccountName(record.getBusiName());
        }

    }

    /**
     * app获取充值说明
     * @param
     * @return
     * @author wgx
     */
    @ApiOperation(value = "获取充值说明", notes = "获取充值说明")
    @PostMapping(value = "/getRechargeDetail")
    @ResponseBody
    public WebResult<AppRechargeVO> getRechargeRule() {
        WebResult webResult = new WebResult("0","成功");
        try {
            List<AppRechargeRuleVO> rechargeRuleList = appRechargeService.getRechargeRule();
            List<AppRechargeLimitVO> rechargeLimitList = appRechargeService.getRechargeLimit();
            AppRechargeVO appRechargeVo = new AppRechargeVO();
            appRechargeVo.setRechargeRule(rechargeRuleList);
            appRechargeVo.setRechargeLimit(rechargeLimitList);
            webResult.setData(appRechargeVo);
        } catch (Exception e) {
            webResult.setStatus("1");
            webResult.setStatusDesc("充值说明获取失败");
        }
        return webResult;
    }

}
